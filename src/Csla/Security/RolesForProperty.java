package Csla.Security;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
/**
 * Maintains a list of allowed and denied user roles for a specific property.
 * 
 *      @remark
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:42 PM
 */
public class RolesForProperty {

	private List<String> _executeAllowed = new ArrayList<String>();
	private List<String> _executeDenied = new ArrayList<String>();
	private List<String> _readAllowed = new ArrayList<String>();
	private List<String> _readDenied = new ArrayList<String>();
	private List<String> _writeAllowed = new ArrayList<String>();
	private List<String> _writeDenied = new ArrayList<String>();

	/**
	 * Returns a List(Of String) containing the list of roles allowed execute access.
	 */
	public List<String> getExecuteAllowed(){
		return _executeAllowed;
	}

	/**
	 * Returns a List(Of String) containing the list of roles denied execute access.
	 */
	public List<String> getExecuteDenied(){
		return _executeDenied;
	}

	/**
	 * Returns True if the user is in a role explicitly allowed execute access.
	 * 
	 *        @returns True if the user is allowed execute access.
	 *        @remark
	 * 
	 * @param principal    A System.Security.Principal.IPrincipal representing the
	 * user.
	 */
	public boolean isExecuteAllowed(HttpServletRequest request){
		boolean result = false;
		for (String role : getExecuteAllowed())
		{
			if (request.isUserInRole(role))
			{
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Returns True if the user is in a role explicitly denied execute access.
	 * 
	 *        @returns True if the user is denied execute access.
	 *        @remark
	 * 
	 * @param request    A System.Security.Principal.IPrincipal representing the
	 * user.
	 */
	public boolean isExecuteDenied(HttpServletRequest request){
		boolean result = false;

		for (String role : getExecuteDenied())
		{
			if (request.isUserInRole(role))
			{
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is in a role explicitly allowed
	 * read access.
	 * 
	 *        @returns <see langword="true" /> if the user is allowed read access.
	 *        @remark
	 * 
	 * @param principal    A <see cref="System.Security.Principal.IPrincipal" />
	 * representing the user.
	 */
	public boolean IsReadAllowed(HttpServletRequest request){
		for (String role : getReadAllowed())
			if (request.isUserInRole(role))
				return true;
		return false;
	}

	/**
	 * Returns <see langword="true" /> if the user is in a role explicitly denied read
	 * access.
	 * 
	 *        @returns <see langword="true" /> if the user is denied read access.
	 *        @remark
	 * 
	 * @param principal    A <see cref="System.Security.Principal.IPrincipal" />
	 * representing the user.
	 */
	public boolean isReadDenied(HttpServletRequest request){
		for (String role : getReadDenied())
			if (request.isUserInRole(role))
				return true;
		return false;
	}

	/**
	 * Returns <see langword="true" /> if the user is in a role explicitly allowed
	 * write access.
	 * 
	 *        @returns <see langword="true" /> if the user is allowed write access.
	 *        @remark
	 * 
	 * @param principal    A <see cref="System.Security.Principal.IPrincipal" />
	 * representing the user.
	 */
	public boolean isWriteAllowed(HttpServletRequest request){
		for (String role : getWriteAllowed())
			if (request.isUserInRole(role))
				return true;
		return false;
	}

	/**
	 * Returns <see langword="true" /> if the user is in a role explicitly denied
	 * write access.
	 * 
	 *        @returns <see langword="true" /> if the user is denied write access.
	 *        @remark
	 * 
	 * @param principal    A <see cref="System.Security.Principal.IPrincipal" />
	 * representing the user.
	 */
	public boolean isWriteDenied(HttpServletRequest request){
		for (String role : getWriteDenied())
			if (request.isUserInRole(role))
				return true;
		return false;
	}

	/**
	 * Returns a List(Of String) containing the list of roles allowed read access.
	 */
	public List<String> getReadAllowed(){
		return _readAllowed; 
	}

	/**
	 * Returns a List(Of String) containing the list of roles denied read access.
	 */
	public List<String> getReadDenied(){
		return _readDenied; 
	}

	/**
	 * Returns a List(Of String) containing the list of roles allowed write access.
	 */
	public List<String> getWriteAllowed(){
		return _writeAllowed; 
	}

	/**
	 * Returns a List(Of String) containing the list of roles denied write access.
	 */
	public List<String> getWriteDenied(){
		return _writeDenied; 
	}

}