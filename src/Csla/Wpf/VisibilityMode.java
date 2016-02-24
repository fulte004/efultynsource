package Csla.Wpf;

/**
 * Options controlling how the Authorizer control alters the visibility of a
 * control when read access to the property is not allowed.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:49 PM
 */
public enum VisibilityMode {
	/**
	 * Specifies that the non-readable control should be collapsed.
	 * 
	 *        
	 */
	Collapsed,
	/**
	 * Specifies that the non-readable control should be hidden.
	 * 
	 *        
	 */
	Hidden,
	/**
	 * Specifies that the visibility of the non-readable control should not be altered
	 * by the Authorizer control.
	 * 
	 *        
	 */
	Ignore
}