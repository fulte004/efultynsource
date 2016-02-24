package Csla.Security;

/**
 * Defines the authorization interface through which an object can indicate which
 * properties the current user can read and write.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:36 PM
 */
public interface AuthorizeReadWrite {

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the specified
	 * method.
	 * 
	 *        @returns <see langword="true" /> if execute is allowed.
	 * 
	 * @param methodName    Name of the method to execute.
	 */
	public boolean CanExecuteMethod(String methodName);

	/**
	 * Returns <see langword="true" /> if the user is allowed to read the specified
	 * property.
	 * 
	 *        @returns <see langword="true" /> if read is allowed.
	 * 
	 * @param propertyName    Name of the property to read.
	 */
	public boolean CanReadProperty(String propertyName);

	/**
	 * Returns <see langword="true" /> if the user is allowed to write the to the
	 * specified property.
	 * 
	 *        @returns <see langword="true" /> if write is allowed.
	 * 
	 * @param propertyName    Name of the property to read.
	 */
	public boolean CanWriteProperty(String propertyName);

}