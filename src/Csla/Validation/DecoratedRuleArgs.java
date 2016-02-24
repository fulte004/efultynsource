package Csla.Validation;

import java.net.URI;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Object providing extra information to methods that implement business rules.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:33 PM
 */
public class DecoratedRuleArgs extends RuleArgs {

	private java.util.HashMap<String, Object> _decorations;

	public DecoratedRuleArgs(){

	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 */
	public DecoratedRuleArgs(String propertyName){
		_decorations = new HashMap<String, Object>();
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used in
	 * place of the property name when creating the broken rule description String.
	 */
	public DecoratedRuleArgs(String propertyName, String friendlyName){
		_decorations = new HashMap<String, Object>();
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> parameter defines only the initial default
	 * severity value. If the rule changes this value by setting e.Severity, then that
	 * new value will become the default for all subsequent rule invocations.
	 *         To avoid confusion, it is recommended that the
	 *        <b>severity</b> constructor parameter only be used for rule methods that
	 * do not explicitly set e.Severity.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param severity    Initial default severity for the rule.
	 */
	public DecoratedRuleArgs(String propertyName, RuleSeverity severity){
		_decorations = new HashMap<String, Object>();
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> parameter defines only the initial default
	 * severity value. If the rule changes this value by setting e.Severity, then that
	 * new value will become the default for all subsequent rule invocations.
	 *         To avoid confusion, it is recommended that the
	 *        <b>severity</b> constructor parameter only be used for rule methods that
	 * do not explicitly set e.Severity.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used :
	 * place of the property name when creating the broken rule description String.
	 * @param severity    Initial default severity for the rule.
	 */
	public DecoratedRuleArgs(String propertyName, String friendlyName, RuleSeverity severity){
		_decorations = new HashMap<String, Object>();
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> and <b>stopProcessing</b> parameters define
	 * only the initial default values. If the rule changes these values by setting e.
	 * Severity or e.StopProcessing, then the new values will become the default for
	 * all subsequent rule invocations.
	 *         To avoid confusion, It is recommended that the
	 *        <b>severity</b> and <b>stopProcessing</b> constructor parameters only be
	 * used for rule methods that do not explicitly set e.Severity or e.StopProcessing.
	 * 
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param severity    The default severity for the rule.
	 * @param stopProcessing    Initial default value for the StopProcessing property.
	 */
	public DecoratedRuleArgs(String propertyName, RuleSeverity severity, boolean stopProcessing){
		_decorations = new HashMap<String, Object>();
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> and <b>stopProcessing</b> parameters define
	 * only the initial default values. If the rule changes these values by setting e.
	 * Severity or e.StopProcessing, then the new values will become the default for
	 * all subsequent rule invocations.
	 *         To avoid confusion, It is recommended that the
	 *        <b>severity</b> and <b>stopProcessing</b> constructor parameters only be
	 * used for rule methods that do not explicitly set e.Severity or e.StopProcessing.
	 * 
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used :
	 * place of the property name when creating the broken rule description String.
	 * @param severity    The default severity for the rule.
	 * @param stopProcessing    Initial default value for the StopProcessing property.
	 */
	public DecoratedRuleArgs(String propertyName, String friendlyName, RuleSeverity severity, boolean stopProcessing){
		_decorations = new HashMap<String, Object>();
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param args    Reference to a Map containing name/value arguments for
	 * use by the rule method.
	 */
	public DecoratedRuleArgs(String propertyName, HashMap<String,Object> args){
		_decorations = args;
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used :
	 * place of the property name when creating the broken rule description String.
	 * @param args    Reference to a Map containing name/value arguments for
	 * use by the rule method.
	 */
	public DecoratedRuleArgs(String propertyName, String friendlyName, HashMap<String, Object> args){
		_decorations = args;
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> parameter defines only the initial default
	 * severity value. If the rule changes this value by setting e.Severity, then that
	 * new value will become the default for all subsequent rule invocations.
	 *         To avoid confusion, it is recommended that the
	 *        <b>severity</b> constructor parameter only be used for rule methods that
	 * do not explicitly set e.Severity.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param severity    Initial default severity for the rule.
	 * @param args    Reference to a Map containing name/value arguments for
	 * use by the rule method.
	 */
	public DecoratedRuleArgs(String propertyName, RuleSeverity severity, HashMap<String, Object> args){
		_decorations = args;
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> parameter defines only the initial default
	 * severity value. If the rule changes this value by setting e.Severity, then that
	 * new value will become the default for all subsequent rule invocations.
	 *         To avoid confusion, it is recommended that the
	 *        <b>severity</b> constructor parameter only be used for rule methods that
	 * do not explicitly set e.Severity.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used :
	 * place of the property name when creating the broken rule description String.
	 * @param severity    Initial default severity for the rule.
	 * @param args    Reference to a Map containing name/value arguments for
	 * use by the rule method.
	 */
	public DecoratedRuleArgs(String propertyName, String friendlyName, RuleSeverity severity, HashMap<String, Object> args){
		_decorations = args;
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> and <b>stopProcessing</b> parameters define
	 * only the initial default values. If the rule changes these values by setting e.
	 * Severity or e.StopProcessing, then the new values will become the default for
	 * all subsequent rule invocations.
	 *         To avoid confusion, It is recommended that the
	 *        <b>severity</b> and <b>stopProcessing</b> constructor parameters only be
	 * used for rule methods that do not explicitly set e.Severity or e.StopProcessing.
	 * 
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param severity    The default severity for the rule.
	 * @param stopProcessing    Initial default value for the StopProcessing property.
	 * @param args    Reference to a Map containing name/value arguments for
	 * use by the rule method.
	 */
	public DecoratedRuleArgs(String propertyName, RuleSeverity severity, boolean stopProcessing, HashMap<String, Object> args){
		_decorations = args;
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 *        @remark  The <b>severity</b> and <b>stopProcessing</b> parameters define
	 * only the initial default values. If the rule changes these values by setting e.
	 * Severity or e.StopProcessing, then the new values will become the default for
	 * all subsequent rule invocations.
	 *         To avoid confusion, It is recommended that the
	 *        <b>severity</b> and <b>stopProcessing</b> constructor parameters only be
	 * used for rule methods that do not explicitly set e.Severity or e.StopProcessing.
	 * 
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used :
	 * place of the property name when creating the broken rule description String.
	 * @param severity    The default severity for the rule.
	 * @param stopProcessing    Initial default value for the StopProcessing property.
	 * @param args    Reference to a Map containing name/value arguments for
	 * use by the rule method.
	 */
	public DecoratedRuleArgs(String propertyName, String friendlyName, RuleSeverity severity, boolean stopProcessing, HashMap<String, Object> args){
		_decorations = args;
	}

	/**
	 * Gets or sets an argument value for use by the rule method.
	 * 
	 *        @returns
	 * 
	 * @param key    The name under which the value is stored.
	 */
	public Object get(String key)
	{
		if (_decorations.containsKey(key))
			return _decorations.get(key);
		else
			return null;
	}
	public void put(String key, Object value) 
	{ 
		_decorations.put(key, value); 
	}


	/**
	 * Return a String representation of the Object using the rule:// URI format.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		if (_decorations.size() > 0)
		{

			sb.append("?");
			boolean first = true;
			Set<Entry<String, Object>> decorationSet = _decorations.entrySet();
			for (Entry<String, Object> item : decorationSet)
			{
				if (first)
					first = false;
				else
					sb.append("&");
				if (item.getKey()!= null)
				{
					String itemString = URI.create(item.getKey()).toString();
					String valueString;
					if (item.getValue()== null)
						valueString = "";
					else
						valueString = URI.create(item.getKey()).toString();
					sb.append(String.format("{0}={1}", itemString, valueString));
				}
			}
		}
		return sb.toString();
	}

}