package Csla.Validation;
/**
 * Tracks all information for a rule.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */

public interface IRuleMethod extends Comparable<IRuleMethod> {
	/**
	 * Invokes the rule to validate the data.
	 * 
	 *        @returns <see langword="true" /> if the data is valid,
	 *        <see langword="false" /> if the data is invalid.
	 * 
	 * @param target
	 */
	public boolean invoke(Object target);

	/**
	 * Gets the priority of the rule method.
	 * 
	 *        @value The priority value.
	 *        @remark Priorities are processed in descending order, so priority 0 is
	 * processed before priority 1, etc.
	 */
	public Integer getPriority();

	/**
	 * Returns the name of the field, property or column to which the rule applies.
	 */
	public RuleArgs getRuleArgs();

	/**
	 * Gets the name of the rule.
	 * 
	 *        @remark The rule's name must be unique and is used to identify a broken
	 * rule in the BrokenRules collection.
	 */
	public String getRuleName();

}