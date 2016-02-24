package Csla.Core;

/**
 * Defines the interface that must be implemented by any business object that
 * contains child objects.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface Parent {

	/**
	 * Override this method to be notified when a child object's
	 *        <see cref="Core.BusinessBase.ApplyEdit" /> method has completed.
	 * 
	 * @param child    The child object that was edited.
	 */
	public void applyEditChild(EditableBusinessObject child);

	/**
	 * This method is called by a child object when it wants to be removed from the
	 * collection.
	 * 
	 * @param child    The child object to remove.
	 */
	public void removeChild(EditableBusinessObject child);

}