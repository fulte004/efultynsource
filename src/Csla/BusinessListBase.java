package Csla;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Csla.ComponentModel.ListChangedEvent;
import Csla.ComponentModel.ListChangedType;
import Csla.Core.BindableList;
import Csla.Core.EditableBusinessObject;
import Csla.Core.EditableCollection;
import Csla.Core.ObjectCloner;
import Csla.Core.Parent;
import Csla.Core.Savable;
import Csla.Core.SavedEvent;
import Csla.Core.SavedEventListener;
import Csla.Core.UndoException;
import Csla.Core.UndoableObject;
import Csla.Properties.Resources;
import Csla.Server.DataPortalException;
import Csla.Validation.ValidationException;

/**
 * This is the base class from which most business collections or lists will be
 * derived.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:26 PM
 */
public abstract class BusinessListBase<T extends BusinessListBase<T, C>, C extends EditableBusinessObject> 
extends BindableList<C>
implements Savable, UndoableObject, EditableCollection, Parent, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6415142355979080700L;
	private boolean _completelyRemoveChild;
	private List<C> _deletedList;
	/**
	 * keep track of how many edit levels we have
	 */
	private int _editLevel;
	private boolean _isChild = false;
	private ArrayList<SavedEventListener> _savedListeners = new ArrayList<SavedEventListener>();
	private ArrayList<PropertyChangedListener> _propertyChangedListeners = new ArrayList<PropertyChangedListener>();
	private static Collection<Method> _propertyDescriptors;

	/**
	 * Creates an instance of the Object.
	 */
	protected BusinessListBase(){
		initialize();
	}
	
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
	 * @param parentEditLevel
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void acceptChanges(int parentEditLevel) throws UndoException, IllegalArgumentException, IllegalAccessException{
		if (this.getEditLevel() - 1 < parentEditLevel)
			throw new UndoException(String.format(Resources.getEditLevelMismatchException(), "AcceptChanges"));

		// we are coming up one edit level
		_editLevel -= 1;
		if (_editLevel < 0) _editLevel = 0;

		// cascade the call to all child objects
		BindableList<C> thisType = this;
		for (C child : thisType)
		{
			child.acceptChanges(_editLevel);
			// if item is below its point of addition, lower point of addition
			if (child.getEditLevelAdded() > _editLevel) child.setEditLevelAdded( _editLevel);
		}

		// cascade the call to all deleted child objects
		for (int index = getDeletedList().size() - 1; index >= 0; index--)
		{
			C child = getDeletedList().get(index);
			child.acceptChanges(_editLevel);
			// if item is below its point of addition, remove
			if (child.getEditLevelAdded() > _editLevel)
				getDeletedList().remove(index);
		}
	}

	/**
	 * Commits the current edit process.
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * 
	 *      @remark Calling this method causes the most recently taken snapshot of the
	 * Object's state to be discarded, thus committing any changes made to the
	 * Object's state since the last
	 *      <see cref="BeginEdit" /> call.
	 *       This method triggers an <see cref="Core.BusinessBase.ApplyEdit"/> in all
	 * child objects.
	 */
	public void applyEdit() throws IllegalArgumentException, UndoException, IllegalAccessException{
		if (this.isChild())
			throw new UnsupportedOperationException(Resources.getNoApplyEditChildException());

		acceptChanges(this.getEditLevel() - 1);
	}

	/**
	 * Override this method to be notified when a child Object's
	 *      <see cref="Core.BusinessBase.ApplyEdit" /> method has completed.
	 * 
	 * @param child    The child Object that was edited.
	 */
