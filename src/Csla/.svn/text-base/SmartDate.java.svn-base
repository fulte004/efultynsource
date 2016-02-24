package Csla;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Csla.Properties.Resources;

/**
 * Provides a date data type that understands the concept of an empty date value.
 * 
 *    @remark See Chapter 5 for a full discussion of the need for this data type
 * and the design choices behind it.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:45 PM
 */
public class SmartDate implements Comparable<SmartDate> {

	/**
	 * Indicates the empty value of a SmartDate.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:45 PM
	 */
	public enum EmptyValue {
		/**
		 * Indicates that an empty SmartDate is the smallest date.
		 * 
		 *        
		 */
		MinDate,
		/**
		 * Indicates that an empty SmartDate is the largest date.
		 * 
		 *        
		 */
		MaxDate
	}

	private Calendar _date;
	private static String _defaultFormat;
	private EmptyValue _emptyValue;
	private String _format;
	private boolean _initialized;



	public void finalize() throws Throwable {

	}

	protected SmartDate(){
		_defaultFormat = "d";
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 * @param emptyIsMin    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public SmartDate(boolean emptyIsMin){
		_emptyValue = getEmptyValue(emptyIsMin);
		_format = null;
		_initialized = false;
		// provide a dummy value to allow real initialization
		_date = new GregorianCalendar(1900,1,1);
		setEmptyDate(_emptyValue);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public SmartDate(EmptyValue emptyValue){
		_emptyValue = emptyValue;
		_format = null;
		_initialized = false;
		// provide a dummy value to allow real initialization
		_date = new GregorianCalendar(1900,1,1);
		setEmptyDate(_emptyValue);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 *      @remark The SmartDate created will use the min possible date to represent
	 * an empty date.
	 * 
	 * @param value    The initial value of the Object.
	 */
	public SmartDate(Calendar value){
		_emptyValue = Csla.SmartDate.EmptyValue.MinDate;
		_format = null;
		_initialized = false;
		_date = new GregorianCalendar(1900,1,1);
		setDate(value);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 * @param value    The initial value of the Object.
	 * @param emptyIsMin    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public SmartDate(Calendar value, boolean emptyIsMin){
		_emptyValue = getEmptyValue(emptyIsMin);
		_format = null;
		_initialized = false;
		_date = new GregorianCalendar(1900,1,1);
		setDate(value);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 * @param value    The initial value of the Object.
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public SmartDate(Calendar value, EmptyValue emptyValue){
		_emptyValue = emptyValue;
		_format = null;
		_initialized = false;
		_date = new GregorianCalendar(1900,1,1);
		setDate(value);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 *      @remark The SmartDate created will use the min possible date to represent
	 * an empty date.
	 * 
	 * @param value    The initial value of the Object (as text).
	 */
	public SmartDate(String value){
		_emptyValue = EmptyValue.MinDate;
		_format = null;
		_initialized = true;
		_date = new GregorianCalendar(1900,1,1);
		this.setText(value);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 * @param value    The initial value of the Object (as text).
	 * @param emptyIsMin    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public SmartDate(String value, boolean emptyIsMin){
		_emptyValue = getEmptyValue(emptyIsMin);
		_format = null;
		_initialized = true;
		_date = new GregorianCalendar(1900,1,1);
		this.setText(value);
	}

	/**
	 * Creates a new SmartDate Object.
	 * 
	 * @param value    The initial value of the Object (as text).
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public SmartDate(String value, EmptyValue emptyValue){
		_emptyValue = emptyValue;
		_format = null;
		_initialized = true;
		_date = new GregorianCalendar(1900,1,1);
		this.setText(value);
	}

	/**
	 * Adds a TimeSpan onto the Object.
	 * 
	 * @param value    Span to add to the date.
	 */
	public Calendar add(TimeSpan value){
		if (isEmpty())
			return this.getDate();
		else
		{
			Calendar temp = this.getDate();
			temp.add(Calendar.MILLISECOND, value.getMilliseconds());
			return temp;
		}
	}

	/**
	 * Compares one SmartDate to another.
	 * 
	 *      @remark This method works the same as the Date.CompareTo method on the
	 * Date datetype, with the exception that it understands the concept of empty date
	 * values.
	 *      @returns A value indicating if the comparison date is less than, equal to
	 * or greater than this date.
	 * 
	 * @param value    The date to which we are being compared.
	 */
	public int compareTo(SmartDate value){
		if (this.isEmpty() && value.isEmpty())
			return 0;
		else
			return _date.compareTo(value.getDate());
	}

	/**
	 * Compares one SmartDate to another.
	 * 
	 *      @remark This method works the same as the Date.compareTo method on the
	 * Date datetype, with the exception that it understands the concept of empty date
	 * values.
	 *      @returns A value indicating if the comparison date is less than, equal to
	 * or greater than this date.
	 * 
	 * @param value    The date to which we are being compared.
	 */
//	private int compareTo(Object value){
//		if (value instanceof SmartDate)
//			return compareTo((SmartDate)value);
//		else
//			throw new IllegalArgumentException(Resources.getValueNotSmartDateException());
//	}

	/**
	 * Compares a SmartDate to a text date value.
	 * 
	 *      @returns A value indicating if the comparison date is less than, equal to
	 * or greater than this date.
	 * 
	 * @param value    The date to which we are being compared.
	 */
	public int compareTo(String value){
		return this.getDate().compareTo(stringToDate(value, _emptyValue));
	}

	/**
	 * Compares a SmartDate to a date value.
	 * 
	 *      @returns A value indicating if the comparison date is less than, equal to
	 * or greater than this date.
	 * 
	 * @param value    The date to which we are being compared.
	 */
	public int compareTo(Calendar value){
		return this.getDate().compareTo(value);
	}

	/**
	 * Gets or sets the date value.
	 */
	public Calendar getDate(){
		if (!_initialized)
		{
			_date = new GregorianCalendar(1900,1,1);
			_initialized = true;
		}
		return _date;
	}
	public void setDate(Calendar value){
		_date = value;
		_initialized = true;
	}
	/**
	 * Converts a date value into a text representation.
	 * 
	 *      @remark The date is considered empty if it matches the min value for the
	 * Date datatype. If the date is empty, this method returns an empty String.
	 * Otherwise it returns the date value formatted based on the FormatString
	 * parameter.
	 *      @returns Text representation of the date value.
	 * 
	 * @param value    The date value to convert.
	 * @param formatString    The format String used to format the date into text.
	 */
	public static String dateToString(Calendar value, String formatString){
		return dateToString(value, formatString, true);
	}

	/**
	 * Converts a date value into a text representation.
	 * 
	 *      @remark Whether the date value is considered empty is determined by the
	 * EmptyIsMin parameter value. If the date is empty, this method returns an empty
	 * String. Otherwise it returns the date value formatted based on the FormatString
	 * parameter.
	 *      @returns Text representation of the date value.
	 * 
	 * @param value    The date value to convert.
	 * @param formatString    The format String used to format the date into text.
	 * @param emptyIsMin    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public static String dateToString(Calendar value, String formatString, boolean emptyIsMin){
		return dateToString(value, formatString, getEmptyValue(emptyIsMin));
	}

	/**
	 * Converts a date value into a text representation.
	 * 
	 *      @remark Whether the date value is considered empty is determined by the
	 * EmptyIsMin parameter value. If the date is empty, this method returns an empty
	 * String. Otherwise it returns the date value formatted based on the FormatString
	 * parameter.
	 *      @returns Text representation of the date value.
	 * 
	 * @param value    The date value to convert.
	 * @param formatString    The format String used to format the date into text.
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public static String dateToString(Calendar value, String formatString, EmptyValue emptyValue){
		if (emptyValue == EmptyValue.MinDate)
		{
			if (value == new GregorianCalendar(1900,1,1))
				return "";
		}
		else
		{
			if (value == new GregorianCalendar(9999,12,31))
				return "";
		}
		return String.format("{0:" + formatString + "}", value);
	}

	/**
	 * Gets a database-friendly version of the date value.
	 * 
	 *      @remark  If the SmartDate contains an empty date, this returns <see
	 * cref="DBNull"/>. Otherwise the actual date value is returned as type Date.
	 *       This property is very useful when setting parameter values for a Command
	 * Object, since it automatically stores null values into the database for empty
	 * date values.
	 *       When you also use the SafeDataReader and its GetSmartDate method, you can
	 * easily read a null value from the database back into a SmartDate Object so it
	 * remains considered as an empty date value.
	 */
	public Object getDBValue(){
		if (this.isEmpty())
			return null;
		else
			return this.getDate();
	}

	/**
	 * Gets a value indicating whether an empty date is the min or max possible date
	 * value.
	 * 
	 *      @remark Whether an empty date is considered to be the smallest or largest
	 * possible date is only important for comparison operations. This allows you to
	 * compare an empty date with a real date and get a meaningful result.
	 */
	public boolean getEmptyIsMin(){
		return (_emptyValue == EmptyValue.MinDate); 
	}

	/**
	 * Compares this Object to another <see cref="SmartDate"/> for equality.
	 * 
	 * @param obj    Object to compare for equality.
	 */
	public boolean equals(Object obj){
		if (obj instanceof SmartDate)
		{
			SmartDate tmp = (SmartDate)obj;
			if (this.isEmpty() && tmp.isEmpty())
				return true;
			else
				return this.getDate().equals(tmp.getDate());
		}
		else if (obj instanceof Date)
			return this.getDate().equals((Date)obj);
		else if (obj instanceof String)
			return (this.compareTo(obj.toString()) == 0);
		else
			return false;
	}

	/**
	 * Gets or sets the format String used to format a date value when it is returned
	 * as text.
	 * 
	 *      @remark The format String should follow the requirements for the .NET
	 * System.String.Format statement.
	 *      @value A format String.
	 */
	public String getFormatString(){ 
		if (_format == null)
			_format = _defaultFormat;
		return _format;	      
	}
	public void setFormatString(String value){
		_format = value;
	}

	/**
	 * 
	 * @param emptyIsMin
	 */
	private static EmptyValue getEmptyValue(boolean emptyIsMin){
		if (emptyIsMin)
			return EmptyValue.MinDate;
		else
			return EmptyValue.MaxDate;
	}

	/**
	 * Returns a hash code for this Object.
	 */
	public int getHashCode(){
		return this.getDate().hashCode();
	}

	/**
	 * Gets a value indicating whether this Object contains an empty date.
	 */
	public boolean isEmpty(){

		if (_emptyValue == EmptyValue.MinDate)
			return this.getDate().equals(new GregorianCalendar(1900,1,1));
		else
			return this.getDate().equals(new GregorianCalendar(9999,12,31));

	}

	/**
	 * Subtraction operator
	 * 
	 *      @returns
	 * 
	 * @param start    Original date/time
	 * @param span    Span to subtract
	 */
	public static SmartDate subtract(SmartDate start, TimeSpan span){
		return new SmartDate(start.subtract(span), start.getEmptyIsMin());
	}

	/**
	 * Subtraction operator
	 * 
	 *      @returns
	 * 
	 * @param start    Original date/time
	 * @param finish    Second date/time
	 */
	public static TimeSpan subtract(SmartDate start, SmartDate finish){
		return start.subtract(finish.getDate());
	}

	/**
	 * Inequality operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean notEqualTo(SmartDate obj1, SmartDate obj2){
		return !obj1.equals(obj2);
	}

	/**
	 * Inequality operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean notEqualTo(SmartDate obj1, Date obj2){
		return !obj1.equals(obj2);
	}

	/**
	 * Inequality operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean notEqualTo(SmartDate obj1, String obj2){
		return !obj1.equals(obj2);
	}

	/**
	 * Addition operator
	 * 
	 *      @returns
	 * 
	 * @param start    Original date/time
	 * @param span    Span to add
	 */
	public static SmartDate add(SmartDate start, TimeSpan span){
		return new SmartDate(start.add(span), start.getEmptyIsMin());
	}

	/**
	 * Less than operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean lessThan(SmartDate obj1, SmartDate obj2){
		return obj1.compareTo(obj2) < 0;
	}

	/**
	 * Less than operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean lessThan(SmartDate obj1, Calendar obj2){
		return obj1.compareTo(obj2) < 0;
	}

	/**
	 * Less than operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	//	public static boolean lessThan(SmartDate obj1, SmartDate obj2){
	//		return obj1.compareTo(obj2) < 0;
	//	}

	/**
	 * Less than or equals operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean lessThanOrEqualTo(SmartDate obj1, SmartDate obj2){
		return obj1.compareTo(obj2) <= 0;
	}

	/**
	 * Less than or equals operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean lessThanOrEqualTo(SmartDate obj1, Calendar obj2){
		return obj1.compareTo(obj2) <= 0;
	}

	/**
	 * Less than or equals operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean olessThanOrEqualTo(SmartDate obj1, String obj2){
		return obj1.compareTo(obj2) <= 0;
	}

	/**
	 * Equality operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean equals(SmartDate obj1, SmartDate obj2){
		return obj1.equals(obj2);
	}

	/**
	 * Equality operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean equals(SmartDate obj1, Calendar obj2){
		return obj1.equals(obj2);
	}

	/**
	 * Equality operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean equals(SmartDate obj1, String obj2){
		return obj1.equals(obj2);
	}

	/**
	 * Greater than operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean greaterThan(SmartDate obj1, SmartDate obj2){
		return obj1.compareTo(obj2) > 0;
	}

	/**
	 * Greater than operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean greaterThan(SmartDate obj1, Calendar obj2){
		return obj1.compareTo(obj2) > 0;
	}

	/**
	 * Greater than operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean greaterThan(SmartDate obj1, String obj2){
		return obj1.compareTo(obj2) > 0;
	}

	/**
	 * Greater than or equals operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean greaterThanOrEqualTo(SmartDate obj1, SmartDate obj2){
		return obj1.compareTo(obj2) >= 0;
	}

	/**
	 * Greater than or equals operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean greaterThanOrEqualTo(SmartDate obj1, Calendar obj2){
		return obj1.compareTo(obj2) >= 0;
	}

	/**
	 * Greater than or equals operator
	 * 
	 *      @returns
	 * 
	 * @param obj1    First Object
	 * @param obj2    Second Object
	 */
	public static boolean greaterThanOrEqualTo(SmartDate obj1, String obj2){
		return obj1.compareTo(obj2) >= 0;
	}

	/**
	 * Converts a String value into a SmartDate.
	 * 
	 *      @returns A new SmartDate containing the date value.
	 *      @remark EmptyIsMin will default to <see langword="true"/>.
	 * 
	 * @param value    String containing the date value.
	 */
	public static SmartDate parse(String value){
		return new SmartDate(value);
	}

	/**
	 * Converts a String value into a SmartDate.
	 * 
	 *      @returns A new SmartDate containing the date value.
	 * 
	 * @param value    String containing the date value.
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public static SmartDate parse(String value, EmptyValue emptyValue){
		return new SmartDate(value, emptyValue);
	}

	/**
	 * Converts a String value into a SmartDate.
	 * 
	 *      @returns A new SmartDate containing the date value.
	 * 
	 * @param value    String containing the date value.
	 * @param emptyIsMin    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public static SmartDate parse(String value, boolean emptyIsMin){
		return new SmartDate(value, emptyIsMin);
	}

	/**
	 * Sets the global default format String used by all new SmartDate values going
	 * forward.
	 * 
	 *      @remark The default global format String is "d" unless this method is
	 * called to change that value. Existing SmartDate values are unaffected by this
	 * method, only SmartDate values created after calling this method are affected.
	 * 
	 * @param formatString    The format String should follow the requirements for the
	 * .NET System.String.Format statement.
	 */
	public static void setDefaultFormatString(String formatString){
		_defaultFormat = formatString;
	}

	/**
	 * 
	 * @param emptyValue
	 */
	private void setEmptyDate(EmptyValue emptyValue){
		if (emptyValue == SmartDate.EmptyValue.MinDate)
			this.setDate(new GregorianCalendar(1900,1,1));
		else
			this.setDate(new GregorianCalendar(9999,12,31));
	}

	/**
	 * Converts a text date representation into a Calendar value.
	 * 
	 *      @remark An empty String is assumed to represent an empty date. An empty
	 * date is returned as the MinValue of the Calendar datatype.
	 *      @returns A Calendar value.
	 * 
	 * @param value    The text representation of the date.
	 */
	public static Calendar stringToDate(String value){
		return stringToDate(value, true);
	}

	/**
	 * Converts a text date representation into a Calendar value.
	 * 
	 *      @remark An empty String is assumed to represent an empty date. An empty
	 * date is returned as the MinValue or MaxValue of the Calendar datatype depending on
	 * the EmptyIsMin parameter.
	 *      @returns A Calendar value.
	 * 
	 * @param value    The text representation of the date.
	 * @param emptyIsMin    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public static Calendar stringToDate(String value, boolean emptyIsMin){
		return stringToDate(value, getEmptyValue(emptyIsMin));
	}

	/**
	 * Converts a text date representation into a Calendar value.
	 * 
	 *      @remark An empty String is assumed to represent an empty date. An empty
	 * date is returned as the MinValue or MaxValue of the Calendar datatype depending on
	 * the EmptyIsMin parameter.
	 *      @returns A Calendar value.
	 * 
	 * @param value    The text representation of the date.
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 */
	public static Calendar stringToDate(String value, EmptyValue emptyValue){
		Calendar result = new GregorianCalendar(1900,1,1);
		if (tryStringToDate(value, emptyValue, result))
			return result;
		else
			throw new IllegalArgumentException(Resources.getStringToDateException());
	}

	/**
	 * Subtracts a TimeSpan from the Object.
	 * 
	 * @param value    Span to subtract from the date.
	 */
	public Calendar subtract(TimeSpan value){
		if (isEmpty())
			return this.getDate();
		else
		{
			Calendar temp = this.getDate();
			temp.add(Calendar.MILLISECOND, (int) (-1*value.getMilliseconds()));
			return temp;
		}
	}

	/**
	 * Subtracts a Calendar from the Object.
	 * 
	 * @param value    Calendar to subtract from the date.
	 */
	public TimeSpan subtract(Calendar value){
		if (isEmpty())
			return TimeSpan.getZero();
		else
		{
			Calendar temp = this.getDate();
			temp.add(Calendar.MILLISECOND, (int) (-1*value.getTimeInMillis()));
			return new TimeSpan(temp.getTimeInMillis());
		}
	}

	/**
	 * Gets or sets the date value.
	 * 
	 *      @remark  This property can be used to set the date value by passing a text
	 * representation of the date. Any text date representation that can be parsed by
	 * the .NET runtime is valid.
	 *       When the date value is retrieved via this property, the text is formatted
	 * by using the format specified by the
	 *      <see cref="FormatString" /> property. The default is the short date format
	 * (d).
	 */
	public String getText(){
		return dateToString(this.getDate(), getFormatString(), _emptyValue); 
	}
	public void setText(String value){
		this.setDate(stringToDate(value, _emptyValue)); 
	}
	/**
	 * Returns a text representation of the date value.
	 */
	public String toString(){
		return this.getText();
	}

	/**
	 * Returns a text representation of the date value.
	 * 
	 * @param format    A standard .NET format String.
	 */
	public String ToString(String format){
		return dateToString(this.getDate(), format, _emptyValue);
	}

	/**
	 * Converts a String value into a SmartDate.
	 * 
	 *      @returns A value indicating if the parse was successful.
	 * 
	 * @param value    String containing the date value.
	 * @param result    The resulting SmartDate value if the parse was successful.
	 */
	public static boolean tryParse(String value, SmartDate result){
		return tryParse(value, EmptyValue.MinDate, result);
	}

	/**
	 * Converts a String value into a SmartDate.
	 * 
	 *      @returns A value indicating if the parse was successful.
	 * 
	 * @param value    String containing the date value.
	 * @param emptyValue    Indicates whether an empty date is the min or max date
	 * value.
	 * @param result    The resulting SmartDate value if the parse was successful.
	 */
	public static boolean tryParse(String value, EmptyValue emptyValue, SmartDate result){
		Calendar dateResult = new GregorianCalendar(1900,1,1);
		if (tryStringToDate(value, emptyValue, dateResult))
		{
			result = new SmartDate(dateResult, emptyValue);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 
	 * @param value
	 * @param emptyValue
	 * @param result
	 */
	private static boolean tryStringToDate(String value, EmptyValue emptyValue, Calendar result){
//		Calendar tmp;
		if (value.isEmpty())
		{
			if (emptyValue == EmptyValue.MinDate)
			{
				result = new GregorianCalendar(1900,1,1);
				
				return true;
			}
			else
			{
				result = new GregorianCalendar(9999,12,31);
				return true;
			}
		}
//		else if (Date.tryParse(value, tmp))
//		{
//			result = tmp;
//			return true;
//		}
		else
		{
			String ldate = value.trim().toLowerCase();
			if (ldate == Resources.getSmartDateT() ||
					ldate == Resources.getSmartDateToday() ||
					ldate == ".")
			{
				result = Calendar.getInstance(); // Current date
				return true;
			}
			if (ldate == Resources.getSmartDateY() ||
					ldate == Resources.getSmartDateYesterday() ||
					ldate == "-")
			{
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				result = cal;
				return true;
			}
			if (ldate == Resources.getSmartDateTom() ||
					ldate == Resources.getSmartDateTomorrow() ||
					ldate == "+")
			{
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 1);
				result = cal;
				return true;
			}
		}
		return false;
	}

}