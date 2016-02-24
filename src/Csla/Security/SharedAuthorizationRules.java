package Csla.Security;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Maintains a list of all the per-type
 *      <see cref="AuthorizationRulesManager"/> objects loaded in memory.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:44 PM
 */
public class SharedAuthorizationRules {

	private static Map<Type, AuthorizationRulesManager> _managers = new HashMap<Type, AuthorizationRulesManager>();

	public SharedAuthorizationRules(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Gets the <see cref="AuthorizationRulesManager"/> for the specified object type,
	 * optionally creating a new instance of the object if necessary.
	 * 
	 * @param objectType    Type of business object for which the rules apply.
	 * @param create    Indicates whether to create a new instance of the object if
	 * one doesn't exist.
	 */
	public static AuthorizationRulesManager GetManager(Type objectType, boolean create){
		AuthorizationRulesManager result = null;
		if (!(_managers.get(objectType)==null) && create)
		{
			synchronized (_managers)
			{
				if (!(_managers.get(objectType)==null))
				{
					result = new AuthorizationRulesManager();
					_managers.put(objectType, result);
				}
			}
		}
		return result;
	}

	/**
	 * Gets a value indicating whether a set of rules have been created for a given
	 * <see cref="Type" />.
	 * 
	 *        @returns <see langword="true" /> if rules exist for the type.
	 * 
	 * @param objectType    Type of business object for which the rules apply.
	 */
	public static boolean RulesExistFor(Type objectType){
		return _managers.containsKey(objectType);
	}

}