//	public void applyEditChild(EditableBusinessObject child){
//
//	}

	/**
	 * Starts a nested edit on the Object.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * 
	 *      @remark  When this method is called the Object takes a snapshot of its
	 * current state (the values of its variables). This snapshot can be restored by
	 * calling <see cref="CancelEdit" /> or committed by calling <see cref="ApplyEdit"
	 * />.
	 *       This is a nested operation. Each call to BeginEdit adds a new snapshot of
	 * the Object's state to a stack. You should ensure that for each call to
	 * BeginEdit there is a corresponding call to either CancelEdit or ApplyEdit to
	 * remove that snapshot from the stack.
	 *       See Chapters 2 and 3 for details on n-level undo and state stacking.
	 *       This method triggers the copying of all child Object states.
	 */
	public void beginEdit() throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		if (this.isChild())
			throw new UnsupportedOperationException(Resources.getNoBeginEditChildException());

		copyState(this.getEditLevel() + 1);
	}

	/**
	 * Cancels the current edit process, restoring the Object's state to its previous
	 * values.
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * 
	 *      @remark Calling this method causes the most recently taken snapshot of the
	 * Object's state to be restored. This resets the Object's values to the point of
	 * the last <see cref="BeginEdit" /> call.
	 *       This method triggers an undo in all child objects.
	 */
	public void cancelEdit() throws IllegalArgumentException, IllegalAccessException, UndoException, IOException{
		if (this.isChild())
			throw new UnsupportedOperationException(Resources.getNoCancelEditChildException());

		undoChanges(this.getEditLevel() - 1);
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	@SuppressWarnings("unused")
	private void Child_PropertyChanged(Object sender, PropertyChangedEvent e){
		if (getRaiseListChangedEvents())
		{
			for (int index = 0; index < size(); index++)
			{
				if (this.get(index).equals(sender))
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
	}

	/**
	 * Clears the collection, moving all active items to the deleted list.
	 */
	public void clear(){
		while (super.size() > 0)
			try {
				removeItem(0);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		super.clear();
	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *      @returns A new Object containing the exact data of the original Object.
	 */
	@SuppressWarnings("unchecked")
	public T clone(){
		return (T)getClone();
	}

	/**
	 * Returns <see langword="true"/> if the internal deleted list contains the
	 * specified child Object.
	 * 
	 * @param item    Child Object to check.
	 */
	public boolean containsDeleted(C item){
		return getDeletedList().contains(item);
	}

	/**
	 * 
	 * @param parentEditLevel
	 * @throws UndoException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void copyState(int parentEditLevel) throws UndoException, IllegalArgumentException, IllegalAccessException, IOException{
		if (this.getEditLevel() + 1 > parentEditLevel)
			throw new UndoException(String.format(Resources.getEditLevelMismatchException(), "CopyState"));

		// we are going a level deeper in editing
		_editLevel += 1;

		// cascade the call to all child objects
		for (C child : this)
			child.copyState(_editLevel);

		// cascade the call to all deleted child objects
		for (C child : getDeletedList())
			child.copyState(_editLevel);
	}

	/**
	 * 
	 * @param child
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	private void copyToDeletedList(C child) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		deleteChild(child);
		NotifyPropertyChanged c = (NotifyPropertyChanged)child;
		if (c != null)
			_propertyChangedListeners.remove(c);
	}

//	public Object save(){
//		return save();
//	}

	/**
	 * 
	 * @param newObject
	 */
	@SuppressWarnings("unchecked")
	public void saveComplete(Object newObject){
		onSaved((T)newObject);
	}

	/**
	 * Override this method to load a new business Object with default values from the
	 * datasuper.
	 */
	protected void DataPortal_Create(){
		throw new UnsupportedOperationException(Resources.getCreateNotSupportedException());
	}

	/**
	 * Override this method to allow immediate deletion of a business Object.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 */
	protected void DataPortal_Delete(Object criteria){
		throw new UnsupportedOperationException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to allow retrieval of an existing business Object based on
	 * data in the datasuper.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 */
	protected void DataPortal_Fetch(Object criteria){
		throw new UnsupportedOperationException(Resources.getFetchNotSupportedException());
	}

	/**
	 * Called by the server-side DataPortal if an exception occurs during data access.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 * @param ex    The Exception thrown during data access.
	 */
	protected void DataPortal_OnDataPortalException(DataPortalEvent e, Exception ex){

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

	/**
	 * Override this method to allow update of a business Object.
	 */
	protected void DataPortal_Update(){
		throw new UnsupportedOperationException(Resources.getUpdateNotSupportedException());
	}

	/**
	 * 
	 * @param child
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	private void deleteChild(C child) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		// set child edit level
		resetChildEditLevel(child, this.getEditLevel());
		// mark the Object as deleted
		child.deleteChild();
		// and add it to the deleted collection for storage
		getDeletedList().add(child);
	}

	/**
	 * A collection containing all child objects marked for deletion.
	 */
	protected List<C> getDeletedList(){
		if (_deletedList == null)
			_deletedList = new ArrayList<C>();
		return _deletedList; 
	}

	/**
	 * Override this method to be notified when a child Object's
	 *      <see cref="Core.BusinessBase.ApplyEdit" /> method has completed.
	 * 
	 * @param child    The child Object that was edited.
	 */
	protected void EditChildComplete(EditableBusinessObject child){
		// do nothing, we don't really care
		// when a child has its edits applied
	}

	/**
	 * Returns the current edit level of the Object.
	 */
	protected int getEditLevel(){
		return _editLevel; 
	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *      @returns A new Object containing the exact data of the original Object.
	 */
	protected Object getClone(){
		return ObjectCloner.clone(this);
	}

	/**
	 * 
	 * @param propertyName
	 */
	private Method getPropertyDescriptor(String propertyName){
		if (_propertyDescriptors == null)
		{
			Class<?> temp = super.get(0).getClass();
			for(Method m : temp.getMethods())
				_propertyDescriptors.add(m);
		}
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
	 * This method is called by a child Object when it wants to be removed from the
	 * collection.
	 * 
	 * @param child    The child Object to remove.
	 */
	public void removeChild(EditableBusinessObject child){
		remove(child);
	}

	/**
	 * Override this method to set up event handlers so user code in a partial class
	 * can respond to events raised by generated code.
	 */
	protected void initialize(){
		/* allows subclass to initialize events before any other activity occurs */
	}

	/**
	 * Sets the edit level of the child Object as it is added.
	 * 
	 * @param index    Index of the item to insert.
	 * @param item    Item to insert.
	 */
	public void add(int index, C item){
		// set parent reference
		item.setParent(this);
		// set child edit level
		try {
			resetChildEditLevel(item, this.getEditLevel());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UndoException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// when an Object is inserted we assume it is
		// a new Object and so the edit level when it was
		// added must be set
		item.setEditLevelAdded(_editLevel);
		super.add(index, item);
	}

	/**
	 * 
	 * @param child
	 */
	public void applyEditChild(EditableBusinessObject child){
		EditChildComplete(child);
	}

	/**
	 * This method is called by a child Object when it wants to be removed from the
	 * collection.
	 * 
	 * @param child    The child Object to remove.
	 */
//	public void removeChild(EditableBusinessObject child){
//		remove(child);
//	}

	/**
	 * Indicates whether this collection Object is a child Object.
	 * 
	 *      @returns True if this is a child Object.
	 */
	protected boolean isChild(){
		return _isChild; 
	}

	/**
	 * Gets a value indicating whether this Object's data has been changed.
	 */
	public boolean isDirty(){

		// any non-new deletions make us dirty
		for (C item : getDeletedList())
			if (!item.isNew())
				return true;

		// run through all the child objects
		// and if any are dirty then then
		// collection is dirty
		for (C child : this)
			if (child.isDirty())
				return true;
		return false;
	}

	/**
	 * Returns <see langword="true" /> if this Object is both dirty and valid.
	 * 
	 *      @returns A value indicating if this Object is both dirty and valid.
	 */
	public boolean isSavable(){
		return (isDirty() && isValid()); 
	}

	/**
	 * Gets a value indicating whether this Object is currently in a valid state (has
	 * no broken validation rules).
	 */
	public boolean isValid(){
			// run through all the child objects
			// and if any are invalid then the
			// collection is invalid
			for (C child : this)
				if (!child.isValid())
					return false;
			return true;
	}



	/**
	 * Marks the Object as being a child Object.
	 * 
	 *      @remark  By default all business objects are 'parent' objects. This means
	 * that they can be directly retrieved and updated into the datasuper.
	 *       We often also need child objects. These are objects which are contained
	 * within other objects. For instance, a parent Invoice Object will contain child
	 * LineItem objects.
	 *       To create a child Object, the MarkAsChild method must be called as the
	 * Object is created. Please see Chapter 7 for details on the use of the
	 * MarkAsChild method.
	 */
	protected void MarkAsChild(){
		_isChild = true;
	}

	/**
	 * This method is called on a newly deserialized Object after deserialization is
	 * complete.
	 * 
	 * @param context
	 */
	//	protected void OnDeserialized(StreamingContext context){
	//		// do nothing - this is here so a subclass
	//		// could override if needed
	//	}
	//
	//	/**
	//	 * 
	//	 * @param context
	//	 */
	//	private void OnDeserializedHandler(StreamingContext context){
	//		OnDeserialized(context);
	//		for (EditableBusinessObject child : this)
	//		{
	//			child.setParent(this);
	//			NotifyPropertyChanged c = (NotifyPropertyChanged)child;
	//			if (c != null)
	//				c.PropertyChanged += new PropertyChangedEventHandler(Child_PropertyChanged);
	//		}
	//		for (Core.IEditableBusinessObject child : getDeletedList())
	//			child.SetParent(this);
	//	}

	/**
	 * Raises the <see cref="Saved"/> event, indicating that the Object has been saved,
	 * and providing a reference to the new Object instance.
	 * 
	 * @param newObject    The new Object instance.
	 */
	public void onSaved(T newObject){
		SavedEvent args = new SavedEvent(newObject);
		if (!_savedListeners.isEmpty())
			for(SavedEventListener listener : _savedListeners)
				listener.onSaved(this, args);
	}

	/**
	 * Removes the specified child from the parent collection.
	 * 
	 * @param child    Child Object to be removed.
	 */
//	public void removeChild(EditableBusinessObject child){
//
//	}

	/**
	 * This method is called by a child Object when it wants to be removed from the
	 * collection.
	 * 
	 * @param child    The child Object to remove.
	 */
//	public void removeChild(EditableBusinessObject child){
//
//	}

	/**
	 * Marks the child Object for deletion and moves it to the collection of deleted
	 * objects.
	 * 
	 * @param index    Index of the item to remove.
	 */
	protected void removeItem(int index){
		// when an Object is 'removed' it is really
		// being deleted, so do the deletion work
		C child = this.get(index);
		boolean oldRaiseListChangedEvents = this.getRaiseListChangedEvents();
		try
		{
			this.setRaiseListChangedEvents(false);
			super.remove(index);
		}
		finally
		{
			this.setRaiseListChangedEvents(oldRaiseListChangedEvents);
		}
		if (!_completelyRemoveChild)
		{
			// the child shouldn't be completely removed,
			// so copy it to the deleted list
			try {
				copyToDeletedList(child);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (UndoException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (getRaiseListChangedEvents())
			onListChanged(new ListChangedEvent(this, ListChangedType.ItemDeleted, index));
	}

	/**
	 * 
	 * @param child
	 * @param parentEditLevel
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	private void resetChildEditLevel(C child, int parentEditLevel) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		// if item's edit level is too high,
		// reduce it to match list
		while (child.getEditLevel() > parentEditLevel)
			child.acceptChanges(parentEditLevel);
		// if item's edit level is too low,
		// increase it to match list
		while (child.getEditLevel() < parentEditLevel)
			child.copyState(parentEditLevel);
	}

	/**
	 * Saves the Object to the datasuper.
	 * @throws ValidationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 * 
	 *      @remark  Calling this method starts the save operation, causing the all
	 * child objects to be inserted, updated or deleted within the database based on
	 * the each Object's current state.
	 *       All this is contingent on <see cref="IsDirty" />. If this value is <see
	 * langword="false"/>, no data operation occurs. It is also contingent on <see
	 * cref="IsValid" />. If this value is
	 *      <see langword="false"/> an exception will be thrown to indicate that the
	 * UI attempted to save an invalid Object.
	 *       It is important to note that this method returns a new version of the
	 * business collection that contains any data updated during the save operation.
	 * You MUST update all Object references to use this new version of the business
	 * collection in order to have access to the correct Object data.
	 *       You can override this method to add your own custom behaviors to the save
	 * operation. For instance, you may add some security checks to make sure the user
	 * can save the Object. If all security checks pass, you would then invoke the
	 * base Save method via <c>MyBase.Save()</c>.
	 *      @returns A new Object containing the saved values.
	 */
	@SuppressWarnings("unchecked")
	public T save() throws ValidationException, SecurityException, NoSuchMethodException, DataPortalException{
		T result;
		if (this.isChild())
			throw new UnsupportedOperationException(Resources.getNoApplyEditChildException());

		if (_editLevel > 0)
			throw new ValidationException(Resources.getNoSaveEditingException());

		if (!isValid())
			throw new ValidationException(Resources.getNoSaveInvalidException());

		if (isDirty())
			result = (T)DataPortal.update(this);
		else
			result = (T)this;
		onSaved(result);
		return result;
	}

	/**
	 * Replaces the item at the specified index with the specified item, first moving
	 * the original item to the deleted list.
	 * 
	 *      @remark
	 * 
	 * @param index    The zero-based index of the item to replace.
	 * @param item    The new value for the item at the specified index. The value can
	 * be null for reference types.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	protected void setItem(int index, C item) throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		C child = null;
		if (!this.get(index).equals(item))
			child = this.get(index);
		// replace the original Object with this new
		// Object
		boolean oldRaiseListChangedEvents = this.getRaiseListChangedEvents();
		try
		{
			this.setRaiseListChangedEvents(false);
			// set parent reference
			item.setParent(this);
			// set child edit level
			resetChildEditLevel(item, this.getEditLevel());
			// reset EditLevelAdded 
			item.setEditLevelAdded(this.getEditLevel());
			// add to list
			super.setItem(index, item);
		}
		finally
		{
			this.setRaiseListChangedEvents(oldRaiseListChangedEvents);
		}
		if (child != null)
			copyToDeletedList(child);
		if (getRaiseListChangedEvents())
			onListChanged(new ListChangedEvent(this, ListChangedType.ItemChanged, index));
	}

	/**
	 * 
	 * @param child
	 */
	private void UnDeleteChild(C child){
		// since the Object is no longer deleted, remove it from
		// the deleted collection
		getDeletedList().remove(child);

		// we are inserting an _existing_ Object so
		// we need to preserve the Object's editleveladded value
		// because it will be changed by the normal add process
		int saveLevel = child.getEditLevelAdded();
		add(child);
		child.setEditLevelAdded(saveLevel);
	}

	/**
	 * 
	 * @param parentEditLevel
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	public void undoChanges(int parentEditLevel) throws IllegalArgumentException, IllegalAccessException, UndoException, IOException{
		C child;

		if (this.getEditLevel() - 1 < parentEditLevel)
			throw new UndoException(String.format(Resources.getEditLevelMismatchException(), "UndoChanges"));

		// we are coming up one edit level
		_editLevel -= 1;
		if (_editLevel < 0) _editLevel = 0;

		boolean oldRLCE = this.getRaiseListChangedEvents();
		this.setRaiseListChangedEvents(false);
		try
		{
			// Cancel edit on all current items
			for (int index = size() - 1; index >= 0; index--)
			{
				child = this.get(index);
				child.undoChanges(_editLevel);
				// if item is below its point of addition, remove
				if (child.getEditLevelAdded() > _editLevel)
				{
					boolean oldAllowRemove = this.allowsRemove();
					try
					{
						this.setAllowsRemove(true);
						_completelyRemoveChild = true;
						remove(index);
					}
					finally
					{
						_completelyRemoveChild = false;
						this.setAllowsRemove(oldAllowRemove);
					}
				}
			}

			// cancel edit on all deleted items
			for (int index = getDeletedList().size() - 1; index >= 0; index--)
			{
				child = getDeletedList().get(index);
				child.undoChanges(_editLevel);
				if (child.getEditLevelAdded() > _editLevel)
				{
					// if item is below its point of addition, remove
					getDeletedList().remove(index);
				}
				else
				{
					// if item is no longer deleted move back to main list
					if (!child.isDeleted()) UnDeleteChild(child);
				}
			}
		}
		finally
		{
			this.setRaiseListChangedEvents(oldRLCE);
			onListChanged(new ListChangedEvent(this, ListChangedType.Reset, -1));
		}
	}

}