package Csla.Validation;
/**
 * Object providing extra information to methods that implement business rules.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:42 PM
 */
public class RuleArgs {

	private String _description;
	private String _propertyFriendlyName;
	private String _propertyName;
	private RuleSeverity _severity = RuleSeverity.ERROR;
	private boolean _stopProcessing;

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 */
	public RuleArgs(String propertyName){
		_propertyName = propertyName;
	}

	/**
	 * Creates an instance of RuleArgs.
	 * 
	 * @param propertyName    The name of the property to be validated.
	 * @param friendlyName    A friendly name for the property, which will be used in
	 * place of the property name when creating the broken rule description String.
	 */
	public RuleArgs(String propertyName, String friendlyName){
		_propertyName = propertyName;
		_propertyFriendlyName = friendlyName;
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
	public RuleArgs(String propertyName, RuleSeverity severity){
		_severity = severity;
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
	 * @param friendlyName    A friendly name for the property, which will be used in
	 * place of the property name when creating the broken rule description String.
	 * @param severity    Initial default severity for the rule.
	 */
	public RuleArgs(String propertyName, String friendlyName, RuleSeverity severity){
		_severity = severity;
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
	public RuleArgs(String propertyName, RuleSeverity severity, boolean stopProcessing){
		_stopProcessing = stopProcessing;
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
	 * @param friendlyName    A friendly name for the property, which will be used in
	 * place of the property name when creating the broken rule description String.
	 * @param severity    The default severity for the rule.
	 * @param stopProcessing    Initial default value for the StopProcessing property.
	 */
	public RuleArgs(String propertyName, String friendlyName, RuleSeverity severity, boolean stopProcessing){
		_stopProcessing = stopProcessing;
	}

	public RuleArgs() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Set by the rule handler method to describe the broken rule.
	 * 
	 *        @value A human-readable description of the broken rule.
	 *        @remark Setting this property only has an effect if the rule method
	 * returns <see langword="false" />.
	 */
	public String getDescription(){
		return _description; 
	}
	public void setDescription(String value){
		_description = value; 
	}
	/**
	 * Gets the property name from the RuleArgs object, using the friendly name if one
	 * is defined.
	 * 
	 *        @returns The friendly property name if it exists, otherwise the property
	 * name itself.
	 * 
	 * @param e    Object from which to extract the name.
	 */
	public static String GetPropertyName(RuleArgs e){
		String propName;
		if (e.getPropertyFriendlyName().length()==0)
			propName = e.getPropertyName();
		else
			propName = e.getPropertyFriendlyName();
		return propName;
	}

	/**
	 * Gets or sets a friendly name for the property, which will be used in place of
	 * the property name when creating the broken rule description String.
	 */
	public String getPropertyFriendlyName(){
		  return _propertyFriendlyName; 
	}
	
	public void setPropertyFriendlyName(String value){
		  _propertyFriendlyName = value; 
	}

	/**
	 * The name of the property to be validated.
	 */
	public String getPropertyName(){
		  return _propertyName; 
	}

	/**
	 * Gets or sets the severity of the broken rule.
	 * 
	 *        @value The severity of the broken rule.
	 *        @remark Setting this property only has an effect if the rule method
	 * returns <see langword="false" />.
	 */
	public RuleSeverity getSeverity(){
		  return _severity; 
	}
	public void setSeverity(RuleSeverity value){
		  _severity = value; 
	}
	/**
	 * Gets or sets a value indicating whether this broken rule should stop the
	 * processing of subsequent rules for this property.
	 * 
	 *        @value <see langword="true" /> if no further rules should be process for
	 * this property.
	 *        @remark Setting this property only has an effect if the rule method
	 * returns <see langword="false" />.
	 */
	public boolean getStopProcessing(){
		  return _stopProcessing; 
	}
	public void setStopProcessing(boolean value){
		  _stopProcessing = value; 
	}
	/**
	 * Return a String representation of the object.
	 */
	public String ToString(){
		return _propertyName;
	}

}