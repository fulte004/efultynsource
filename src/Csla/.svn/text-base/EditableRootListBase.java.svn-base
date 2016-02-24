package Csla;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import Csla.ComponentModel.ListChangedEvent;
import Csla.ComponentModel.ListChangedType;
import Csla.Core.BindableList;
import Csla.Core.EditableBusinessObject;
import Csla.Core.ObjectCloner;
import Csla.Core.Savable;
import Csla.Core.UndoException;
import Csla.Properties.Resources;
import Csla.Server.DataPortalException;
import Csla.Validation.ValidationException;

/**
 * This is the base class from which collections of editable root business objects
 * should be derived.
 * 
 *    @remark  Your subclass should implement a factory method and should override
 * or overload DataPortal_Fetch() to implement data retrieval.
 *     Saving (inserts or updates) of items in the collection should be handled
 * through the SaveItem() method on the collection.
 *     Removing an item from the collection through Remove() or RemoveAt() causes
 * immediate deletion of the object, by calling the object's Delete() and Save()
 * methods.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:34 PM
 */
public abstract class EditableRootListBase<T extends EditableBusinessObject> extends BindableList<T> implements Csla.Core.Parent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8126423695846034013L;
	private boolean _activelySaving;
	private static Collection<Method> _propertyDescriptors;
	private ArrayList<PropertyChangedListener> _propertyChangedListeners = new ArrayList<PropertyChangedListener>();


	/**
	 * Override this method to be notified when a child object's
	 *      <see cref="BusinessBase.ApplyEdit" /> method has completed.
	 * 
	 * @param child    The child object that was edited.
	 */
//	public void applyEditChild(EditableBusinessObject child){
//
//	}
	
	/**
	 * Adds an object that implements the Csla.PropertyChangedListener interface to the list of listeners for the PropertyChanged event.
	 * @param listener A target object which implements the Csla.PropertyChangedListener interface.
	 */
	public void add_PropertyChanged(PropertyChangedListener listener){
		_propertyChangedListeners.add(listener);
	}
	/**
	 * Removes an object that implements the Csla.PropertyChangedListener interface from the list of listeners for the PropertyChanged event.
	 * @param listener A target object which implements the Csla.PropertyChangedListener interface to be removed.
	 */
	public void remove_PropertyChanged(PropertyChangedListener listener){
		_propertyChangedListeners.remove(listener);
	}
	/**
	 * 
	 * @param sender
	 * @param e
	 */
	protected void child_PropertyChanged(PropertyChangedEvent e){
		for (int index = 0; index < this.size(); index++)
		      {
		        if (this.get(index).equals(e.getSource()))
		        {
		          Method descriptor = getPropertyDescriptor(e.getPropertyName());
		          if (descriptor != null)
		            onListChanged(new ListChangedEvent(this,
		              ListChangedType.ItemChanged, index, getPropertyDescriptor(e.getPropertyName())));
		          else
		            onListChanged(new ListChangedEvent(this,
		              ListChangedType.ItemChanged, index));
		          return;
		        }
		      }
	}

	/**
	 * 
	 * @param child
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * @throws DataPortalException 
	 */
	@SuppressWarnings("unchecked")
	public void applyEditChild(EditableBusinessObject child) {
		if (!_activelySaving && child.getEditLevel() == 0)
		        saveItem((T)child);
	}

	/**
	 * 
	 * @param child
	 */
//	public void removeChild(EditableBusinessObject child){
//		// do nothing, removal of a child is handled by
//		      // the RemoveItem override
//	}

	/**
	 * 
	 * @param criteria
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_create(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getCreateNotSupportedException());
	}

	/**
	 * 
	 * @param criteria
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_delete(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to allow retrieval of an existing business Object based on
	 * data in the database.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_fetch(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getFetchNotSupportedException());
	}

	/**
	 * Called by the server-side DataPortal if an exception occurs during data access.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 * @param ex    The Exception thrown during data access.
	 */
	protected void DataPortal_onDataPortalException(DataPortalEvent e, Exception ex){

	}

	/**
	 * Called by the server-side DataPortal prior to calling the requested
	 * DataPortal_xyz method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvoke(DataPortalEvent e){

	}

	/**
	 * Called by the server-side DataPortal after calling the requested DataPortal_xyz
	 * method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvokeComplete(DataPortalEvent e){

	}

	protected void DataPortal_Update() throws NotSupportedException{
		throw new NotSupportedException(Resources.getUpdateNotSupportedException());
	}

	/**
	 * 
	 * @param propertyName
	 */
	private Method getPropertyDescriptor(String propertyName){
		if (_propertyDescriptors == null)
			for (Method m : this.getClass().getMethods())
		        _propertyDescriptors.add(m);
		      Method result = null;
		      for (Method desc : _propertyDescriptors)
		        if (desc.getName() == propertyName)
		        {
		          result = desc;
		          break;
		        }
		      return result;
	}

	/**
	 * Gives the new Object a parent reference to this list.
	 * 
	 * @param index    Index at which to insert the item.
	 * @param item    Item to insert.
	 */
	protected void InsertItem(int index, T item){
		item.setParent(this);
		      super.add(index, item);
	}

	/**
	 * This method is called on a newly deserialized Object after deserialization is
	 * complete.
	 * 
	 * @param context    Serialization context Object.
	 */
