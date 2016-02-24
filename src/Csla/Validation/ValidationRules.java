package Csla.Validation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Csla.Properties.Resources;

/**
 * Tracks the business rules broken within a business Object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:48 PM
 */
public class ValidationRules {

	/**
	 * list of broken rules for this business Object.
	 */
	private BrokenRulesCollection _brokenRules;
	/**
	 * reference to per-instance rules manager for this Object
	 */
	private ValidationRulesManager _instanceRules;
	/**
	 * threshold for short-circuiting to kick in
	 */
	private int _processThroughPriority;
	/**
	 * reference to the active set of rules for this Object
	 */
	private ValidationRulesManager _rulesToCheck;
	/**
	 * reference to current business Object
	 */
	private Object _target;
	/**
	 * reference to per-type rules manager for this Object
	 */
	private ValidationRulesManager _typeRules;

	/**
	 * 
	 * @param businessObject
	 */
	public ValidationRules(Object businessObject){
		SetTarget(businessObject);
	}

	/**
	 * Adds a property to the list of dependencies for the specified property
	 * 
	 *        @remark When rules are checked for propertyName, they will also be
	 * checked for any dependant properties associated with that property.
	 * 
	 * @param propertyName    The name of the property.
	 * @param dependantPropertyName    The name of the depandent property.
	 */
	public void AddDependantProperty(String propertyName, String dependantPropertyName){
		GetTypeRules(true).AddDependantProperty(propertyName, dependantPropertyName);
	}

