package Csla.Validation;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Maintains a list of all the per-type
 *      <see cref="ValidationRulesManager"/> objects loaded in memory.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:45 PM
 */
public class SharedValidationRules {

	private static Map<Type, ValidationRulesManager> _managers = new HashMap<Type, ValidationRulesManager>();

	public SharedValidationRules(){

	}

	@Override
	public void finalize() throws Throwable {

	}

	/**
	 * Gets the <see cref="ValidationRulesManager"/> for the specified object type,
	 * optionally creating a new instance of the object if necessary.
	 * 
	 * @param objectType    Type of business object for which the rules apply.
	 * @param create    Indicates whether to create a new instance of the object if
	 * one doesn't exist.
	 */
	public static ValidationRulesManager GetManager(Type objectType, boolean create){
		ValidationRulesManager result = null;
		if (!_managers.containsKey(objectType) && create)
		{
			synchronized (_managers)
			{
				if (!_managers.containsKey(objectType))
				{
					result = new ValidationRulesManager();
					_managers.put(objectType, result);
				}
				else {
					result = _managers.get(objectType);
				}
			}
		}
		else {
			result = _managers.get(objectType);
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