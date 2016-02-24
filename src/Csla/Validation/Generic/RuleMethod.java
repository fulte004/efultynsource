package Csla.Validation.Generic;

import Csla.Validation.IRuleMethod;
import Csla.Validation.RuleArgs;

/**
 * Tracks all information for a rule.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:43 PM
 */
public class RuleMethod<T, R extends RuleArgs>  implements IRuleMethod {

	private R _args;
	private RuleHandler<T, R> _handler;
	private int _priority;
	private String _ruleName = "";

	/**
	 * Creates and initializes the rule.
	 * 
	 * @param handler    The address of the method implementing the rule.
	 * @param args    A RuleArgs Object.
	 */
	public RuleMethod(RuleHandler<T, R> handler, R args){
		_handler = handler;
		        _args = args;
		        _ruleName = String.format("rule://$1%/$2%", _handler.getRuleName(), _args.toString());
	}

	/**
	 * Creates and initializes the rule.
	 * 
	 * @param handler    The address of the method implementing the rule.
	 * @param args    A RuleArgs Object.
	 * @param priority    Priority for processing the rule (smaller numbers have
	 * higher priority, default=0).
	 */
	public RuleMethod(RuleHandler<T, R> handler, R args, int priority){
		_priority = priority;
	}

	/**
	 * 
	 * @param obj
	 */
	
	public int compareTo(RuleMethod<T, R> o) {
		return getPriority().compareTo(o.getPriority());
	}
	/**
	 * 
	 * @param other
	 */

	@Override
	public int compareTo(IRuleMethod other) {
		return getPriority().compareTo(other.getPriority());
	}
	/**
	 * Invokes the rule to validate the data.
	 * 
	 *        @returns True if the data is valid, False if the data is invalid.
	 * 
	 * @param target
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean invoke(Object target){
		return this.invokeT((T)target);
	}

	/**
	 * Invokes the rule to validate the data.
	 * 
	 *        @returns <see langword="true" /> if the data is valid,
	 *        <see langword="false" /> if the data is invalid.
	 * 
	 * @param target
	 */
	public  boolean invokeT(T target){
		return _handler.invoke(target, _args);
	}

	/**
	 * Gets the priority of the rule method.
	 * 
	 *        @value The priority value
	 *        @remark Priorities are processed in descending order, so priority 0 is
	 * processed before priority 1, etc.
	 */
	public Integer getPriority(){
		  return _priority; 
	}

	/**
	 * Returns the name of the field, property or column to which the rule applies.
	 */
	@Override
	public RuleArgs getRuleArgs(){
		 return this.getRuleArgs();
	}

	/**
	 * Returns the name of the field, property or column to which the rule applies.
	 */
	public R getRuleArgsR(){
		  return _args; 
	}

	/**
	 * Gets the name of the rule.
	 * 
	 *        @remark The rule's name must be unique and is used to identify a broken
	 * rule in the BrokenRules collection.
	 */
	public String getRuleName(){
		 return _ruleName;
	}

	/**
	 * Returns the name of the method implementing the rule and the property, field or
	 * column name to which the rule applies.
	 */
	public String toString(){
		return _ruleName;
	}


}