//	protected void onDeserialized(StreamingContext context){
//		// do nothing - this is here so a subclass
//		      // could override if needed
//	}

	/**
	 * 
	 * @param context
	 */
//	private void onDeserializedHandler(StreamingContext context){
//		OnDeserialized(context);
//		      for (EditableBusinessObject child : this)
//		      {
//		        child.setParent(this);
//		        PropertyChangedListener c = (PropertyChangedListener)child;
//		        if (c != null)
//		        {
//		        	_propertyChangedListeners.add(c);
//		        }
//		      }
//	}

	/**
	 * This method is called by a child Object when it wants to be removed from the
	 * collection.
	 * 
	 * @param child    The child Object to remove.
	 */
	public void removeChild(EditableBusinessObject child){

	}

	/**
	 * Removes an item from the list.
	 * 
	 * @param index    Index of the item to be removed.
	 */
	protected void removeItem(int index){
		// delete item from database
		      T item = this.get(index);
		      
		      // only delete/save the item if it is not new
		      if (!item.isNew())
		      {
		        item.delete();
		        saveItem(index);
		      }
		      
		      // disconnect event handler if necessary
		      PropertyChangedListener c = (PropertyChangedListener)item;
		      if (c != null)
		      {
		    	  _propertyChangedListeners.remove(c);
		      }
		      
		      super.remove(index);
	}

	/**
	 * Saves the specified item in the list.
	 * 
	 *      @remark This method properly saves the child item, by making sure the item
	 * in the collection is properly replaced by the result of the Save() method call.
	 * 
	 * @param item    Reference to the item to be saved.
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * @throws DataPortalException 
	 */
	public void saveItem(T item){
		saveItem(indexOf(item));
	}

	/**
	 * Saves the specified item in the list.
	 * 
	 *      @remark This method properly saves the child item, by making sure the item
	 * in the collection is properly replaced by the result of the Save() method call.
	 * 
	 * @param index    Index of the item to be saved.
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * @throws ValidationException 
	 * @throws IOException 
	 * @throws DataPortalException 
	 */
	@SuppressWarnings("unchecked")
	public void saveItem(int index) {
		boolean raisingEvents = this.getRaiseListChangedEvents();
		      this.setRaiseListChangedEvents(false);
		      
		      _activelySaving = true;
		      
		      T item = this.get(index);
		      int editLevel = item.getEditLevel();
		      // commit all changes
		      for (int tmp = 1; tmp <= editLevel; tmp++)
				try {
					item.acceptChanges(editLevel - tmp);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (UndoException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		      try
		      {
		        T savable = item;
		        if (!ApplicationContext.getAutoCloneOnUpdate())
		        {
		          // clone the Object if possible
		          Cloneable clonable = (Cloneable)savable;
		          if (clonable != null)
		            savable = (T) ObjectCloner.clone(clonable);//(T)clonable.clone();
		        }
		      
		        // do the save
		        try {
					this.set(index, (T)savable.save());
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (ValidationException e) {
					e.printStackTrace();
				} catch (DataPortalException e) {
					e.printStackTrace();
				}
		      
		        if (!savable.equals(item) && !ApplicationContext.getAutoCloneOnUpdate())
		        {
		          // raise Saved event from original Object
		          Savable original = (Savable)item;
		          if (original != null)
		            original.saveComplete(this.get(index));
		        }
		      }
		      finally
		      {
		        // restore edit level to previous level
		        for (int tmp = 1; tmp <= editLevel; tmp++)
					try {
						item.copyState(tmp);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (UndoException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        _activelySaving = false;
		        this.setRaiseListChangedEvents(raisingEvents);
		      }
		      this.onListChanged(new ListChangedEvent(this, ListChangedType.ItemChanged, index));
	}

	/**
	 * Replaces item in the list.
	 * 
	 * @param index    Index of the item that was replaced.
	 * @param item    New item.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	protected void setItem(int index, T item) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		item.setParent(this);
		      super.setItem(index, item);
	}

}