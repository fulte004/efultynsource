package Csla;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import java.util.Iterator;

import Csla.ComponentModel.AddingNewEvent;
import Csla.ComponentModel.BindingListListener;
import Csla.ComponentModel.ListChangedEvent;
import Csla.ComponentModel.ListChangedType;
import Csla.ComponentModel.RemovingItemEvent;
import Csla.Core.IBindingList;
import Csla.Core.ListSortDirection;
//import Csla.SortedBindingList.SortedEnumerator;

/**
 * Provides a filtered view into an existing IList(Of T).
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:35 PM
 */
public class FilteredBindingList<T> implements IBindingList<T>, Iterable<T>, CancelAddNew, BindingListListener  {

	/**
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:35 PM
	 */
	private class ListItem {

		private int _baseIndex;
		private Object _key;

		/**
		 * 
		 * @param key
		 * @param baseIndex
		 */
		public ListItem(Object key, int baseIndex){
			_key = key;
			_baseIndex = baseIndex;
		}

		public int getBaseIndex(){
			return _baseIndex; 
		}
		public void setBaseIndex(int value){
			_baseIndex = value; 
		}
		public Object getKey(){
			return _key; 
		}

		public String toString(){
			return getKey().toString();
		}

	}

	/**
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:35 PM
	 */
	private class FilteredEnumerator implements Iterable<T> {

		private List<ListItem> _filterIndex;
		private int _index;
		private List<T> _list;

		/**
		 * 
		 * @param list
		 * @param filterIndex
		 */
		public FilteredEnumerator(List<T> list, List<ListItem> filterIndex){
			_list = list;
			_filterIndex = filterIndex;
			reset();
		}

		@SuppressWarnings("unused")
		public T getCurrent(){
			return _list.get(_filterIndex.get(_index).getBaseIndex()); 
		}

		@SuppressWarnings("unused")
		public boolean moveNext(){
			if (_index < _filterIndex.size() - 1)
			{
				_index++;
				return true;
			}
			else
				return false;
		}

		public void reset(){
			_index = -1;
		}

		public java.util.Iterator<T> iterator() {
			return _list.iterator();
		}

	}

	private IBindingList<T> _bindingList;
	private Object _filter;
	private Method _filterBy;
	private boolean _filtered;
	private List<ListItem> _filterIndex = new ArrayList<ListItem>();
	private List<T> _list;
	private FilterProviderListener _provider = null;
	private ArrayList<BindingListListener> _bindingListeners = new ArrayList<BindingListListener>();
	private boolean _supportsBinding;

	/**
	 * Creates a new view based on the provided IList Object.
	 * 
	 * @param list    The IList (collection) containing the data.
	 */
	public FilteredBindingList(List<T> list){
		_list = list;

		IBindingList<T> test = this;
		if (_list.getClass().isInstance(test))
		{
			_supportsBinding = true;
			_bindingList = (IBindingList<T>)_list;
			_bindingList.add_ListChanged((BindingListListener) this);
		}
	}

	/**
	 * Creates a new view based on the provided IList Object.
	 * 
	 * @param list    The IList (collection) containing the data.
	 * @param filterProvider    Delegate pointer to a method that implements the
	 * filter behavior.
	 */
	public FilteredBindingList(List<T> list, FilterProviderListener filterProvider){
		_provider = filterProvider;
	}

	/**
	 * 
	 * @param value
	 */
	public void add_ListChanged(BindingListListener value){
		_bindingListeners.add(value);
	}

