package Csla;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Csla.ComponentModel.AddingNewEvent;
import Csla.ComponentModel.BindingListListener;
import Csla.ComponentModel.ListChangedEvent;
import Csla.ComponentModel.ListChangedType;
import Csla.ComponentModel.RemovingItemEvent;
import Csla.Core.IBindingList;
import Csla.Core.ListSortDirection;

/**
 * Provides a sorted view into an existing IList(Of T).
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:46 PM
 */
public class SortedBindingList<T> implements IBindingList<T>, Iterable<T>, CancelAddNew, BindingListListener {

	/**
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:46 PM
	 */
	private class ListItem implements Comparable<ListItem> {

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
		/**
		 * 
		 * @param other
		 */
		@SuppressWarnings("unchecked")
		public int compareTo(ListItem other){
			Object target = other.getKey();
			Comparable<ListItem> test = this;
			Class<?> testClass = test.getClass();
			
			if (testClass.isInstance(getKey()))
				return ((Comparable<ListItem>)getKey()).compareTo((ListItem) target);
			else
			{
				if (getKey() == null)
				{
					if (target == null)
						return 0;
					else
						return -1;
				}
				else if (getKey().equals(target))
					return 0;
				else
					return getKey().toString().compareTo(target.toString());
			}
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
	 * @created 21-Dec-2009 7:10:46 PM
	 */
	private class SortedEnumerator implements Iterable<T> {

		private int _index;
		private List<T> _list;
		private List<ListItem> _sortIndex;
		private ListSortDirection _sortOrder;

		/**
		 * 
		 * @param list
		 * @param sortIndex
		 * @param direction
		 */
		public SortedEnumerator(List<T> list, List<ListItem> sortIndex, ListSortDirection direction){
			_list = list;
			_sortIndex = sortIndex;
			_sortOrder = direction;
			Reset();
		}

		@SuppressWarnings("unused")
		protected Object getCurrent(){
			{ return _list.get(_sortIndex.get(_index).getBaseIndex()); }
		}

		@SuppressWarnings("unused")
		public T getCurrentT(){
			{ return _list.get(_sortIndex.get(_index).getBaseIndex()); }
		}

		@SuppressWarnings("unused")
		public boolean moveNext(){
			if (_sortOrder == ListSortDirection.ASCENDING)
			{
				if (_index < _sortIndex.size() - 1)
				{
					_index++;
					return true;
				}
				else
					return false;
			}
			else
			{
				if (_index > 0)
				{
					_index--;
					return true;
				}
				else
					return false;
			}
		}

		public void Reset(){
			if (_sortOrder == ListSortDirection.ASCENDING)
				_index = -1;
			else
				_index = _sortIndex.size();
		}

		@Override
		public Iterator<T> iterator() {
			return _list.iterator();
		}

	}

	private IBindingList<T> _bindingList;
	private boolean _initiatedLocally;
	private List<T> _list;
	private Method _sortBy;
	private boolean _sorted;
	private List<ListItem> _sortIndex = new ArrayList<ListItem>();
	private ListSortDirection _sortOrder = ListSortDirection.ASCENDING;
	private boolean _supportsBinding;
	private ArrayList<BindingListListener> _bindingListeners = new ArrayList<BindingListListener>();
	private boolean _readOnly = false;

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
	 * Creates a new view based on the provided IList Object.
	 * 
	 * @param list    The IList (collection) containing the data.
	 */
	public SortedBindingList(List<T> list){
		_list = list;
		IBindingList<T> test = this;
		if (_list.getClass().isInstance(test))
		{
			_supportsBinding = true;
			_bindingList = (IBindingList<T>)_list;
			_bindingList.add_ListChanged(this);
		}
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param item    Item to add to the list.
	 */
		public boolean add(T item){
			return _list.add(item);
		}

	/**
	 * Implemented by List source Object.
	 * 
	 * @param property    Property on which to build the index.
	 */
		public void addIndex(Method property){
			if (_supportsBinding)
			        _bindingList.addIndex(property);
		}

	/**
	 * Implemented by List source Object.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedOperationException 
	 */
	public T addNew() throws UnsupportedOperationException, InstantiationException, IllegalAccessException{
		T result;
		if (_supportsBinding)
		{
			_initiatedLocally = true;
			result = _bindingList.addNew();
			_initiatedLocally = false;
			onListChanged(new ListChangedEvent(this, ListChangedType.ItemAdded, _bindingList.size() - 1));
		}
		else
			result = null;

		return result;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean allowsEdit(){
		if (_supportsBinding)
			return _bindingList.allowsEdit();
		else
			return false;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean allowsNew(){
		if (_supportsBinding)
			return _bindingList.allowsNew();
		else
			return false;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean allowsRemove(){
		if (_supportsBinding)
			return _bindingList.allowsRemove();
		else
			return false;
	}

	/**
	 * Applies a sort to the view.
	 * 
	 * @param propertyName    The text name of the property on which to sort.
	 * @param direction    The direction to sort the data.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void applySort(String propertyName, ListSortDirection direction) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		_sortBy = null;

		if (!propertyName.isEmpty())
		{
			if(_list.size() > 0){
				T item = _list.get(0);
				Class<?> itemType = item.getClass();
				for (Method prop : itemType.getMethods())
				{
					if (prop.getName() == propertyName)
					{
						_sortBy = prop;
						break;
					}
				}
			}
		}

		applySort(_sortBy, direction);
	}

	/**
	 * Applies a sort to the view.
	 * 
	 * @param property    A PropertyDescriptor for the property on which to sort.
	 * @param direction    The direction to sort the data.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void applySort(Method property, ListSortDirection direction) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		_sortBy = property;
		_sortOrder = direction;
		doSort();
	}

	/**
	 * 
	 * @param itemIndex
	 */
	public void cancelNew(int itemIndex){
		CancelAddNew can = (CancelAddNew)_list;
		if (can != null)
			can.cancelNew(itemIndex);
		else
			_list.remove(itemIndex);
	}

	/**
	 * Implemented by IList source Object.
	 */
	public void clear(){
		_list.clear();
	}

	protected boolean isSynchronized(){
		return false; 
	}

	protected Object getSyncRoot(){
		return _list; 
	}

	public Iterator<T> iterator(){
		return getEnumerator();
	}

	/**
	 * 
	 * @param value
	 */
	public int addIndex(T value){
		add((T)value);
		return sortedIndex(_list.size() - 1);
	}

	/**
	 * 
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(Object value){
		return contains((T)value);
	}

	/**
	 * 
	 * @param index
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, Object value){
		_list.add(index, (T)value);
	}

	protected boolean isFixedSize(){
		return false; 
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param array    Array to receive the data.
	 * @param arrayIndex    Starting array index.
	 */
	public void copyTo(T[] array, int arrayIndex){
		int pos = arrayIndex;

		for (T child : this)
		{
			array[pos] = child;
			pos++;
		}
	}

	/**
	 * Implemented by IList source Object.
	 */
	public int size(){
		return _list.size(); 
	}

	private void doSort() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		int index = 0;
		_sortIndex.clear();

		if (_sortBy == null)
		{
			for (T obj : _list)
			{
				_sortIndex.add(new ListItem(obj, index));
				index++;
			}
		}
		else
		{
			for (T obj : _list)
			{
				_sortIndex.add(new ListItem(_sortBy.invoke(obj), index));
				index++;
			}
		}

		Collections.sort(_sortIndex);
		_sorted = true;

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
			return sortedIndex(_bindingList.find(property, key));
		else
			return -1;
	}

	/**
	 * Returns an enumerator for the list, honoring any sort that is active at the
	 * time.
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> getEnumerator(){
		if (_sorted)
			return (Iterator<T>) new SortedEnumerator(_list, _sortIndex, _sortOrder);
		else
			return _list.iterator();
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param item    Item for which to search.
	 */
	public int indexOf(Object item){
		return sortedIndex(_list.indexOf(item));
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param index    Index at which to insert the item.
	 * @param item    Item to insert.
	 */
	//	 public void Insert(int index, T item){
	//		 _list.Insert(index, item);
	//	 }

	/**
	 * Implemented by IList source Object.
	 */
	public boolean isReadOnly(){
		return _readOnly; 
	}

	/**
	 * Gets a value indicating whether the view is currently sorted.
	 */
	public boolean isSorted(){
		return _sorted; 
	}

	/**
	 * Raised to indicate that the list's data has changed.
	 * 
	 *      @remark This event is raised if the underling IList Object's data changes
	 * (assuming the underling IList also implements the IBindingList interface). It
	 * is also raised if the sort property or direction is changed to indicate that
	 * the view's data has changed. See Chapter 5 for details.
	 */
	//	 public ListChangedEventHandler ListChanged(){
	//		 return null;
	//	 }

	@Override
	public void onAddingNew(AddingNewEvent e) {
		if (_bindingListeners.size() > 0){
			for(BindingListListener listener : _bindingListeners){
				listener.onAddingNew(e);
			}
		}
	}

	/**
	 * Raises the <see cref="ListChanged"/> event.
	 * 
	 * @param e    Event arguments.
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
	 * @param sortedIndex
	 */
	private int OriginalIndex(int sortedIndex){
		if (_sorted)
		{
			if (_sortOrder == ListSortDirection.ASCENDING)
				return _sortIndex.get(sortedIndex).getBaseIndex();
			else
				return _sortIndex.get(_sortIndex.size() - 1 - sortedIndex).getBaseIndex();
		}
		else
			return sortedIndex;
	}

	/**
	 * Implemented by List source Object.
	 * 
	 * @param item    Item to be removed.
	 */
	@Override
	public boolean remove(Object item){
		return _list.remove(item);
	}

	/**
	 * Removes the child Object at the specified index in the list, resorting the
	 * display as needed.
	 * 
	 *      @remark See Chapter 5 for details on how and why the list is altered
	 * during the remove process.
	 * 
	 * @param index    The index of the Object to remove.
	 */
	public void removeAt(int index){
		if (_sorted)
		{
			_initiatedLocally = true;
			int baseIndex = OriginalIndex(index);

			// remove the item from the source list
			_list.remove(baseIndex);

			if (_list.size() != _sortIndex.size())
			{
				// delete the corresponding value in the sort index
				if (_sortOrder == ListSortDirection.ASCENDING)
					_sortIndex.remove(index);
				else
					_sortIndex.remove(_sortIndex.size() - 1 - index);

				// now fix up all index pointers in the sort index
				for (ListItem item : _sortIndex)
					if (item.getBaseIndex() > baseIndex)
						item.setBaseIndex(item.getBaseIndex()-1);
			}

			onListChanged(new ListChangedEvent(this,
					ListChangedType.ItemDeleted, index));
			_initiatedLocally = false;
		}
		else
			_list.remove(index);
	}

	/**
	 * Implemented by IList source Object.
	 * 
	 * @param property    Property for which the index should be removed.
	 */
	public void remove(Method property){
		if (_supportsBinding)
			_bindingList.remove(property);
	}

	/**
	 * Removes any sort currently applied to the view.
	 */
	public void removeSort(){
		undoSort();
	}

	/**
	 * Returns the direction of the current sort.
	 */
	public ListSortDirection getSortDirection(){
		return _sortOrder; 
	}

	/**
	 * 
	 * @param originalIndex
	 */
	private int sortedIndex(int originalIndex){
		int result = 0;
		if (_sorted)
		{
			for (int index = 0; index < _sortIndex.size(); index++)
			{
				if (_sortIndex.get(index).getBaseIndex() == originalIndex)
				{
					result = index;
					break;
				}
			}
			if (_sortOrder == ListSortDirection.DESCENDING)
				result = _sortIndex.size() - 1 - result;
		}
		else
			result = originalIndex;
		return result;
	}

	/**
	 * Returns the PropertyDescriptor of the current sort.
	 */
	public Method getSortProperty(){
		return _sortBy; 
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
		if (_sorted)
		{
			switch (e.getListChangedType())
			{
			case ItemAdded:
				T newItem = _list.get(e.getNewIndex());
				if (e.getNewIndex() == _list.size() - 1)
				{
					Object newKey;
					if (_sortBy != null)
						newKey = _sortBy.invoke(newItem);
					else
						newKey = newItem;

					if (_sortOrder == ListSortDirection.ASCENDING)
						_sortIndex.add(
								new ListItem(newKey, e.getNewIndex()));
					else
						_sortIndex.add(0, 
								new ListItem(newKey, e.getNewIndex()));
					if (!_initiatedLocally)
						onListChanged(
								new ListChangedEvent(this,
										ListChangedType.ItemAdded, 
										sortedIndex(e.getNewIndex())));
				}
				else
					doSort();
				break;

			case ItemChanged:
				// an item changed - just relay the event with
				// a translated index value
				onListChanged(
						new ListChangedEvent(this,
								ListChangedType.ItemChanged, sortedIndex(e.getNewIndex()), e.getPropertyDescriptor()));
				break;

			case ItemDeleted:
				if (!_initiatedLocally)
					doSort();
				break;

			default:
				// for anything other than add, delete or change
				// just re-sort the list
				if (!_initiatedLocally)
					doSort();
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
	 * Returns <see langword="true"/> since this Object does raise the ListChanged
	 * event.
	 */
	public boolean getSupportsChangeNotification(){
		return true;
	}

	/**
	 * Implemented by IList source Object.
	 */
	public boolean getSupportsSearching(){
		if (_supportsBinding)
			return _bindingList.supportsSearching();
		else
			return false;
	}

	/**
	 * Returns <see langword="true"/>. Sorting is supported.
	 */
	public boolean getSupportsSorting(){
		return true; 
	}

	/**
	 * Gets the child item at the specified index in the list, honoring the sort order
	 * of the items.
	 * 
	 * @param index    The index of the item in the sorted list.
	 */
	public T get(int index)
	{
		if (_sorted)
			return _list.get(OriginalIndex(index));
		else
			return _list.get(index);
	}

	public T set(int index, T value)
	{
		if (_sorted)
			return _list.set(OriginalIndex(index), value);
		else
			return _list.set(index, value) ;
	}

	private void undoSort(){
		_sortIndex.clear();
		_sortBy = null;
		_sortOrder = ListSortDirection.ASCENDING;
		_sorted = false;

		onListChanged(new ListChangedEvent(this, ListChangedType.Reset, 0));
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
	public boolean isEmpty() {
		return _list.isEmpty();
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
	public T remove(int index) {
		return _list.remove(index);
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

	@Override
	public void removeIndex(Method property) {
	      if (_supportsBinding)
	          _bindingList.removeIndex(property);
	      }

	@Override
	public boolean supportsChangeNotification() {
		return true;
	}

	@Override
	public boolean supportsSearching() {
        if (_supportsBinding)
            return _bindingList.supportsSearching();
          else
            return false;
	}

	@Override
	public boolean supportsSorting() {
		return true;
	}


}