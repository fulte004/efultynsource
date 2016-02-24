package Csla.Validation;
import java.util.ArrayList;
import java.util.List;

import Csla.Core.ReadOnlyBindingList;

/**
 * A collection of currently broken rules.
 * 
 *      @remark This collection is readonly and can be safely made available to
 * code outside the business object such as the UI. This allows external code,
 * such as a UI, to display the list of broken rules to the user.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:25 PM
 */
public class BrokenRulesCollection extends ReadOnlyBindingList<BrokenRule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _errorCount;
	private int _infoCount;
	private int _warningCount;

	BrokenRulesCollection(){
		// limit creation to this assembly
	}

	/**
	 * 
	 * @param rule
	 */
	public void add(IRuleMethod rule){
		Remove(rule);
		setIsReadOnly(false);
		BrokenRule item = new BrokenRule(rule);
		IncrementCount(item);
		add(item);
		setIsReadOnly(true);
	}

	/**
	 * 
	 * @param item
	 */
	private void DecrementCount(BrokenRule item){
		switch (item.getSeverity())
		{
		case ERROR:
			_errorCount -= 1;
			break;
		case WARNING:
			_warningCount -= 1;
			break;
		case INFORMATION:
			_infoCount -= 1;
			break;
		}
	}

	/**
	 * Gets the number of broken rules in the collection that have a severity of Error.
	 * 
	 * 
	 *        @value An integer value.
	 */
	public int getErrorCount(){
		return _errorCount; 
	}

	/**
	 * Returns the first <see cref="BrokenRule" /> object corresponding to the
	 * specified property.
	 * 
	 *        @remark Code in a business object or UI can also use this value to
	 * retrieve the first broken rule in <see cref="BrokenRulesCollection" /> that
	 * corresponds to a specfic property on the object.
	 *        @returns The first BrokenRule object corresponding to the specified
	 * property, or null if there are no rules defined for the property.
	 * 
	 * @param property    The name of the property affected by the rule.
	 */
	public BrokenRule GetFirstBrokenRule(String property){
		return GetFirstMessage(property, RuleSeverity.ERROR);
	}

	/**
	 * Returns the first <see cref="BrokenRule" /> object corresponding to the
	 * specified property.
	 * 
	 *        @remark Code in a business object or UI can also use this value to
	 * retrieve the first broken rule in <see cref="BrokenRulesCollection" /> that
	 * corresponds to a specfic property.
	 *        @returns The first BrokenRule object corresponding to the specified
	 * property, or Nothing (null in C#) if there are no rules defined for the
	 * property.
	 * 
	 * @param property    The name of the property affected by the rule.
	 */
	public BrokenRule GetFirstMessage(String property){
		for (BrokenRule item : this)
			if (item.getProperty() == property)
				return item;
		return null;
	}

	/**
	 * Returns the first <see cref="BrokenRule"/> object corresponding to the
	 * specified property and severity.
	 * 
	 *        @returns The first BrokenRule object corresponding to the specified
	 * property, or Nothing (null in C#) if there are no rules defined for the
	 * property.
	 * 
	 * @param property    The name of the property affected by the rule.
	 * @param severity    The severity of broken rule to return.
	 */
	public BrokenRule GetFirstMessage(String property, RuleSeverity severity){
		for (BrokenRule item : this)
			if (item.getProperty() == property && item.getSeverity() == severity)
				return item;
		return null;
	}

	/**
	 * 
	 * @param item
	 */
	private void IncrementCount(BrokenRule item){
		switch (item.getSeverity())
		{
		case ERROR:
			_errorCount += 1;
			break;
		case WARNING:
			_warningCount += 1;
			break;
		case INFORMATION:
			_infoCount += 1;
			break;
		}
	}

	/**
	 * Gets the number of broken rules in the collection that have a severity of
	 * Information.
	 * 
	 *        @value An integer value.
	 */
	public int getInformationCount(){
		return _infoCount; 
	}

	/**
	 * 
	 * @param rule
	 */
	public void Remove(IRuleMethod rule){
		// we loop through using a numeric counter because
		// removing items within a for isn't reliable
		setIsReadOnly(false);
		for (int index = 0; index < size(); index++)
			if (this.get(index).getRuleName() == rule.getRuleName())
			{
				DecrementCount(this.get(index));
				remove(index);
				break;
			}
		setIsReadOnly(true);
	}

	/**
	 * Returns a String array containing all broken rule descriptions.
	 * 
	 *        @returns The text of all broken rule descriptions matching the specified
	 * severtiy.
	 */
	public String[] toArray(){
		List<String> result = new ArrayList<String>();
		for (BrokenRule item : this)
			result.add(item.getDescription());
		return (String[]) result.toArray();
	}

	/**
	 * Returns a String array containing all broken rule descriptions.
	 * 
	 *        @returns The text of all broken rule descriptions matching the specified
	 * severtiy.
	 * 
	 * @param severity    The severity of rules to include in the result.
	 */
	public String[] ToArray(RuleSeverity severity){
		List<String> result = new ArrayList<String>();
		for (BrokenRule item : this)
			if (item.getSeverity() == severity)
				result.add(item.getDescription());
		return (String[]) result.toArray();
	}

	/**
	 * Returns the text of all broken rule descriptions, each separated by a <see
	 * cref="Environment.NewLine" />.
	 * 
	 *        @returns The text of all broken rule descriptions.
	 */
	public String ToString(){
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (BrokenRule item : this)
		{
			if (first)
				first = false;
			else
				result.append("\n");
			result.append(item.getDescription());
		}
		return result.toString();
	}

	/**
	 * Returns the text of all broken rule descriptions, each separated by a <see
	 * cref="Environment.NewLine" />.
	 * 
	 *        @returns The text of all broken rule descriptions matching the specified
	 * severtiy.
	 * 
	 * @param severity    The severity of rules to include in the result.
	 */
	public String ToString(RuleSeverity severity){
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (BrokenRule item : this)
		{
			if (item.getSeverity() == severity)
			{
				if (first)
					first = false;
				else
					result.append("\n");
				result.append(item.getDescription());
			}
		}
		return result.toString();
	}

	/**
	 * Gets the number of broken rules in the collection that have a severity of
	 * Warning.
	 * 
	 *        @value An integer value.
	 */
	public int getWarningCount(){
		return _warningCount; 
	}

}