	/**
	 * Adds a property to the list of dependencies for the specified property
	 * 
	 *        @remark When rules are checked for propertyName, they will also be
	 * checked for any dependant properties associated with that property. If
	 * isBidirectional is
	 *        <see langword="true"/> then an additional association is set up so when
	 * rules are checked for dependantPropertyName the rules for propertyName will
	 * also be checked.
	 * 
	 * @param propertyName    The name of the property.
	 * @param dependantPropertyName    The name of the depandent property.
	 * @param isBidirectional    If <see langword="true"/> then a reverse dependancy
	 * is also established from dependantPropertyName to propertyName.
	 */
	public void AddDependantProperty(String propertyName, String dependantPropertyName, boolean isBidirectional){
		ValidationRulesManager mgr = GetTypeRules(true);
		mgr.AddDependantProperty(propertyName, dependantPropertyName);
		if (isBidirectional)
		{
			mgr.AddDependantProperty(dependantPropertyName, propertyName);
		}
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 */
	public void AddInstanceRule(RuleHandler handler, String propertyName){
		GetInstanceRules(true).AddRule(handler, new RuleArgs(propertyName), 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 */
	public void AddInstanceRule(RuleHandler handler, String propertyName, int priority){
		GetInstanceRules(true).AddRule(handler, new RuleArgs(propertyName), priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 */
	public <T> void AddInstanceRule(Csla.Validation.Generic.RuleHandler<T, RuleArgs> handler, String propertyName){
		GetInstanceRules(true).AddRule(handler, new RuleArgs(propertyName), 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 */
	public <T> void AddInstanceRule(Csla.Validation.Generic.RuleHandler<T, RuleArgs> handler, String propertyName, int priority){
		GetInstanceRules(true).AddRule(handler, new RuleArgs(propertyName), priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 */
	public void AddInstanceRule(RuleHandler handler, RuleArgs args){
		GetInstanceRules(true).AddRule(handler, args, 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 */
	public void AddInstanceRule(RuleHandler handler, RuleArgs args, int priority){
		GetInstanceRules(true).AddRule(handler, args, priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 */
	public <T, R extends RuleArgs> void AddInstanceRule(Csla.Validation.Generic.RuleHandler<T, R> handler, R args){
		GetInstanceRules(true).AddRule(handler, args, 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 */
	public <T, R extends RuleArgs> void AddInstanceRule(Csla.Validation.Generic.RuleHandler<T, R> handler, R args, int priority){
		GetInstanceRules(true).AddRule(handler, args, priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 * @throws NoSuchMethodException 
	 */
	public void AddRule(RuleHandler handler, String propertyName) throws NoSuchMethodException {
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, new RuleArgs(propertyName), 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 * @throws NoSuchMethodException 
	 */
	public void AddRule(RuleHandler handler, String propertyName, int priority) throws NoSuchMethodException {
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, new RuleArgs(propertyName), priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	public <T> void AddRule(Csla.Validation.Generic.RuleHandler<T, RuleArgs> handler, String propertyName) throws NoSuchMethodException {
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, new RuleArgs(propertyName), 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark  A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 *         The propertyName may be used by the method that implements the rule in
	 * order to retrieve the value to be validated. If the rule implementation is
	 * inside the target Object then it probably has direct access to all data.
	 * However, if the rule implementation is outside the target Object then it will
	 * need to use reflection or CallByName to dynamically invoke this property to
	 * retrieve the value to be validated.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param propertyName    The property name on the target Object where the rule
	 * implementation can retrieve the value to be validated.
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	public <T> void AddRule(Csla.Validation.Generic.RuleHandler<T, RuleArgs> handler, String propertyName, int priority) throws NoSuchMethodException{
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, new RuleArgs(propertyName), priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	public void AddRule(RuleHandler handler, RuleArgs args) throws NoSuchMethodException {
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, args, 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	public void AddRule(RuleHandler handler, RuleArgs args, int priority) throws NoSuchMethodException {
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, args, priority);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 * @throws Exception 
	 */
	public <T, R extends RuleArgs> void AddRule(Csla.Validation.Generic.RuleHandler<T, R> handler, R args) throws Exception{
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, args, 0);
	}

	/**
	 * Adds a rule to the list of rules to be enforced.
	 * 
	 *        @remark A rule is implemented by a method which conforms to the method
	 * signature defined by the RuleHandler delegate.
	 * 
	 * @param handler    The method that implements the rule.
	 * @param args    A RuleArgs Object specifying the property name and other
	 * arguments passed to the rule method
	 * @param priority    The priority of the rule, where lower numbers are processed
	 * first.
	 * @throws Exception 
	 */
	public <T, R extends RuleArgs> void AddRule(Csla.Validation.Generic.RuleHandler<T, R> handler, R args, int priority) throws Exception{
		ValidateHandler(handler);
		GetTypeRules(true).AddRule(handler, args, priority);
	}

	private BrokenRulesCollection getBrokenRulesList(){
			if (_brokenRules == null)
				_brokenRules = new BrokenRulesCollection();
			return _brokenRules;
	}

	/**
	 * Invokes all rule methods associated with the specified property and any
	 * dependant properties.
	 * 
	 * @param propertyName    The name of the property to validate.
	 * @throws ValidationException 
	 */
	public void CheckRules(String propertyName) throws ValidationException{
		// get the rules dictionary
		ValidationRulesManager rules = getRulesToCheck();
		if (rules != null)
		{
			// get the rules list for this property
			RulesList rulesList = rules.GetRulesForProperty(propertyName, false);
			if (rulesList != null)
			{
				// get the actual list of rules (sorted by priority)
				List<IRuleMethod> list = rulesList.getList(true);
				if (list != null)
					CheckRules(list);
				List<String> dependancies = rulesList.getDependancyList(false);
				if (dependancies != null)
				{
					for (int i = 0; i < dependancies.size(); i++)
					{
						String dependantProperty = dependancies.get(i);
						CheckRules(rules, dependantProperty);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param rules
	 * @param propertyName
	 * @throws ValidationException 
	 */
	private void CheckRules(ValidationRulesManager rules, String propertyName) throws ValidationException{
		// get the rules list for this property
		RulesList rulesList = rules.GetRulesForProperty(propertyName, false);
		if (rulesList != null)
		{
			// get the actual list of rules (sorted by priority)
			List<IRuleMethod> list = rulesList.getList(true);
			if (list != null)
				CheckRules(list);
		}
	}

	/**
	 * Invokes all rule methods for all properties in the Object.
	 * @throws ValidationException 
	 */
	public void CheckRules() throws ValidationException{
		ValidationRulesManager rules = getRulesToCheck();
		if (rules != null)
		{
			ArrayList<Map.Entry<String, RulesList>> entries = new ArrayList<Map.Entry<String, RulesList>>(rules.getRulesDictionary().entrySet());
			for (Map.Entry<String, RulesList> de : entries)
				CheckRules(de.getValue().getList(true));
		}
	}

	/**
	 * Given a list containing IRuleMethod objects, this method executes all those
	 * rule methods.
	 * 
	 * @param list
	 */
	private void CheckRules(List<IRuleMethod> list) throws ValidationException{
		boolean previousRuleBroken = false;
		boolean shortCircuited = false;

		for (int index = 0; index < list.size(); index++)
		{
			IRuleMethod rule = list.get(index);
			// see if short-circuiting should kick in
			if (!shortCircuited && (previousRuleBroken && rule.getPriority() > _processThroughPriority))
				shortCircuited = true;

			if (shortCircuited)
			{
				// we're short-circuited, so just remove
				// all remaining broken rule entries
				getBrokenRulesList().Remove(rule);
			}
			else
			{
				// we're not short-circuited, so check rule
				boolean ruleResult;
				try
				{
					ruleResult = rule.invoke(_target);
				}
				catch (Exception ex)
				{
					//// force a broken rule
					//ruleResult = false;
					//rule.RuleArgs.Severity = RuleSeverity.Error;
					//rule.RuleArgs.Description = 
					//  String.Format(Properties.Resources.ValidationRuleException & "{{2}}", rule.RuleArgs.PropertyName, rule.RuleName, ex.Message);
					// throw a more detailed exception
					throw new ValidationException(
							String.format(Resources.getValidationRulesException(), rule.getRuleArgs().getPropertyName(), rule.getRuleName()), ex);
				}

				if (ruleResult)
				{
					// the rule is not broken
					getBrokenRulesList().Remove(rule);
				}
				else
				{
					// the rule is broken
					getBrokenRulesList().add(rule);
					if (rule.getRuleArgs().getSeverity() == RuleSeverity.ERROR)
						previousRuleBroken = true;
				}
				if (rule.getRuleArgs().getStopProcessing())
				{
					shortCircuited = true;
					// reset the value for next time
					rule.getRuleArgs().setStopProcessing(false);
				}
			}
		}
	}

	/**
	 * Returns a reference to the readonly collection of broken business rules.
	 * 
	 *        @remark The reference returned points to the actual collection Object.
	 * This means that as rules are marked broken or unbroken over time, the
	 * underlying data will change. Because of this, the UI developer can bind a
	 * display directly to this collection to get a dynamic display of the broken
	 * rules at all times.
	 *        @returns A reference to the collection of broken rules.
	 */
	public BrokenRulesCollection GetBrokenRules(){
		return getBrokenRulesList();
	}

	/**
	 * 
	 * @param createObject
	 */
	private ValidationRulesManager GetInstanceRules(boolean createObject){
		if (_instanceRules == null)
			if (createObject)
				_instanceRules = new ValidationRulesManager();
		return _instanceRules;
	}

	/**
	 * Returns an array containing the text descriptions of all validation rules
	 * associated with this Object.
	 * 
	 *        @returns String array.
	 *        @remark 
	 */
	public String[] GetRuleDescriptions(){
		List<String> result = new ArrayList<String>();
		ValidationRulesManager rules = getRulesToCheck();
		if (rules != null)
		{
			ArrayList<Map.Entry<String, RulesList>> entries = new ArrayList<Map.Entry<String, RulesList>>(rules.getRulesDictionary().entrySet());
			for (Map.Entry<String, RulesList> de : entries)
			{
				List<IRuleMethod> list = de.getValue().getList(false);
				for (int i = 0; i < list.size(); i++)
				{
					IRuleMethod rule = list.get(i);
					result.add(rule.toString());
				}
			}
		}
		return result.toArray(new String[0]);
	}

	/**
	 * 
	 * @param createObject
	 */
	private ValidationRulesManager GetTypeRules(boolean createObject){
		if (_typeRules == null)
			_typeRules = SharedValidationRules.GetManager(_target.getClass(), createObject);
		return _typeRules;
	}

	/**
	 * Returns a value indicating whether there are any broken rules at this time.
	 * 
	 *        @returns A value indicating whether any rules are broken.
	 */
	public boolean isValid(){
		  return getBrokenRulesList().getErrorCount() == 0; 
	}

	/**
	 * Gets or sets the priority through which CheckRules should process before short-
	 * circuiting processing on broken rules.
	 * 
	 *        @value Defaults to 0.
	 *        @remark All rules for each property are processed by CheckRules though
	 * this priority. Rules with lower priorities are only processed if no previous
	 * rule has been marked as broken.
	 */
	public int getProcessThroughPriority(){
		  return _processThroughPriority; 
	}
	public void setProcessThroughPriority(int value){
		  _processThroughPriority = value; 
	}
	
	private ValidationRulesManager getRulesToCheck(){              
		if (_rulesToCheck == null)
		{
			ValidationRulesManager instanceRules = GetInstanceRules(false);
			ValidationRulesManager typeRules = GetTypeRules(false);
			if (instanceRules == null)
			{
				if (typeRules == null)
					_rulesToCheck = null;
				else
					_rulesToCheck = typeRules;
			}
			else if (typeRules == null)
				_rulesToCheck = instanceRules;
			else
			{
				// both have values - consolidate into instance rules
				_rulesToCheck = instanceRules;
				
				ArrayList<Map.Entry<String, RulesList>> entries = new ArrayList<Map.Entry<String, RulesList>>(typeRules.getRulesDictionary().entrySet());
				for (Map.Entry<String, RulesList> de : entries)
				{
					RulesList rules = _rulesToCheck.GetRulesForProperty(de.getKey(), true);
					List<IRuleMethod> instanceList = rules.getList(false);
					instanceList.addAll(de.getValue().getList(false));
					List<String> dependancy = de.getValue().getDependancyList(false);
					if (dependancy != null)
						rules.getDependancyList(true).addAll(dependancy);
				}
			}
		}
		return _rulesToCheck;         
	}

	/**
	 * 
	 * @param businessObject
	 */
	public void SetTarget(Object businessObject){
		_target = businessObject;
	}

	/**
	 * 
	 * @param handler
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	private boolean ValidateHandler(RuleHandler handler) throws NoSuchMethodException {
		return ValidateHandler(handler.getMethod());
	}

	/**
	 * 
	 * @param handler
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	private <T, R extends RuleArgs> boolean ValidateHandler(Csla.Validation.Generic.RuleHandler<T, R> handler) throws NoSuchMethodException {
		return ValidateHandler(handler.getMethod());
	}

	/**
	 * 
	 * @param method
	 * @throws NoSuchMethodException 
	 * @throws Exception 
	 */
	private boolean ValidateHandler(Method method) throws NoSuchMethodException {
		if (/*!method.isStatic() &&*/ method.getDeclaringClass().equals(_target.getClass()))
			throw new NoSuchMethodException(
					String.format("%1$: %2$",
							Resources.getInvalidRuleMethodException(), method.getName()));
		return true;
	}

}