	/**
	 * 
	 * @param value
	 */
	public void remove_ListChanged(BindingListListener value){
		_bindingListeners.remove(value);
	}
	/**
	 * Adds an item to the list.
	 * 
	 * @param item    Item to be added.
	 */
	public boolean add(T item){
		return _list.add(item);
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param property    Property on which to build the index.
	 */
	public void addIndex(Method property){
		if (_supportsBinding)
			_bindingList.addIndex(property);
	}

	/**
	 * Implemented by IList source Object.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedOperationException 
	 */
	public T addNew() throws UnsupportedOperationException, InstantiationException, IllegalAccessException{
		T result;
		if (_supportsBinding)
			result = (T)_bindingList.addNew();
		else
			result = null; //default(T)

		//_newItem = (T)result;
		return result;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean getAllowEdit(){
		if (_supportsBinding)
			return _bindingList.allowsEdit();
		else
			return false;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean getAllowNew(){
		if (_supportsBinding)
			return _bindingList.allowsNew();
		else
			return false;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean getAllowRemove(){
		if (_supportsBinding)
			return _bindingList.allowsRemove();
		else
			return false;
	}

	/**
	 * Applies a filter to the view.
	 * 
	 * @param propertyName    The text name of the property on which to filter.
	 * @param filter    The filter criteria.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void applyFilter(String propertyName, Object filter) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		_filterBy = null;
		_filter = filter;

		if (!propertyName.isEmpty())
		{
			if(_list.size() > 0){
				T item = _list.get(0);
				Class<?> itemType = item.getClass();
				for (Method prop : itemType.getMethods())
				{
					if (prop.getName() == propertyName)
					{
						_filterBy = prop;
						break;
					}
				}
			}
		}

		applyFilter(_filterBy, filter);
	}

	/**
	 * Applies a filter to the view.
	 * 
	 * @param property    A PropertyDescriptor for the property on which to filter.
	 * @param filter    The filter criteria.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void applyFilter(Method property, Object filter) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		_filterBy = property;
		_filter = filter;
		doFilter();
	}

	/**
	 * Sorts the list if the original list supports sorting.
	 * 
	 * @param property    Property on which to sort.
	 * @param direction    Direction of the sort.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NotSupportedException 
	 */
	public void applySort(Method property, ListSortDirection direction) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NotSupportedException{
		if (supportsSorting())
			_bindingList.applySort(property, direction);
		else
			throw new NotSupportedException("Sorting not supported.");
	}

	/**
	 * void ICancelAddNew.EndNew(int itemIndex) { do nothing }
	 * 
	 * @param itemIndex
	 */
	public void cancelNew(int itemIndex){
		CancelAddNew can = (CancelAddNew)_list ;
		if (can != null)
			can.cancelNew(itemIndex);
		else
			_list.remove(itemIndex);
	}

	/**
	 * Clears the list.
	 */
	public void clear(){
		_list.clear();
	}

	/**
	 * 
	 * @param array
	 * @param indexFilteredBindingList<T>
	 */
	//	private void copyTo(Array array, int index){
	//		T[] tmp = new T[array.size()];
	//		CopyTo(tmp, index);
	//		Array.Copy(tmp, 0, array, index, array.Length);
	//	}

	protected boolean isSynchronized(){
		return false; 
	}

	protected Object getSyncRoot(){
		return _list; 
	}

	/**
	 * 
	 * @param value
	 */
	//	public boolean add(Object value){
	//		add((T)value);
	//		int index = FilteredIndex(_list.size() - 1);
	//		if (index > -1)
	//			return index;
	//		else
	//			return 0;
	//	}

	/**
	 * 
	 * @param value
	 */
	//	public boolean contains(Object value){
	//		return _list.contains((T)value);
	//	}

	/**
	 * 
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public int indexOf(Object value){
		return indexOf((T)value);
	}

	/**
	 * 
	 * @param index
	 * @param value
	 */
	//	@SuppressWarnings("unchecked")
	//	public void add(int index, Object value){
	//		_list.add(index, (T)value);
	//	}

	protected boolean isFixedSize(){
		return false; 
	}

	/**
	 * 
	 * @param value
	 */
	public boolean remove(Object value){
		return _list.remove(value);
	}

	/**
	 * 
	 * @param index
	 */
	//	private Object get(int index){
	//		this[index] = (T)value;
	//	}

	/**
	 * Determines whether the specified item is contained in the list.
	 * 
	 *      @returns <see langword="true"/> if the item is contained in the list.
	 * 
	 * @param item    Item to find.
	 */
	public boolean contains(Object item){
		return _list.contains(item);
	}

	/**
	 * Copies the contents of the list to an array.
	 * 
	 * @param array    Array to receive the data.
	 * @param arrayIndex    Starting array index.
	 */
	//	public void copyTo(T[] array, int arrayIndex){
	//		int pos = arrayIndex;
	//		for (T child : this)
	//		{
	//			array[pos] = child;
	//			pos++;
	//		}
	//	}

	/**
	 * Gets the number of items in the list.
	 */
	public int size(){
		if (_filtered)
			return _filterIndex.size();
		else
			return _list.size(); 
	}

	private void doFilter() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		int index = 0;
		_filterIndex.clear();

		if (_provider == null)
			_provider = (FilterProviderListener) new DefaultFilter();

		if (_filterBy == null)
		{
			for (T obj : _list)
			{
				if (_provider.onFilterProviderInvoked(obj, _filter))
					_filterIndex.add(new ListItem(obj, index));
				index++;
			}
		}
		else
		{
			for (T obj : _list)
			{
				Object tmp = _filterBy.invoke(obj);
				if (_provider.onFilterProviderInvoked(tmp, _filter))
					_filterIndex.add(new ListItem(tmp, index));
				index++;
			}
		}

		_filtered = true;

		onListChanged(new ListChangedEvent(this, ListChangedType.Reset, 0));
	}

	/**
	 * 
	 * @param itemIndex
	 */
	public void endNew(int itemIndex){
		CancelAddNew can = (CancelAddNew)_list;
		if (can != null)
			can.endNew(itemIndex);
	}

	/**
	 * 
	 * @param originalIndex
	 */
	protected int filteredIndex(int originalIndex){
		int result = -1;
		if (_filtered)
		{
			for (int index = 0; index < _filterIndex.size(); index++)
			{
				if (_filterIndex.get(index).getBaseIndex() == originalIndex)
				{
					result = index;
					break;
				}
			}
		}
		else
			result = originalIndex;
		return result;
	}

	/**
	 * The property on which the items will be filtered.
	 * 
	 *      @value A descriptor for the property on which the items in the collection
	 * will be filtered.
	 *      @returns
	 *      @remark 
	 */
	public Method getFilterProperty(){
		return _filterBy; 
	}

	/**
	 * Gets or sets the filter provider method.
	 * 
	 *      @value Delegate pointer to a method that implements the filter behavior.
	 *      @returns Delegate pointer to a method that implements the filter behavior.
	 * 
	 *      @remark If this value is set to Nothing (null in C#) then the default
	 * filter provider, <see cref="DefaultFilter" /> will be used.
	 */
	public FilterProviderListener getFilterProvider(){
		return _provider;
	}
	public void setFilterProvider(FilterProviderListener value){
		_provider = value;
	}

	/**
	 * Finds an item in the view
	 * 
	 * @param propertyName    Name of the property to search
	 * @param key    Value to find
	 * @param type An instance of Class<T> from which the property methods can be found
	 */
	public int find(String propertyName, Object key, Class<T> type){
		Method findProperty = null;

		if (!propertyName.isEmpty())
		{
			for (Method prop : type.getMethods())
			{
				if (prop.getName() == propertyName)
				{
					findProperty = prop;
					break;
				}
			}
		}

		return find(findProperty, key);
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param property    Property to search for the key value.
	 * @param key    Key value for which to search.
	 */
	public int find(Method property, Object key){
		if (_supportsBinding)
			return filteredIndex(_bindingList.find(property, key));
		else
			return -1;
	}

	/**
	 * Gets an enumerator Object.
	 * 
	 *      @returns 
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> getEnumerator(){
		if (_filtered)
			return (Iterator<T>)new FilteredEnumerator(_list, _filterIndex);
		else
			return _list.iterator();
	}

	/**
	 * Gets the 0-based index of an item in the list.
	 * 
	 *      @returns 0-based index of the item in the list.
	 * 
	 * @param item    The item to find.
	 */
	//	public int indexOf(T item){
	//		return FilteredIndex(_list.indexOf(item));
	//	}

	/**
	 * Inserts an item into the list.
	 * 
	 * @param index    Index at which to insert the item.
	 * @param item    Item to insert.
	 */
	public void add(int index, T item){
		_list.add(index, item);
	}

	/**
	 * Returns True if the view is currently filtered.
	 */
	public boolean isFiltered(){
		return _filtered; 
	}

	/**
	 * Gets a value indicating whether the list is read-only.
	 */
	//	public boolean isReadOnly(){
	//		 return _list.isReadOnly(); 
	//	}

	/**
	 * Returns True if the view is currently sorted.
	 */
	public boolean isSorted(){
		if (supportsSorting())
			return _bindingList.isSorted();
		else
			return false;
	}


	@Override
	public void onAddingNew(AddingNewEvent e) {
		if (_bindingListeners.size() > 0){
			for(BindingListListener listener : _bindingListeners){
				listener.onAddingNew(e);
			}
		}
	}

	/**
	 * Raised to indicate that the list's data has changed.
	 * 
	 *      @remark This event is raised if the underling IList Object's data changes
	 * (assuming the underling IList also implements the IBindingList interface). It
	 * is also raised if the filter is changed to indicate that the view's data has
	 * changed.
	 */
	@Override
	public void onListChanged(ListChangedEvent e){
		if (_bindingListeners.size() > 0){
			for(BindingListListener listener : _bindingListeners){
				listener.onListChanged(e);
			}
		}
	}


	@Override
	public void onRemovingItem(RemovingItemEvent e) {
		if (_bindingListeners.size() > 0){
			for(BindingListListener listener : _bindingListeners){
				listener.onRemovingItem(e);
			}
		}
	}

	/**
	 * 
	 * @param filteredIndex
	 */
	private int originalIndex(int filteredIndex){
		if (_filtered)
			return _filterIndex.get(filteredIndex).getBaseIndex();
		else
			return filteredIndex;
	}

	/**
	 * Removes an item from the list.
	 * 
	 *      @returns <see langword="true"/> if the remove succeeds.
	 * 
	 * @param item    Item to remove.
	 */
	//	public boolean remove(T item){
	//		return _list.remove(item);
	//	}

	/**
	 * Removes an item from the list.
	 * 
	 * @param index    Index of item to be removed.
	 */
	public T remove(int index){
		if (_filtered)
		{
			return _list.remove(originalIndex(index));
		}
		else
			return _list.remove(index);
	}

	/**
	 * Removes the filter from the list, so the view reflects the state of the
	 * original list.
	 */
	public void RemoveFilter(){
		undoFilter();
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param property    Property for which the index should be removed.
	 */
	public void RemoveIndex(Method property){
		if (_supportsBinding)
			_bindingList.removeIndex(property);
	}

	/**
	 * Removes any sort currently applied to the view.
	 * @throws NotSupportedException 
	 */
	public void RemoveSort() throws NotSupportedException{
		if (supportsSorting())
			_bindingList.removeSort();
		else
			throw new NotSupportedException("Sorting not supported");
	}

	/**
	 * Returns the direction of the current sort.
	 */
	public ListSortDirection getSortDirection(){
		if (supportsSorting())
			return _bindingList.getSortDirection();
		else
			return ListSortDirection.ASCENDING; 
	}

	/**
	 * Returns the Method of the current sort.
	 */
	public Method getSortProperty(){
		if (supportsSorting())
			return _bindingList.getSortProperty();
		else
			return null; 
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected void onSourceChanged(Object sender, ListChangedEvent e) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if (_filtered)
		{
			int listIndex;
			int filteredIndex = -1;
			T newItem;
			Object newKey;
			switch (e.getListChangedType())
			{
			case ItemAdded:
				listIndex = e.getNewIndex();
				// add new value to index
				newItem = _list.get(listIndex);
				if (_filterBy != null)
					newKey = _filterBy.invoke(newItem);
				else
					newKey = newItem;
				_filterIndex.add(
						new ListItem(newKey, listIndex));
				filteredIndex = _filterIndex.size() - 1;
				// raise event 
				onListChanged(
						new ListChangedEvent(this,
								e.getListChangedType(), filteredIndex));
				break;

			case ItemChanged:
				listIndex = e.getNewIndex();
				// update index value
				filteredIndex = filteredIndex(listIndex);
				if (filteredIndex != -1)
				{
					newItem = _list.get(listIndex);
					if (_filterBy != null)
						newKey = _filterBy.invoke(newItem);
					else
						newKey = newItem;
					_filterIndex.set(filteredIndex, new ListItem(newKey, listIndex));
				}
				// raise event if appropriate
				if (filteredIndex > -1)
					onListChanged(
							new ListChangedEvent(this,
									e.getListChangedType(), filteredIndex, e.getPropertyDescriptor()));
				break;

			case ItemDeleted:
				listIndex = e.getNewIndex();
				// delete corresponding item from index
				// (if any)
				filteredIndex = filteredIndex(listIndex);
				if (filteredIndex != -1)
					_filterIndex.remove(filteredIndex);
				// adjust index xref values
				for (ListItem item : _filterIndex)
					if (item.getBaseIndex() > e.getNewIndex())
						item.setBaseIndex(item.getBaseIndex() - 1);
				// raise event if appropriate
				if (filteredIndex > -1)
					onListChanged(
							new ListChangedEvent(this,
									e.getListChangedType(), filteredIndex));
				break;

			case PropertyDescriptorAdded:
			case PropertyDescriptorChanged:
			case PropertyDescriptorDeleted:
				onListChanged(e);
				break;

			default:
				doFilter();
				onListChanged(
						new ListChangedEvent(this,
								ListChangedType.Reset, 0));
				break;
			}
		}
		else
			onListChanged(e);
	}

	/**
	 * Gets the source list over which this SortedBindingList is a view.
	 */
	public List<T> getSourceList(){
		return _list;
	}

	/**
	 * Returns True since this Object does raise the ListChanged event.
	 */
	public boolean supportsChangeNotification(){
		return true;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean supportsSearching(){
		if (_supportsBinding)
			return _bindingList.supportsSorting();
		else
			return false;
	}

	/**
	 * Returns True. Sorting is supported.
	 */
	public boolean supportsSorting(){
		if (_supportsBinding)
			return _bindingList.supportsSorting();
		else
			return false; 
	}

	/**
	 * Gets or sets the item at the specified index.
	 * 
	 *      @returns Item at the specified index.
	 * 
	 * @param index    Index of the item.
	 */
	//	public T this(int index){
	//		if (_filtered)
	//			_list[OriginalIndex(index)] = value;
	//		else
	//			_list[index] = value;
	//	}

	private void undoFilter(){
		_filterIndex.clear();
		_filterBy = null;
		_filter = null;
		_filtered = false;

		onListChanged(new ListChangedEvent(this, ListChangedType.Reset, 0));
	}

	@Override
	public boolean allowsEdit() {
		if (_supportsBinding)
			return _bindingList.allowsEdit();
		else
			return false;
	}

	@Override
	public boolean allowsNew() {
		if (_supportsBinding)
			return _bindingList.allowsNew();
		else
			return false;
	}

	@Override
	public boolean allowsRemove() {
		if (_supportsBinding)
			return _bindingList.allowsRemove();
		else
			return false;
	}

	@Override
	public void removeIndex(Method property) {
		if (_supportsBinding)
			_bindingList.removeIndex(property);
	}

	@Override
	public void removeSort() throws UnsupportedOperationException, NotSupportedException {
	      if (supportsSorting())
	          _bindingList.removeSort();
	        else
	          throw new NotSupportedException("Sorting not supported");
	      }

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return _list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return _list.addAll(index, c);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return _list.containsAll(c);
	}

	@Override
	public T get(int index) {
        if (_filtered)
        {
          int src = originalIndex(index);
          return _list.get(src);
        }
        else
          return _list.get(index);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return getEnumerator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return _list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return _list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return _list.listIterator(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return _list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return _list.retainAll(c);
	}

	@Override
	public T set(int index, T value) {
        if (_filtered)
            return _list.set(originalIndex(index), value);
          else
            return _list.set(index, value);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return _list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return _list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

}