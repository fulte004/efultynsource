package Csla;

/**
 * Valid options for calling a property or method via the <see cref="Csla.
 * Utilities.CallByName"/> method.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:27 PM
 */
public enum CallType {
	/**
	 * Gets a value from a property.
	 * 
	 *      
	 */
	GET,
	/**
	 * Sets a value into a property.
	 * 
	 *      
	 */
	LET,
	/**
	 * Invokes a method.
	 * 
	 *      
	 */
	METHOD,
	/**
	 * Sets a value into a property.
	 * 
	 *      
	 */
	SET
}