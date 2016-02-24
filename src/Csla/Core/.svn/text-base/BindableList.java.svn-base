package Csla.Core;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Csla.ComponentModel.AddingNewEvent;
import Csla.ComponentModel.BindingListListener;
import Csla.ComponentModel.ListChangedEvent;
import Csla.Server.DataPortalException;
import Csla.Validation.ValidationException;

public class BindableList<E> extends ArrayList<E>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean _allowEdit;
	private boolean _allowNew;
	private boolean _allowRemove;
	private boolean _isSorted;
	private boolean _raiseListChangedEvents;
	private ListSortDirection _sortDirection;
	private boolean _supportsChangeNotification;
	private boolean _supportsSearching;
	private boolean _supportsSorting;
	private PropertyDescriptor _sortProperty;
	private ArrayList<BindingListListener> _listeners;
	/**
	 * Initializes a new instance of the csla.ComponentModel.BindingList<T> class
	 * using default values.
	 */
	public BindableList(){
		super();
		initialize();
	}
	//
	// Summary:
	//     Initializes a new instance of the System.ComponentModel.BindingList<T> class
	//     with the specified list.
	//
	// Parameters:
	//   list:
	//     An System.Collections.Generic.IList<T> of items to be contained in the System.ComponentModel.BindingList<T>.
	public BindableList(List<E> list){
		super(list);
		initialize();
	}
	
	private void initialize(){
		_listeners = new ArrayList<BindingListListener>();
	}
	
	public void addListener(BindingListListener l){
		_listeners.add(l);
	}
	
	public void removeListener(BindingListListener l){
		_listeners.remove(l);
	}
	
	protected void fireAddNew(Object o){
		for(BindingListListener listener : _listeners){
			listener.onAddingNew(new AddingNewEvent(this, o));
		}
	}
	
	protected void fireListChanged(ListChangedEvent e){
		for(BindingListListener l : _listeners){
			l.onListChanged(e);
		}
	}
	
	// Summary:
	//     Gets or sets a value indicating whether items in the list can be edited.
	//
	// Returns:
	//     true if list items can be edited; otherwise, false. The default is true.
	public boolean allowsEdit() { return _allowEdit; }
	public void setAllowsEdit(boolean value) { _allowEdit = value; }
	//
	// Summary:
	//     Gets or sets a value indicating whether you can add items to the list using
	//     the System.ComponentModel.BindingList<T>.AddNew() method.
	//
	// Returns:
	//     true if you can add items to the list with the System.ComponentModel.BindingList<T>.AddNew()
	//     method; otherwise, false. The default depends on the underlying type contained
	//     in the list.
	public boolean allowsNew() { return _allowNew; }
	public void setAllowsNew(boolean value) { _allowNew=value; }
	//
	// Summary:
	//     Gets or sets a value indicating whether you can remove items from the collection.
	//
	// Returns:
	//     true if you can remove items from the list with the System.ComponentModel.BindingList<T>.RemoveItem(System.Int32)
	//     method otherwise, false. The default is true.
	public boolean allowsRemove() { return _allowRemove; }
	public void setAllowsRemove(boolean value) { _allowRemove=value; }
	//
	// Summary:
	//     Gets a value indicating whether the list is sorted.
	//
	// Returns:
	//     true if the list is sorted; otherwise, false. The default is false.
	protected boolean isSorted() { return _isSorted; }
	//
	// Summary:
	//     Gets or sets a value indicating whether adding or removing items within the
	//     list raises System.ComponentModel.BindingList<T>.ListChanged events.
	//
	// Returns:
	//     true if adding or removing items raises System.ComponentModel.BindingList<T>.ListChanged
	//     events; otherwise, false. The default is true.
	public boolean getRaiseListChangedEvents() { return _raiseListChangedEvents; }
	public void setRaiseListChangedEvents(boolean value) { _raiseListChangedEvents=value; }
	//
	// Summary:
	//     Gets the direction the list is sorted.
	//
	// Returns:
	//     One of the System.ComponentModel.ListSortDirection values. The default is
	//     System.ComponentModel.ListSortDirection.Ascending.
	protected ListSortDirection getSortDirection() { return _sortDirection; }
	//
	// Summary:
	//     Gets the property descriptor that is used for sorting the list if sorting
	//     is implemented in a derived class; otherwise, returns null.
	//
	// Returns:
	//     The System.ComponentModel.PropertyDescriptor used for sorting the list.
	protected PropertyDescriptor getSortProperty() { 
		return _sortProperty; 
	}
	//
	// Summary:
	//     Gets a value indicating whether System.ComponentModel.BindingList<T>.ListChanged
	//     events are enabled.
	//
	// Returns:
	//     true if System.ComponentModel.BindingList<T>.ListChanged events are supported;
	//     otherwise, false. The default is true.
	protected boolean getSupportsChangeNotification() { return _supportsChangeNotification; }
	//
	// Summary:
	//     Gets a value indicating whether the list supports searching.
	//
	// Returns:
	//     true if the list supports searching; otherwise, false. The default is false.
	protected boolean getSupportsSearching() { return _supportsSearching; }
	//
	// Summary:
	//     Gets a value indicating whether the list supports sorting.
	//
	// Returns:
	//     true if the list supports sorting; otherwise, false. The default is false.
	protected boolean getSupportsSorting() { return _supportsSorting; }

	// Summary:
	//     Adds a new item to the collection.
	//
	// Returns:
	//     The item added to the list.
	//
	// Exceptions:
	//   System.InvalidOperationException:
	//     The System.Windows.Forms.BindingSource.AllowNew property is set to false.
	//     -or- A public default constructor could not be found for the current item
	//     type.
	public E addNew(){
		return null;
	}
	//
	// Summary:
	//     Adds a new item to the end of the collection.
	//
	// Returns:
	//     The item that was added to the collection.
	//
	// Exceptions:
	//   System.InvalidCastException:
	//     The new item is not the same type as the objects contained in the System.ComponentModel.BindingList<T>.
	protected Object addNewCore(){
		return null;
	}
	//
	// Summary:
	//     Sorts the items if overridden in a derived class; otherwise, throws a System.NotSupportedException.
	//
	// Parameters:
	//   prop:
	//     A System.ComponentModel.PropertyDescriptor that specifies the property to
	//     sort on.
	//
	//   direction:
	//     One of the System.ComponentModel.ListSortDirection values.
	//
	// Exceptions:
	//   System.NotSupportedException:
	//     Method is not overridden in a derived class.
	protected void applySort(PropertyDescriptor prop, ListSortDirection direction){
	}
	//
	// Summary:
	//     Discards a pending new item.
	//
	// Parameters:
	//   itemIndex:
	//     The index of the of the new item to be added
	public void cancelNew(int itemIndex){
		
	}
	//
	// Summary:
	//     Commits a pending new item to the collection.
	//
	// Parameters:
	//   itemIndex:
	//     The index of the new item to be added.
	public void endNew(int itemIndex){
	}
	//
	// Summary:
	//     Raises the System.ComponentModel.BindingList<T>.AddingNew event.
	//
	// Parameters:
	//   e:
	//     An System.ComponentModel.AddingNewEventArgs that contains the event data.
	protected void onAddingNew(AddingNewEvent e){
	}
	//
	// Summary:
	//     Raises the System.ComponentModel.BindingList<T>.ListChanged event.
	//
	// Parameters:
	//   e:
	//     A System.ComponentModel.ListChangedEventArgs that contains the event data.
	protected void onListChanged(ListChangedEvent e){
	}
	//
	// Summary:
	//     Removes the item at the specified index.
	//
	// Parameters:
	//   index:
	//     The zero-based index of the item to remove.
	//
	// Exceptions:
	//   System.NotSupportedException:
	//     You are removing a newly added item and System.ComponentModel.IBindingList.AllowRemove
	//     is set to false.
	protected void removeItem(int index) throws IllegalArgumentException, UndoException, IllegalAccessException, ValidationException, IOException, DataPortalException {
	}
	//
	// Summary:
	//     Removes any sort applied with System.ComponentModel.BindingList<T>.ApplySortCore(System.ComponentModel.PropertyDescriptor,System.ComponentModel.ListSortDirection)
	//     if sorting is implemented in a derived class; otherwise, raises System.NotSupportedException.
	//
	// Exceptions:
	//   System.NotSupportedException:
	//     Method is not overridden in a derived class.
	protected void removeSort() {
	}
	//
	// Summary:
	//     Raises a System.ComponentModel.BindingList<T>.ListChanged event of type System.ComponentModel.ListChangedType.Reset.
	public void resetBindings() {
	}
	//
	// Summary:
	//     Raises a System.ComponentModel.BindingList<T>.ListChanged event of type System.ComponentModel.ListChangedType.ItemChanged
	//     for the item at the specified position.
	//
	// Parameters:
	//   position:
	//     A zero-based index of the item to be reset.
	public void resetItem(int position) {
	}
	/**
	 * Replaces the item at the specified index with the specified item.
	 * @param index The zero-based index of the item to replace.
	 * @param item The new value for the item at the specified index. The value can be null
	 *     for reference types.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * @throws java.util.IndexOutOfRangeException index is less than zero -or- index is greater than java.util.ArrayList.size()
	 */
	protected void setItem(int index, E item) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException {
		super.remove(index);
		super.add(index, item);
	}
}
