package Csla.Core;

/**
 * Defines the common methods required by all editable CSLA collection objects.
 * 
 *      @remark It is strongly recommended that the implementations of the methods
 * in this interface be made Private so as to not clutter up the native interface
 * of the collection objects.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:36 PM
 */
public interface EditableCollection extends BusinessObject, SupportUndo {

	/**
	 * Returns <see langword="true" /> if the collection or any child object's data in
	 * the collection has been changed.
	 * 
	 *        @returns A value indicating if this object's data has been changed.
	 */
	public boolean isDirty();

	/**
	 * Returns <see langword="true" /> if this object is both dirty and valid.
	 * 
	 *        @returns A value indicating if this object is both dirty and valid.
	 */
	public boolean isSavable();

	/**
	 * Returns <see langword="true" /> if all child objects are currently valid, <see
	 * langword="false" /> if any child object has broken rules or is otherwise
	 * invalid.
	 * 
	 *        @returns A value indicating if the object is currently valid.
	 */
	public boolean isValid();

	/**
	 * Removes the specified child from the parent collection.
	 * 
	 * @param child    Child object to be removed.
	 */
	public void RemoveChild(EditableBusinessObject child);

}