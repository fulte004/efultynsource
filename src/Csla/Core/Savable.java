package Csla.Core;

import Csla.Server.DataPortalException;
import Csla.Validation.ValidationException;

/**
 * Specifies that the object can save itself.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface Savable {

	/**
	 * Saves the object to the database.
	 * @throws ValidationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 * 
	 *        @returns A new object containing the saved values.
	 */
	public Object save() throws ValidationException, SecurityException, NoSuchMethodException, DataPortalException;

	/**
	 * INTERNAL CSLA .NET USE ONLY.
	 * 
	 * @param newObject    The new object returned as a result of the save.
	 */
	public void saveComplete(Object newObject);

	/**
	 * Add listener for Event raised when an object has been saved. 
	 */
	public void addSavedListener(SavedEventListener listener);
	
	/**
	 * Remove listener for Event raised when an object has been saved.
	 */
	public void removeSavedLisenter(SavedEventListener listener);


}