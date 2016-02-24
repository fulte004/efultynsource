package Csla;
import java.util.ArrayList;

import Csla.Core.Savable;
import Csla.Core.SavedEvent;
import Csla.Core.SavedEventListener;
import Csla.Properties.Resources;
import Csla.Server.DataPortalException;
import Csla.Validation.ValidationException;

/**
 * This is the base class from which most business objects will be derived.
 * 
 *    @remark  This class is the core of the CSLA .NET framework. To create a
 * business object, inherit from this class.
 *     Please refer to 'Expert C# 2005 Business Objects' for full details on the
 * use of this base class to create business objects.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:26 PM
 */
public abstract class BusinessBase<T extends Csla.BusinessBase<T>> extends Csla.Core.BusinessBase implements Savable {

	private ArrayList<SavedEventListener> _savedListeners;

	/**
	 * Creates a clone of the object.
	 * 
	 *      @returns A new object containing the exact data of the original object.
	 */
	@SuppressWarnings("unchecked")
	public T clone(){
		return (T)GetClone();
	}

//	public Object save() throws ValidationException, SecurityException, NoSuchMethodException{
//		return save();
//	}

	/**
	 * 
	 * @param newObject
	 */
	@SuppressWarnings("unchecked")
	public void SaveComplete(Object newObject){
		OnSaved((T)newObject);
	}

	/**
	 * Compares this Object for equality with another Object, using the results of
	 * <see cref="GetIdValue"/> to determine equality.
	 * 
	 * @param obj    The Object to be compared.
	 */
	@SuppressWarnings("unchecked")
	public boolean Equals(Object obj){
		if (obj instanceof BusinessBase<?>)
		{
			Object id = GetIdValue();
			if (id == null)
				throw new IllegalArgumentException(Resources.getGetIdValueCantBeNull());
			return id.equals(((T)obj).GetIdValue());
		}
		else
			return false;
	}

	/**
	 * Returns a hash code value for this Object, based on the results of <see
	 * cref="GetIdValue"/>.
	 */
	public int GetHashCode(){
		Object id = GetIdValue();
		if (id == null)
			throw new IllegalArgumentException(Resources.getGetIdValueCantBeNull());
		return id.hashCode();
	}

	/**
	 * Override this method to return a unique identifying value for this Object.
	 * 
	 *      @remark If you can not provide a unique identifying value, it is best if
	 * you can generate such a unique value (even temporarily). If you can not do that,
	 * then return
	 *      <see langword="Nothing"/> and then manually override the
	 *      <see cref="Equals"/>, <see cref="GetHashCode"/> and
	 *      <see cref="ToString"/> methods in your business Object.
	 */
	protected abstract Object GetIdValue();

	/**
	 * Raises the Saved event, indicating that the Object has been saved, and
	 * providing a reference to the new Object instance.
	 * 
	 * @param newObject    The new Object instance.
	 */
	protected void OnSaved(T newObject){
		SavedEvent args = new SavedEvent(newObject);
		if (_savedListeners != null)
			for(SavedEventListener listener: _savedListeners)
				listener.onSaved(this, args);
	}

	/**
	 * Saves the Object to the database.
	 * @throws ValidationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 * 
	 *      @remark  Calling this method starts the save operation, causing the Object
	 * to be inserted, updated or deleted within the database based on the Object's
	 * current state.
	 *       If <see cref="Core.BusinessBase.IsDeleted" /> is <see langword="true"/>
	 * the Object will be deleted. Otherwise, if <see cref="Core.BusinessBase.IsNew"
	 * /> is <see langword="true"/> the Object will be inserted. Otherwise the
	 * Object's data will be updated in the database.
	 *       All this is contingent on <see cref="Core.BusinessBase.IsDirty" />. If
	 * this value is <see langword="false"/>, no data operation occurs. It is also
	 * contingent on <see cref="Core.BusinessBase.IsValid" />. If this value is <see
	 * langword="false"/> an exception will be thrown to indicate that the UI
	 * attempted to save an invalid Object.
	 *       It is important to note that this method returns a new version of the
	 * business Object that contains any data updated during the save operation. You
	 * MUST update all Object references to use this new version of the business
	 * Object in order to have access to the correct Object data.
	 *       You can override this method to add your own custom behaviors to the save
	 * operation. For instance, you may add some security checks to make sure the user
	 * can save the Object. If all security checks pass, you would then invoke the
	 * base Save method via <c>base.Save()</c>.
	 *      @returns A new Object containing the saved values.
	 */
	@SuppressWarnings("unchecked")
	public T save() throws ValidationException, SecurityException, NoSuchMethodException, DataPortalException{
		T result;
		if (this.isChild())
			throw new UnsupportedOperationException(Resources.getNoSaveChildException());
		if (getEditLevel() > 0)
			throw new ValidationException(Resources.getNoSaveEditingException());
		if (!isValid() && !isDeleted())
			throw new ValidationException(Resources.getNoSaveInvalidException());
		if (isDirty())
			result = (T)DataPortal.update(this);
		else
			result = (T)this;
		OnSaved(result);
		return result;
	}

	/**
	 * Saves the Object to the database, forcing IsNew to <see langword="false"/> and
	 * IsDirty to True.
	 * 
	 *      @returns A new Object containing the saved values.
	 *      @remark This overload is designed for use in web applications when
	 * implementing the Update method in your data wrapper Object.
	 * 
	 * @param forceUpdate    If <see langword="true"/>, triggers overriding IsNew and
	 * IsDirty. If <see langword="false"/> then it is the same as calling Save().
	 * @throws ValidationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 */
	public T save(boolean forceUpdate) throws ValidationException, SecurityException, NoSuchMethodException, DataPortalException{
		if (forceUpdate && isNew())
		{
			// mark the Object as old - which makes it
			// not dirty
			MarkOld();
			// now mark the Object as dirty so it can save
			MarkDirty(true);
		}
		return this.save();
	}

	/**
	 * INTERNAL CSLA .NET USE ONLY.
	 * 
	 * @param newObject    The new Object returned as a result of the save.
	 */
	public void saveComplete(Object newObject){

	}

	/**
	 * Event raised when an Object has been saved.
	 */
	public void addSavedListener(SavedEventListener listener){
		_savedListeners.add(listener);
	}
	public void removeSavedListener(SavedEventListener listener){
		_savedListeners.remove(listener);
	}
	/**
	 * Returns a text representation of this Object by returning the <see
	 * cref="GetIdValue"/> value in text form.
	 */
	public String toString(){
		Object id = GetIdValue();
		if (id == null)
			throw new IllegalArgumentException(Resources.getGetIdValueCantBeNull());
		return id.toString();
	}
}