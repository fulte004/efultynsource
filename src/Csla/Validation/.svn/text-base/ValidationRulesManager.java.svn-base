package Csla.Validation;

import java.util.HashMap;
import java.util.List;

/**
 * Maintains rule methods for a business object or business object type.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:48 PM
 */
public class ValidationRulesManager {

	private HashMap<String, RulesList> _rulesList;

	public ValidationRulesManager(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Adds a property to the list of dependencies for the specified property
	 * 
	 *        @remark When rules are checked for propertyName, they will also be
	 * checked for any dependant properties associated with that property.
	 * 
	 * @param propertyName    The name of the property.
	 * @param dependantPropertyName    The name of the dependant property.
	 */
	public void AddDependantProperty(String propertyName, String dependantPropertyName){
		// get the list of rules for the property
		List<String> list = GetRulesForProperty(propertyName, true).getDependancyList(true);

		// we have the list, add the dependency
		list.add(dependantPropertyName);
	}

	/**
	 * 
	 * @param handler
	 * @param args
	 * @param priority
	 */
	public void AddRule(Csla.Validation.RuleHandler handler, RuleArgs args, int priority){
		// get the list of rules for the property
		List<IRuleMethod> list = GetRulesForProperty(args.getPropertyName(), true).getList(false);

		// we have the list, add out new rule
		list.add(new RuleMethod(handler, args, priority));
	}

	/**
	 * 
	 * @param handler
	 * @param args
	 * @param priority
	 */
	public <T, R extends RuleArgs> void AddRule(Csla.Validation.Generic.RuleHandler<T, R> handler, R args, int priority)  {
		// get the list of rules for the property
		List<IRuleMethod> list = GetRulesForProperty(args.getPropertyName(), true).getList(false);

		// we have the list, add out new rule
		list.add(new Csla.Validation.Generic.RuleMethod<T, R>(handler, args, priority));
	}

	/**
	 * 
	 * @param propertyName
	 * @param createList
	 */
	public RulesList GetRulesForProperty(String propertyName, boolean createList){
		// get the list (if any) from the dictionary
		RulesList list = null;
		if (getRulesDictionary().containsKey(propertyName))
			list = getRulesDictionary().get(propertyName);

		if (createList && list == null)
		{
			// there is no list for this name - create one
			list = new RulesList();
			getRulesDictionary().put(propertyName, list);
		}
		return list;
	}

	public HashMap<String, RulesList> getRulesDictionary(){
		if (_rulesList == null)
			_rulesList = new HashMap<String, RulesList>();
		return _rulesList;
	}
}