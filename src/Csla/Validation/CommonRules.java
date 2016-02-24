package Csla.Validation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import Csla.CallType;
import Csla.Utilities;
import Csla.Properties.Resources;

/**
 * Implements common business rules.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:28 PM
 */
public class CommonRules {

	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="StringMaxLength" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:28 PM
	 */
	/**
	 * Returns the specified built-in regex pattern.
	 * 
	 * @param pattern    Pattern to return.
	 */

	/**
	 * List of built-in regex patterns.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:29 PM
	 */
	public enum RegExPatterns {
		/**
		 * US Social Security number pattern.
		 * 
		 *          
		 */
		SSN,
		/**
		 * Email address pattern.
		 * 
		 *          
		 */
		Email
	}
	
	/**
	 * List of options for the NullResult property.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:29 PM
	 */
	public enum NullResultOptions {
		/**
		 * Indicates that a null value should always result in the rule returning false.
		 * 
		 *            
		 */
		RETURN_FALSE,
		/**
		 * Indicates that a null value should always result in the rule returning true.
		 * 
		 *            
		 */
		RETURN_TRUE,
		/**
		 * Indicates that a null value should be converted to an empty String before the
		 * regular expression is evaluated.
		 * 
		 *            
		 */
		CONVERT_TO_EMPTY_STRING
	}
	
	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="RegExMatch" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:29 PM
	 */
	public class RegExRuleArgs extends DecoratedRuleArgs {
		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param pattern    Built-in regex pattern to use.
		 */
		public RegExRuleArgs(String propertyName, RegExPatterns pattern){
			put("RegEx", Pattern.compile(GetPattern(pattern)));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param pattern    Built-in regex pattern to use.
		 */
		public RegExRuleArgs(String propertyName, String friendlyName, RegExPatterns pattern){
			put("RegEx", Pattern.compile(GetPattern(pattern)));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param pattern    Custom regex pattern to use.
		 */
		public RegExRuleArgs(String propertyName, String pattern){
			put("RegEx", Pattern.compile(pattern));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param pattern    Custom regex pattern to use.
		 */
		public RegExRuleArgs(String propertyName, String friendlyName, String pattern){
			put("RegEx", Pattern.compile(pattern));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param regEx    <see cref="RegEx"/> Object to use.
		 */
		public RegExRuleArgs(String propertyName, Pattern regEx){
			put("RegEx", regEx);
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param regEx    <see cref="RegEx"/> Object to use.
		 */
		public RegExRuleArgs(String propertyName, String friendlyName, Pattern regEx){
			put("RegEx", regEx);
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param pattern    Built-in regex pattern to use.
		 * @param nullResult    Value indicating how a null value should be handled by the
		 * rule method.
		 */
		public RegExRuleArgs(String propertyName, RegExPatterns pattern, NullResultOptions nullResult){
			put("RegEx", Pattern.compile(GetPattern(pattern)));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param pattern    Built-in regex pattern to use.
		 * @param nullResult    Value indicating how a null value should be handled by the
		 * rule method.
		 */
		public RegExRuleArgs(String propertyName, String friendlyName, RegExPatterns pattern, NullResultOptions nullResult){
			put("RegEx", Pattern.compile(GetPattern(pattern)));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param pattern    Custom regex pattern to use.
		 * @param nullResult    Value indicating how a null value should be handled by the
		 * rule method.
		 */
		public RegExRuleArgs(String propertyName, String pattern, NullResultOptions nullResult){
			put("RegEx", Pattern.compile(pattern));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param pattern    Custom regex pattern to use.
		 * @param nullResult    Value indicating how a null value should be handled by the
		 * rule method.
		 */
		public RegExRuleArgs(String propertyName, String friendlyName, String pattern, NullResultOptions nullResult){
			put("RegEx", Pattern.compile(pattern));
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param regEx    <see cref="RegEx"/> Object to use.
		 * @param nullResult    Value indicating how a null value should be handled by the
		 * rule method.
		 */
		public RegExRuleArgs(String propertyName, Pattern regEx, NullResultOptions nullResult){
			put("RegEx", regEx);
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}

		/**
		 * Creates a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param regEx    <see cref="RegEx"/> Object to use.
		 * @param nullResult    Value indicating how a null value should be handled by the
		 * rule method.
		 */
		public RegExRuleArgs(String propertyName, String friendlyName, Pattern regEx, NullResultOptions nullResult){
			put("RegEx", regEx);
			put("NullOption", NullResultOptions.RETURN_FALSE);
		}



		/**
		 * Gets a value indicating whether a null value means the rule will return true or
		 * false.
		 */
		public NullResultOptions getNullResult(){			
				return (NullResultOptions)get("NullOption");
		}

		/**
		 * The <see cref="RegEx"/> Object used to validate the property.
		 */
		public Pattern getRegEx(){
			  return (Pattern)get("RegEx"); 
		}


	}

		public static String GetPattern(RegExPatterns pattern){
			switch (pattern)
			{
			case SSN:
				return "^\\d[3]-\\d[2]-\\d[4]$";
			case Email:
				return "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
			default:
				return "";
			}
		}	
	public class MaxLengthRuleArgs extends DecoratedRuleArgs {

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param maxLength    Max length of characters allowed.
		 */
		public MaxLengthRuleArgs(String propertyName, int maxLength){
			put("MaxLength", maxLength);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param maxLength    Max length of characters allowed.
		 */
		public MaxLengthRuleArgs(String propertyName, String friendlyName, int maxLength){
			put("MaxLength", maxLength);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param maxLength    Max length of characters allowed.
		 * @param format    Format String for the max length value in the broken rule
		 * String.
		 */
		public MaxLengthRuleArgs(String propertyName, int maxLength, String format){
			put("MaxLength", maxLength);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param maxLength    Max length of characters allowed.
		 * @param format    Format String for the max length value in the broken rule
		 * String.
		 */
		public MaxLengthRuleArgs(String propertyName, String friendlyName, int maxLength, String format){
			put("MaxLength", maxLength);
			put("Format", "");
		}

		/**
		 * Get the max length for the String.
		 */
		public int getMaxLength()
		{ return (Integer)get("MaxLength"); }


	}

	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="StringMinLength" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:28 PM
	 */
	public class MinLengthRuleArgs extends DecoratedRuleArgs {

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param minLength    min length of characters allowed.
		 */
		public MinLengthRuleArgs(String propertyName, int minLength){
			put("MinLength", minLength);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param minLength    min length of characters allowed.
		 */
		public MinLengthRuleArgs(String propertyName, String friendlyName, int minLength){
			put("MinLength", minLength);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param minLength    min length of characters allowed.
		 * @param format    Format String for the min length value in the broken rule
		 * String.
		 */
		public MinLengthRuleArgs(String propertyName, int minLength, String format){
			put("MinLength", minLength);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property to validate.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param minLength    min length of characters allowed.
		 * @param format    Format String for the min length value in the broken rule
		 * String.
		 */
		public MinLengthRuleArgs(String propertyName, String friendlyName, int minLength, String format){
			put("MinLength", minLength);
			put("Format", "");
		}

		/**
		 * Get the min length for the String.
		 */
		public int getMinLength(){
			return (Integer)get("MinLength"); 
		}

	}

	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="IntegerMaxValue" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:28 PM
	 */
	public class IntegerMaxValueRuleArgs extends DecoratedRuleArgs {

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param maxValue    Maximum allowed value for the property.
		 */
		public IntegerMaxValueRuleArgs(String propertyName, int maxValue){
			put("MaxValue", maxValue);
			put("Format","");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param maxValue    Maximum allowed value for the property.
		 */
		public IntegerMaxValueRuleArgs(String propertyName, String friendlyName, int maxValue){
			put("MaxValue", maxValue);
			put("Format","");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param maxValue    Maximum allowed value for the property.
		 * @param format    Format String for the max value value in the broken rule
		 * String.
		 */
		public IntegerMaxValueRuleArgs(String propertyName, int maxValue, String format){
			put("MaxValue", maxValue);
			put("Format","");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param maxValue    Maximum allowed value for the property.
		 * @param format    Format String for the max value value in the broken rule
		 * String.
		 */
		public IntegerMaxValueRuleArgs(String propertyName, String friendlyName, int maxValue, String format){
			put("MaxValue", maxValue);
			put("Format","");
		}

		/**
		 * Get the max value for the property.
		 */
		public int getMaxValue(){
			return (Integer)get("MaxValue"); 
		}

	}

	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="IntegerMinValue" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:28 PM
	 */
	public class IntegerMinValueRuleArgs extends DecoratedRuleArgs {

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param minValue    Minimum allowed value for the property.
		 */
		public IntegerMinValueRuleArgs(String propertyName, int minValue){
			put("MinValue", minValue);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param minValue    Minimum allowed value for the property.
		 */
		public IntegerMinValueRuleArgs(String propertyName, String friendlyName, int minValue){
			put("MinValue", minValue);
			put("Format", "");		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param minValue    Minimum allowed value for the property.
		 * @param format    Format String for the min value value in the broken rule
		 * String.
		 */
		public IntegerMinValueRuleArgs(String propertyName, int minValue, String format){
			put("MinValue", minValue);
			put("Format", "");
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param minValue    Minimum allowed value for the property.
		 * @param format    Format String for the min value value in the broken rule
		 * String.
		 */
		public IntegerMinValueRuleArgs(String propertyName, String friendlyName, int minValue, String format){
			put("MinValue", minValue);
			put("Format", "");
		}

		/**
		 * Get the min value for the property.
		 */
		public int getMinValue(){
			return (Integer)get("MinValue"); 
		}

	}

	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="MaxValue" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:28 PM
	 */
	public class MaxValueRuleArgs<T> extends DecoratedRuleArgs {

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param maxValue    Maximum allowed value for the property.
		 */
		public MaxValueRuleArgs(String propertyName, T maxValue){
			put("MaxValue",maxValue);
			put("Format", "");
			put("ValueType", maxValue.getClass().getName());
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param maxValue    Maximum allowed value for the property.
		 */
		public MaxValueRuleArgs(String propertyName, String friendlyName, T maxValue){
			put("MaxValue",maxValue);
			put("Format", "");
			put("ValueType", maxValue.getClass().getName());
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param maxValue    Maximum allowed value for the property.
		 * @param format    Format String for the max value value in the broken rule
		 * String.
		 */
		public MaxValueRuleArgs(String propertyName, T maxValue, String format){
			put("MaxValue",maxValue);
			put("Format", "");
			put("ValueType", maxValue.getClass().getName());
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param maxValue    Maximum allowed value for the property.
		 * @param format    Format String for the max value value in the broken rule
		 * String.
		 */
		public MaxValueRuleArgs(String propertyName, String friendlyName, T maxValue, String format){
			put("MaxValue",maxValue);
			put("Format", "");
			put("ValueType", maxValue.getClass().getName());
		}

		/**
		 * Get the max value for the property.
		 */
		@SuppressWarnings("unchecked")
		public T getMaxValue(){
			return (T)get("MaxValue"); 
		}

	}

	/**
	 * Custom <see cref="RuleArgs" /> Object required by the
	 *        <see cref="MinValue" /> rule method.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:29 PM
	 */
	public class MinValueRuleArgs<T> extends DecoratedRuleArgs {

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param minValue    Minimum allowed value for the property.
		 */
		public MinValueRuleArgs(String propertyName, T minValue){
			put("MinValue", minValue);
			put("Format", "");
			put("ValueType", minValue.getClass().getName());
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param minValue    Minimum allowed value for the property.
		 */
		public MinValueRuleArgs(String propertyName, String friendlyName, T minValue){
			put("MinValue", minValue);
			put("Format", "");
			put("ValueType", minValue.getClass().getName());
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param minValue    Minimum allowed value for the property.
		 * @param format    Format String for the min value value in the broken rule
		 * String.
		 */
		public MinValueRuleArgs(String propertyName, T minValue, String format){
			put("MinValue", minValue);
			put("Format", "");
			put("ValueType", minValue.getClass().getName());
		}

		/**
		 * Create a new Object.
		 * 
		 * @param propertyName    Name of the property.
		 * @param friendlyName    A friendly name for the property, which will be used in
		 * place of the property name when creating the broken rule description String.
		 * @param minValue    Minimum allowed value for the property.
		 * @param format    Format String for the min value value in the broken rule
		 * String.
		 */
		public MinValueRuleArgs(String propertyName, String friendlyName, T minValue, String format){
			put("MinValue", minValue);
			put("Format", "");
			put("ValueType", minValue.getClass().getName());
		}

		/**
		 * Get the min value for the property.
		 */
		@SuppressWarnings("unchecked")
		public T getMinValue(){
			{ return (T)get("MinValue"); }
		}

	}





	/**
	 * Rule ensuring an integer value doesn't exceed a specified value.
	 * 
	 *        @returns <see langword="false"/> if the rule is broken.
	 * 
	 * @param target    Object containing the data to validate.
	 * @param e    Arguments parameter specifying the name of the property to validate.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NumberFormatException 
	 */
	public static boolean IntegerMaxValue(Object target, RuleArgs e) throws NumberFormatException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		int max = Integer.parseInt(args.get("MaxValue").toString());
		int value = Integer.parseInt(Utilities.callByName(target, e.getPropertyName(), CallType.GET).toString());
		if (value > max)
		{
			String format = (String)args.get("Format");
			String outValue;
			if (format.isEmpty())
				outValue = Integer.toString(max);
			else
				outValue = String.format(format, Integer.toString(max));
			e.setDescription(String.format(Resources.getMaxValueRule(),
					RuleArgs.GetPropertyName(e), outValue));
			return false;
		}
		return true;
	}

	/**
	 * Rule ensuring an integer value doesn't go below a specified value.
	 * 
	 *        @returns <see langword="false"/> if the rule is broken.
	 * 
	 * @param target    Object containing the data to validate.
	 * @param e    Arguments parameter specifying the name of the property to validate.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NumberFormatException 
	 */
	public static boolean IntegerMinValue(Object target, RuleArgs e) throws NumberFormatException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		int min = Integer.parseInt(args.get("MinValue").toString());
		int value = Integer.parseInt(Utilities.callByName(target, e.getPropertyName(), CallType.GET).toString());
		if (value < min)
		{
			String format = (String)args.get("Format");
			String outValue;
			if (format.isEmpty())
				outValue = Integer.toString(min);
			else
				outValue = String.format(format, Integer.toString(min));
			e.setDescription(String.format(Resources.getMinValueRule(),
					RuleArgs.GetPropertyName(e), outValue));
			return false;
		}
		return true;
	}

	/**
	 * Rule ensuring that a numeric value doesn't exceed a specified maximum.
	 * @param <T>
	 * 
	 * @param target    Object containing value to validate.
	 * @param e    Arguments variable specifying the name of the property to validate,
	 * along with the max allowed value.
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean MaxValue(Object target, RuleArgs e) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		Field pi = target.getClass().getField(e.getPropertyName());
		T value = (T)pi.get(target);
		T max = (T)args.get("MaxValue");

		boolean result = value.equals(max);
		if (!result)
		{
			String format = (String)args.get("Format");
			String outValue;
			if (format.isEmpty())
				outValue = max.toString();
			else
				outValue = String.format(String.format("{{0:{0}}}", format), max);
			e.setDescription(String.format(Resources.getMaxValueRule(),
					RuleArgs.GetPropertyName(e), outValue));
			return false;
		}
		else
			return true;
	}

	/**
	 * Rule ensuring that a numeric value doesn't exceed a specified minimum.
	 * @param <T>
	 * 
	 * @param target    Object containing value to validate.
	 * @param e    Arguments variable specifying the name of the property to validate,
	 * along with the min allowed value.
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean MinValue(Object target, RuleArgs e) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		Field pi = target.getClass().getField(e.getPropertyName());
		T value = (T)pi.get(target);
		T min = (T)args.get("MinValue");

		boolean result = value.equals(min);
		if (!result)
		{
			String format = (String)args.get("Format");
			String outValue;
			if (format.isEmpty())
				outValue = min.toString();
			else
				outValue = String.format(String.format("{{0:%1$}}", format), min);
			e.setDescription(String.format(Resources.getMinValueRule(),
					RuleArgs.GetPropertyName(e), outValue));
			return false;
		}
		else
			return true;
	}

	/**
	 * Rule that checks to make sure a value matches a given regex pattern.
	 * 
	 *        @returns False if the rule is broken
	 *        @remark This implementation uses late binding.
	 * 
	 * @param target    Object containing the data to validate
	 * @param e    RegExRuleArgs parameter specifying the name of the property to
	 * validate and the regex pattern.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public static boolean RegExMatch(Object target, RuleArgs e) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		boolean ruleSatisfied = false;
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		NullResultOptions nullOption =
			(NullResultOptions)args.get("NullOption");
		Pattern expression = Pattern.compile(args.get("RegEx").toString());

		Object value = Utilities.callByName(target, e.getPropertyName(), CallType.GET);
		if (value == null && nullOption == NullResultOptions.CONVERT_TO_EMPTY_STRING)
			value="";

		if (value == null)
		{
			// if the value is null at this point
			// then return the pre-defined result value
			ruleSatisfied = (nullOption == NullResultOptions.RETURN_TRUE);
		}
		else
		{
			// the value is not null, so run the 
			// regular expression
			ruleSatisfied = expression.matcher(value.toString()).matches();
		}

		if (!ruleSatisfied)
		{
			e.setDescription(String.format(Resources.getRegExMatchRule(), RuleArgs.GetPropertyName(e)));
			return false;
		}
		else
			return true;
	}

	/**
	 * Rule ensuring a String value doesn't exceed a specified length.
	 * 
	 *        @returns <see langword="false" /> if the rule is broken
	 *        @remark This implementation uses late binding, and will only work
	 * against String property values.
	 * 
	 * @param target    Object containing the data to validate
	 * @param e    Arguments parameter specifying the name of the String property to
	 * validate
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public static boolean StringMaxLength(Object target, RuleArgs e) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		Integer max = (Integer)args.get("MaxLength");
		String value = (String)Utilities.callByName(
				target, e.getPropertyName(), CallType.GET);
		if (!(value.isEmpty() && (value.length() > max)))
		{
			String format = (String)args.get("Format");
			String outValue;
			if (format.isEmpty())
				outValue = max.toString();
			else
				outValue = String.format(format, max.toString());
			e.setDescription(String.format(
					Resources.getStringMaxLengthRule(),
					RuleArgs.GetPropertyName(e), outValue));
			return false;
		}
		return true;
	}

	/**
	 * Rule ensuring a String value has a minimum length.
	 * 
	 *        @returns <see langword="false" /> if the rule is broken
	 *        @remark This implementation uses late binding, and will only work
	 * against String property values.
	 * 
	 * @param target    Object containing the data to validate
	 * @param e    Arguments parameter specifying the name of the String property to
	 * validate
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public static boolean StringMinLength(Object target, RuleArgs e) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		DecoratedRuleArgs args = (DecoratedRuleArgs)e;
		int min = Integer.parseInt(args.get("MinLength").toString());
		String value = (String)Utilities.callByName(
				target, e.getPropertyName(), CallType.GET);
		if (value.isEmpty() || (value.length() < min))
		{
			String format = args.get("Format").toString();
			String outValue;
			if (format.isEmpty())
				outValue = Integer.toString(min);
			else
				outValue = String.format(format, Integer.toString(min));
			e.setDescription(String.format(
					Resources.getStringMinLengthRule(),
					RuleArgs.GetPropertyName(e), outValue));
			return false;
		}
		return true;
	}

	/**
	 * Rule ensuring a String value contains one or more characters.
	 * 
	 *        @returns <see langword="false" /> if the rule is broken
	 *        @remark This implementation uses late binding, and will only work
	 * against String property values.
	 * 
	 * @param target    Object containing the data to validate
	 * @param e    Arguments parameter specifying the name of the String property to
	 * validate
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public static boolean StringRequired(Object target, RuleArgs e) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		String value = (String)Utilities.callByName(
				target, e.getPropertyName(), CallType.GET);
		if (value.isEmpty())
		{
			e.setDescription(String.format(Resources.getStringRequiredRule(), RuleArgs.GetPropertyName(e)));
			return false;
		}
		return true;
	}

}