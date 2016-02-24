package Csla.Core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import Csla.NotSupportedException;
import Csla.ComponentModel.BindingListListener;

/**
 * @author Eric
 * @version 1.0
 * @created 06-Jan-2010 9:48:19 PM
 */
public interface IBindingList<E> extends List<E> {

	/**
	 * 
	 * @param property
	 */
	public abstract void addIndex(Method property);

	/**
	 * Adds a new item to the collection.
	 * 
	 * @return The item added to the list.
	 * 
	 * @throws UnsupportedOperationException The AllowNew property is set to false.
	 * -or- A public default constructor could not be found for the current item
	 * type.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public abstract E addNew() throws UnsupportedOperationException, InstantiationException, IllegalAccessException;

	/**
	 * Gets whether you can update items in the list.
	 * @return true if you can update the items in the list; otherwise, false.
	 */
	public boolean allowsEdit();

	/**
	 * Gets whether you can add items to the list using IBindingList.addNew().
	 * @return true if you can add items to the list using IBindingList.addNew();
	 * otherwise, false.
	 */
	public boolean allowsNew();

	/**
	 * Gets whether you can remove items from the list, using List.remove(Object)
	 * or List.removeAt(int).
	 * @return true if you can remove items from the list; otherwise, false.
	 */
	public boolean allowsRemove();

	/**
	 * Sorts the list based on a PropertyDescriptor and a
	 * ListSortDirection.
	 * 
	 * @param property The System.ComponentModel.PropertyDescriptor to sort by.
	 * @param direction One of the System.ComponentModel.ListSortDirection values.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NotSupportedException 
	 * 
	 * @throws UnsupportedOperationException IBindingList.supportsSorting is false.
	 */
	public abstract void applySort(Method property, ListSortDirection direction) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NotSupportedException;

	/**
	 * Returns the index of the row that has the given java.beans.PropertyDescriptor.
	 * 
	 * @param property The java.beans.PropertyDescriptor to search on.
	 * @param key The value of the property parameter to search for.
	 * 
	 * @return The index of the row that has the given java.beans.PropertyDescriptor.
	 * 
	 * @throws UnsupportedOperationException IBindingList.supportsSearching is false.
	 */
	public abstract int find(Method property, Object key) throws UnsupportedOperationException;

	/**
	 * Gets whether the items in the list are sorted.
	 * 
	 * @return true if System.ComponentModel.IBindingList.ApplySort(PropertyDescriptor, ListSortDirection)
	 * has been called and System.ComponentModel.IBindingList.RemoveSort() has not
	 * been called; otherwise, false.
	 * 
	 * @throws UnsupportedOperationException IBindingList.supportsSorting is false.
	 */
	public boolean isSorted() throws UnsupportedOperationException;


	/**
	 * Occurs when the list changes or an item in the list changes.
	 * @param value
	 */
	public abstract void add_ListChanged(BindingListListener value);
	
	/**
	 * 
	 * @param value
	 */
	public abstract void remove_ListChanged(BindingListListener value);

	/**
	 * Removes the System.ComponentModel.PropertyDescriptor from the indexes used
	 * for searching.
	 * 
	 * @param property The java.beans.PropertyDescriptor to remove from the indexes used
	 * for searching.
	 */
	public abstract void removeIndex(Method property);

	/**
	 * Removes any sort applied using IBindingList.applySort(java.beans.PropertyDescriptor, ListSortDirection).
	 * 
	 * @throws UnsupportedOperationException IBindingList.supportsSorting is false.
	 * @throws NotSupportedException 
	 */
	public abstract void removeSort() throws UnsupportedOperationException, NotSupportedException;

	/**
	 * Gets the direction of the sort.
	 * 
	 * @return One of the System.ComponentModel.ListSortDirection values.
	 * 
	 * @throws UnsupportedOperationException IBindingList.supportsSorting is false.
	 */
	public ListSortDirection getSortDirection() throws UnsupportedOperationException;

	/**
	 * Gets the java.beans.PropertyDescriptor that is being used for
	 * sorting.
	 * 
	 * @return The System.ComponentModel.PropertyDescriptor that is being used for sorting.
	 * 
	 * @throws UnsupportedOperationException IBindingList.supportsSorting is false.
	 */
	public Method getSortProperty() throws UnsupportedOperationException;

	/**
	 * Gets whether a System.ComponentModel.IBindingList.ListChanged event is raised
	 * when the list changes or an item in the list changes.
	 * 
	 * @return true if a IBindingList.ListChanged event is raised
	 * when the list changes or when an item changes; otherwise, false.
	 */
	public boolean supportsChangeNotification();

	/**
	 * Gets whether the list supports searching using the IBindingList.find(PropertyDescriptor, Object)
	 * method.
	 * 
	 * @return true if the list supports searching using the IBindingList.find(PropertyDescriptor, Object)
	 * method; otherwise, false.
	 */
	public boolean supportsSearching();

	/**
	 * Gets whether the list supports sorting.
	 * 
	 * @return true if the list supports sorting; otherwise, false.
	 */
	public boolean supportsSorting();

}