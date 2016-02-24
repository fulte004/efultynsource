package Csla.Validation;

/**
 * Stores details about a specific broken business rule.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:25 PM
 */
public class BrokenRule {

	private String _description;
	private String _property;
	private String _ruleName;
	private RuleSeverity _severity;

	/**
	 * 
	 * @param rule
	 */
	public BrokenRule(IRuleMethod rule){
		_ruleName = rule.getRuleName();
		        _description = rule.getRuleArgs().getDescription();
		        _property = rule.getRuleArgs().getPropertyName();
		        _severity = rule.getRuleArgs().getSeverity();
	}

	/**
	 * Provides access to the description of the broken rule.
	 * 
	 *        @value The description of the rule.
	 */
	public String getDescription(){
		  return _description; 
	}

	/**
	 * Provides access to the property affected by the broken rule.
	 * 
	 *        @value The property affected by the rule.
	 */
	public String getProperty(){
		  return _property; 
	}

	/**
	 * Provides access to the name of the broken rule.
	 * 
	 *        @value The name of the rule.
	 */
	public String getRuleName(){
		 return _ruleName; 
	}

	/**
	 * Gets the severity of the broken rule.
	 * 
	 *        @value
	 *        @returns
	 *        @remark 
	 */
	public RuleSeverity getSeverity(){
		  return _severity; 
	}

}