package Csla.Security;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains authorization roles for a business object or business object type.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:24 PM
 */
public class AuthorizationRulesManager {

	private Map<String, RolesForProperty> _rules;

	/**
	 * 
	 * @param propertyName
	 */
	public RolesForProperty getRolesForProperty(String propertyName){
		RolesForProperty currentRoles = null;
		if (!getRulesList().containsKey(propertyName))
		{
			currentRoles = new RolesForProperty();
			getRulesList().put(propertyName, currentRoles);
		}
		else
			currentRoles = getRulesList().get(propertyName);
		return currentRoles;
	}

	public Map<String, RolesForProperty> getRulesList(){
		if (_rules == null)
			_rules = new HashMap<String, RolesForProperty>();
		return _rules;
	}

}