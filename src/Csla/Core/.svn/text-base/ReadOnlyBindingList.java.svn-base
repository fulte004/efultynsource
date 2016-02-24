package Csla.Core;

import java.io.IOException;

import Csla.Properties.Resources;
import Csla.Server.DataPortalException;
import Csla.Validation.ValidationException;

/**
 * A readonly version of BindingList(Of T)
 * 
 *      @remark This is a subclass of BindingList(Of T) that implements a readonly
 * list, preventing adding and removing of items from the list. Use the IsReadOnly
 * property to unlock the list for loading/unloading data.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:40 PM
 */
public abstract class ReadOnlyBindingList<C> extends BindableList<C> implements BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean _isReadOnly = true;


	/**
	 * Creates an instance of the object.
	 */
	protected ReadOnlyBindingList(){
		this.setRaiseListChangedEvents(false);
		setAllowsEdit(false);
		setAllowsRemove(false);
		setAllowsNew(false);
		this.setRaiseListChangedEvents(true);
	}

	/**
	 * Prevents insertion of items into the collection.
	 */
	protected Object addNewCore(){
		if (!isReadOnly())
			return super.addNewCore();
		else
			throw new UnsupportedOperationException(Resources.getInsertInvalidException());
	}

	/**
	 * Prevents clearing the collection.
	 */
	protected void clearItems(){
		if (!isReadOnly())
		{
			boolean oldValue = allowsRemove();
			setAllowsRemove(true);
			super.clear();
			setAllowsRemove(oldValue);
		}
		else
			throw new UnsupportedOperationException(Resources.getClearInvalidException());
	}

	/**
	 * Prevents insertion of items into the collection.
	 * 
	 * @param index    Index at which to insert the item.
	 * @param item    Item to insert.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	protected void insertItem(int index, C item) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		if (!isReadOnly())
			super.setItem(index, item);
		else
			throw new UnsupportedOperationException(Resources.getInsertInvalidException());
	}

	/**
	 * Gets or sets a value indicating whether the list is readonly.
	 * 
	 *        @remark Subclasses can set this value to unlock the collection in order
	 * to alter the collection's data.
	 *        @value True indicates that the list is readonly.
	 */
	public boolean isReadOnly(){
		return _isReadOnly; 

	}
	public void setIsReadOnly(boolean value){
		_isReadOnly = value; 
	}
	/**
	 * Removes the item at the specified index if the collection is not in readonly
	 * mode.
	 * 
	 * @param index    Index of the item to remove.
	 * @throws DataPortalException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	protected void removeItem(int index) throws IllegalArgumentException, UndoException, IllegalAccessException, ValidationException, IOException, DataPortalException{
		if (!isReadOnly())
		{
			boolean oldValue = allowsRemove();
			setAllowsRemove(true);
			super.removeItem(index);
			setAllowsRemove(oldValue);
		}
		else
			throw new UnsupportedOperationException(Resources.getRemoveInvalidException());
	}

	/**
	 * Replaces the item at the specified index with the specified item if the
	 * collection is not in readonly mode.
	 * 
	 * @param index    Index of the item to replace.
	 * @param item    New item for the list.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	protected void setItem(int index, C item) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		if (!isReadOnly())
			super.setItem(index, item);
		else
			throw new UnsupportedOperationException(Resources.getChangeInvalidException());
	}

}