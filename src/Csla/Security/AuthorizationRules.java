package Csla.Security;

import java.io.Serializable;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

/**
 * Maintains a list of allowed and denied user roles for each property.
 * 
 *      @remark
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:24 PM
 */
public class AuthorizationRules implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Type _businessObjectType;
	private AuthorizationRulesManager _instanceRules;
	private AuthorizationRulesManager _typeRules;

	/**
	 * Creates an instance of the object, initializing it with the business object
	 * type.
	 * 
	 * @param businessObjectType    Type of the business object to which the rules
	 * apply.
	 */
	public AuthorizationRules(Type businessObjectType){
		_businessObjectType = businessObjectType;
	}

	/**
	 * Specify the roles allowed to execute a given method.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of allowed roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param methodName    Name of the property.
	 * @param roles    List of roles granted execute access.
	 */
	public void allowExecute(String methodName, String[] roles){
		RolesForProperty currentRoles = getTypeRules().getRolesForProperty(methodName);
		for (String item : roles)
		{
			currentRoles.getExecuteAllowed().add(item);
		}
	}

	/**
	 * Specify the roles allowed to read a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of allowed roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles granted read access.
	 */
	public void allowRead(String propertyName, String[] roles){
		RolesForProperty currentRoles = getTypeRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getReadAllowed().add(item);
	}

	/**
	 * Specify the roles allowed to write a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of allowed roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles granted write access.
	 */
	public void allowWrite(String propertyName, String[] roles){
		RolesForProperty currentRoles = getTypeRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getWriteAllowed().add(item);
	}

	/**
	 * Specify the roles denied the right to execute a given method.
	 * 
	 *        @remark This method may be called multiple times, with the roles in each
	 * call being added to the end of the list of denied roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param methodName    Name of the property.
	 * @param roles    List of roles denied execute access.
	 */
	public void denyExecute(String methodName, String[] roles){
		RolesForProperty currentRoles = getTypeRules().getRolesForProperty(methodName);
		for (String item : roles)
		{
			currentRoles.getExecuteDenied().add(item);
		}
	}

	/**
	 * Specify the roles denied read access to a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of denied roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles denied read access.
	 */
	public void denyRead(String propertyName, String[] roles){
		RolesForProperty currentRoles = getTypeRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getReadDenied().add(item);
	}

	/**
	 * Specify the roles denied write access to a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of denied roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles denied write access.
	 */
	public void denyWrite(String propertyName, String[] roles){
		RolesForProperty currentRoles = getTypeRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getWriteDenied().add(item);
	}

	/**
	 * Indicates whether the property has a list of roles granted execute access.
	 * 
	 * @param methodName    Name of the method.
	 */
	public boolean hasExecuteAllowedRoles(String methodName){
		boolean result = false;
		if (getInstanceRules().getRolesForProperty(methodName).getExecuteAllowed().size() > 0)
		{
			result = true;

		}
		else
		{
			result = getTypeRules().getRolesForProperty(methodName).getExecuteAllowed().size() > 0;
		}

		return result;
	}

	/**
	 * Indicates whether the property has a list of roles denied execute access.
	 * 
	 * @param methodName    Name of the method.
	 */
	public boolean hasExecuteDeniedRoles(String methodName){
		boolean result = false;
		if (getInstanceRules().getRolesForProperty(methodName).getExecuteDenied().size() > 0)
		{
			result = true;

		}
		else
		{
			result = getTypeRules().getRolesForProperty(methodName).getExecuteDenied().size() > 0;
		}
		return result;
	}

	/**
	 * Indicates whether the property has a list of roles granted read access.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean hasReadAllowedRoles(String propertyName){
		if (getInstanceRules().getRolesForProperty(propertyName).getReadAllowed().size() > 0)
			return true;
		return getTypeRules().getRolesForProperty(propertyName).getReadAllowed().size() > 0;
	}

	/**
	 * Indicates whether the property has a list of roles denied read access.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean hasReadDeniedRoles(String propertyName){
		if (getInstanceRules().getRolesForProperty(propertyName).getReadDenied().size() > 0)
			return true;
		return getTypeRules().getRolesForProperty(propertyName).getReadDenied().size() > 0;
	}

	/**
	 * Indicates whether the property has a list of roles granted write access.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean hasWriteAllowedRoles(String propertyName){
		if (getInstanceRules().getRolesForProperty(propertyName).getWriteAllowed().size() > 0)
			return true;
		return getTypeRules().getRolesForProperty(propertyName).getWriteAllowed().size() > 0;
	}

	/**
	 * Indicates whether the property has a list of roles denied write access.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean hasWriteDeniedRoles(String propertyName){
		if (getInstanceRules().getRolesForProperty(propertyName).getWriteDenied().size() > 0)
			return true;
		return getTypeRules().getRolesForProperty(propertyName).getWriteDenied().size() > 0;
	}

	/**
	 * Specify the roles allowed to execute a given method.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of allowed roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param methodName    Name of the method.
	 * @param roles    List of roles granted read access.
	 */
	public void instanceAllowExecute(String methodName, String[] roles){
		RolesForProperty currentRoles = getInstanceRules().getRolesForProperty(methodName);
		for (String item : roles)
		{
			currentRoles.getExecuteAllowed().add(item);
		}
	}

	/**
	 * Specify the roles allowed to read a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of allowed roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles granted read access.
	 */
	public void instanceAllowRead(String propertyName, String[] roles){
		RolesForProperty currentRoles = getInstanceRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getReadAllowed().add(item);
	}

	/**
	 * Specify the roles allowed to write a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of allowed roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles granted write access.
	 */
	public void instanceAllowWrite(String propertyName, String[] roles){
		RolesForProperty currentRoles = getInstanceRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getWriteAllowed().add(item);
	}

	/**
	 * Specify the roles denied the right to execute a given method.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of denied roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param methodName    Name of the method.
	 * @param roles    List of roles denied read access.
	 */
	public void instanceDenyExecute(String methodName, String[] roles){
		RolesForProperty currentRoles = getInstanceRules().getRolesForProperty(methodName);
		for (String item : roles)
		{
			currentRoles.getExecuteDenied().add(item);
		}
	}

	/**
	 * Specify the roles denied read access to a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of denied roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles denied read access.
	 */
	public void instanceDenyRead(String propertyName, String[] roles){
		RolesForProperty currentRoles = getInstanceRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getReadDenied().add(item);
	}

	/**
	 * Specify the roles denied write access to a given property.
	 * 
	 *        @remark This method may be called multiple times, with the roles : each
	 * call being added to the end of the list of denied roles. In other words, each
	 * call is cumulative, adding more roles to the list.
	 * 
	 * @param propertyName    Name of the property.
	 * @param roles    List of roles denied write access.
	 */
	public void instanceDenyWrite(String propertyName, String[] roles){
		RolesForProperty currentRoles = getInstanceRules().getRolesForProperty(propertyName);
		for (String item : roles)
			currentRoles.getWriteDenied().add(item);
	}

	private AuthorizationRulesManager getInstanceRules(){

		if (_instanceRules == null)
			_instanceRules = new AuthorizationRulesManager();
		return _instanceRules;
	}

	/**
	 * Indicates whether the current user as defined by
	 *        <see cref="Csla.request.getUserPrincipal()" /> is explicitly allowed to
	 * execute the method.
	 * 
	 * @param methodName    Name of the method.
	 */
	public boolean isExecuteAllowed(String methodName, HttpServletRequest request){
		boolean result = false;
		if (getInstanceRules().getRolesForProperty(methodName).isExecuteAllowed(request))
		{
			result = true;
		}
		else
		{
			result = getTypeRules().getRolesForProperty(methodName).isExecuteAllowed(request);
		}
		return result;
	}

	/**
	 * Indicates whether the current user as defined by
	 *        <see cref="Csla.request.getUserPrincipal()" /> is explicitly denied execute
	 * access to the method.
	 * 
	 * @param methodName    Name of the method.
	 */
	public boolean isExecuteDenied(String methodName, HttpServletRequest request){
		boolean result = false;
		if (getInstanceRules().getRolesForProperty(methodName).isExecuteDenied(request))
		{
			result = true;

		}
		else
		{
			result = getTypeRules().getRolesForProperty(methodName).isExecuteDenied(request);
		}
		return result;
	}

	/**
	 * Indicates whether the current user as defined by
	 *        <see cref="Csla.request.getUserPrincipal()" /> is explicitly allowed to
	 * read the property.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean isReadAllowed(String propertyName, HttpServletRequest request){
		if (getInstanceRules().getRolesForProperty(propertyName).IsReadAllowed(request))
			return true;
		return getTypeRules().getRolesForProperty(propertyName).IsReadAllowed(request);
	}

	/**
	 * Indicates whether the current user as defined by
	 *        <see cref="Csla.request.getUserPrincipal()" /> is explicitly denied read
	 * access to the property.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean isReadDenied(String propertyName, HttpServletRequest request){
		if (getInstanceRules().getRolesForProperty(propertyName).isReadDenied(request))
			return true;
		return getTypeRules().getRolesForProperty(propertyName).isReadDenied(request);
	}

	/**
	 * Indicates whether the current user as defined by
	 *        <see cref="Csla.request.getUserPrincipal()" /> is explicitly allowed to set
	 * the property.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean isWriteAllowed(String propertyName, HttpServletRequest request){
		if (getInstanceRules().getRolesForProperty(propertyName).isWriteAllowed(request))
			return true;
		return getTypeRules().getRolesForProperty(propertyName).isWriteAllowed(request);
	}

	/**
	 * Indicates whether the current user as defined by
	 *        <see cref="Csla.request.getUserPrincipal()" /> is explicitly denied write
	 * access to the property.
	 * 
	 * @param propertyName    Name of the property.
	 */
	public boolean isWriteDenied(String propertyName, HttpServletRequest request){
		if (getInstanceRules().getRolesForProperty(propertyName).isWriteDenied(request))
			return true;
		return getTypeRules().getRolesForProperty(propertyName).isWriteDenied(request);
	}

	private AuthorizationRulesManager getTypeRules(){


		if (_typeRules == null)
			_typeRules = SharedAuthorizationRules.GetManager(_businessObjectType, true);
		return _typeRules;

	}

}