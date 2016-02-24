package Csla;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import Csla.Properties.Resources;

/**
 * @author Eric
 * @version 1.0
 * @created 13-Feb-2010 10:41:00 AM
 */
public class TimeSpan implements Comparable<TimeSpan>{

	private static long TicksPerDay = 864000000000L;
	private static long TicksPerHour = 36000000000L;
	private static long TicksPerMillisecond = 10000L;
	private static long TicksPerMinute = 600000000L;
	private static long TicksPerSecond = 10000000L;

	private static double MinutesPerTick = 1.6666666666666667E-09;
	private static double SecondsPerTick = 1E-07;
    private static double DaysPerTick = 1.1574074074074074E-12;
    private static int MillisPerSecond = 0x3e8;
    private static int MillisPerMinute = 0xea60;
    private static int MillisPerHour = 0x36ee80;
    private static int MillisPerDay = 0x5265c00;
    private static long MaxSeconds = 0xd6bf94d5e5L;
    private static long MinSeconds = -922337203685L;
    private static long MaxMilliSeconds = 0x346dc5d638865L;
    private static long MinMilliSeconds = -922337203685477L;

	long _ticks;

	private static TimeSpan MaxValue;
	private static TimeSpan MinValue;
	private static TimeSpan Zero;

    // Nested Types
    private enum ParseError
    {
        FORMAT,
        OVERFLOW,
        OVERFLOW_HOURS_MINUTES_SECONDS,
        ARGUMENT_NULL
    }
	/**
	 * Initializes a new TimeSpan to the specified number of ticks. 
	 * @param ticks A time period expressed in 100-nanosecond units.
	 */
	public TimeSpan(long ticks){
		 this._ticks = ticks;
	}

	/**
	 * Initializes a new TimeSpan to a specified number of hours, minutes, and seconds. 
	 * @param hours Number of hours. 
	 * @param minutes Number of minutes.
	 * @param seconds Number of seconds.
	 * @throws OverflowException The resulting TimeSpan is less than MinValue or greater than MaxValue.
	 */
	public TimeSpan(int hours, int minutes, int seconds) throws OverflowException{
	    this._ticks = timeToTicks(hours, minutes, seconds);
	}

	/**
	 * Initializes a new TimeSpan to a specified number of days, hours, minutes, and seconds.
	 * @param days Number of days.
	 * @param hours Number of hours. 
	 * @param minutes Number of minutes.
	 * @param seconds Number of seconds.
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	public TimeSpan(int days, int hours, int minutes, int seconds) throws InvalidPropertiesFormatException, IOException{
		this(days, hours, minutes, seconds, 0);
	}

	/**
	 * Initializes a new TimeSpan to a specified number of days, hours, minutes, seconds, and milliseconds.
	 * @param days Number of days.
	 * @param hours Number of hours. 
	 * @param minutes Number of minutes.
	 * @param seconds Number of seconds.
	 * @param milliseconds
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	public TimeSpan(int days, int hours, int minutes, int seconds, int milliseconds) throws InvalidPropertiesFormatException, IOException{
	    long num = ((((((days * 0xe10L) * 0x18L) + (hours * 0xe10L)) + (minutes * 60L)) + seconds) * 0x3e8L) + milliseconds;
	    if ((num > MaxMilliSeconds) || (num < MinMilliSeconds))
	    {
	        throw new IndexOutOfBoundsException(Resources.getOverflow_TimeSpanTooLong());
	    }
	    this._ticks = num * 0x2710L;
	}

	public TimeSpan(){
	    Zero = new TimeSpan(0L);
	    MaxValue = new TimeSpan(MaxMilliSeconds);
	    MinValue = new TimeSpan(MinMilliSeconds);

	}

	/**
	 * Adds the specified TimeSpan to this instance. 
	 * @param ts A TimeSpan. 
	 * @return A TimeSpan that represents the value of this instance plus the value of ts.
	 * @throws IOException 
	 * @throws OverflowException The resulting TimeSpan is less than MinValue or greater than MaxValue.
	 * @throws InvalidPropertiesFormatException 
	 */
	public TimeSpan add(TimeSpan ts) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    long ticks = this._ticks + ts._ticks;
	    if (((this._ticks >> 0x3f) == (ts._ticks >> 0x3f)) && ((this._ticks >> 0x3f) != (ticks >> 0x3f)))
	    {
	        throw new OverflowException(Resources.getOverflow_TimeSpanTooLong());
	    }
	    return new TimeSpan(ticks);

	}

	/**
	 * Compares two TimeSpan values and returns an integer that indicates whether the first value is shorter than, equal to, 
	 * or longer than the second value.
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return Value Condition -1 t1 is shorter than t2 0 t1 is equal to t2 1 t1 is longer than t2
	 */
	public static int compare(TimeSpan t1, TimeSpan t2){
	    if (t1._ticks > t2._ticks)
	    {
	        return 1;
	    }
	    if (t1._ticks < t2._ticks)
	    {
	        return -1;
	    }
	    return 0;
	}

	/**
	 * 
	 * @param <T>
	 * @param value
	 */
//	@Override
//	public <T> int compareTo(T value){
//		   if (value == null)
//		    {
//		        return 1;
//		    }
//		    if (!(value instanceof TimeSpan))
//		    {
//		        throw new IllegalArgumentException(Resources.getArg_MustBeTimeSpan"));
//		    }
//		    long num = ((TimeSpan) value)._ticks;
//		    if (this._ticks > num)
//		    {
//		        return 1;
//		    }
//		    if (this._ticks < num)
//		    {
//		        return -1;
//		    }
//		    return 0;
//
//	}

	/**
	 * Compares this instance to a specified TimeSpan object and returns an integer that indicates whether this instance 
	 * is shorter than, equal to, or longer than the TimeSpan object.
	 * 
	 * @param value A TimeSpan object to compare to this instance.
	 * @return A signed number indicating the relative values of this instance and value. Value Description A negative integer 
	 * This instance is shorter than value. Zero This instance is equal to value. A positive integer This instance is longer than value.
	 */
	public int compareTo(TimeSpan value){
	    long num = value._ticks;
	    if (this._ticks > num)
	    {
	        return 1;
	    }
	    if (this._ticks < num)
	    {
	        return -1;
	    }
	    return 0;
	}

	/**
	 * Returns a new TimeSpan object whose value is the absolute value of the current TimeSpan object.
	 * @return A new TimeSpan whose value is the absolute value of the current TimeSpan object.
	 * @throws OverflowException The value of this instance is MinValue. 
	 */
	public TimeSpan duration() throws OverflowException{
	    if (this._ticks == MinValue._ticks)
	    {
	        throw new OverflowException(Resources.getOverflow_Duration());
	    }
	    return new TimeSpan((this._ticks >= 0L) ? this._ticks : -this._ticks);

	}

	/**
	 * Returns a value indicating whether this instance is equal to a specified object.
	 * @param value An object to compare with this instance.
	 * @return true if value is a TimeSpan object that represents the same time interval as the current TimeSpan structure; otherwise, false.
	 */
	public boolean equals(Object value){
	    return ((value instanceof TimeSpan) && (this._ticks == ((TimeSpan) value)._ticks));
	}

	/**
	 * Returns a value indicating whether this instance is equal to a specified TimeSpan object.
	 * @param obj An TimeSpan object to compare with this instance.
	 * @return true if obj represents the same time interval as this instance; otherwise, false.
	 */
	public boolean equals(TimeSpan obj){
	    return (this._ticks == obj._ticks);
	}

	/**
	 * Returns a value indicating whether two specified instances of TimeSpan are equal.
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return true if the values of t1 and t2 are equal; otherwise, false.
	 */
	public static boolean equals(TimeSpan t1, TimeSpan t2){
	    return (t1._ticks == t2._ticks);
	}


	/**
	 * Returns a TimeSpan that represents a specified number of days, where the specification is accurate to the nearest millisecond.
	 * @param value A number of days, accurate to the nearest millisecond.
	 * @return A TimeSpan that represents value.
	 * @throws IOException 
	 * @throws OverflowException value is less than MinValue or greater than MaxValue. -or- value is PositiveInfinity. -or- value is NegativeInfinity. 
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan fromDays(double value) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    return interval(value, MillisPerDay);
	}

	/**
	 * Returns a TimeSpan that represents a specified number of hours, where the specification is accurate to the nearest millisecond. 
	 * @param value A number of hours accurate to the nearest millisecond.
	 * @return A TimeSpan that represents value.
	 * @throws IOException 
	 * @throws OverflowException value is less than MinValue or greater than MaxValue. -or- value is PositiveInfinity. -or- value is NegativeInfinity.
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan fromHours(double value) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    return interval(value, MillisPerHour);
	}

	/**
	 * Returns a TimeSpan that represents a specified number of milliseconds.
	 * @param value A number of milliseconds.
	 * @return A TimeSpan that represents value.
	 * @throws IOException 
	 * @throws OverflowException value is less than MinValue or greater than MaxValue. -or- value is PositiveInfinity. -or- value is NegativeInfinity.
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan fromMilliseconds(double value) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    return interval(value, 1);
	}

	/**
	 * Returns a TimeSpan that represents a specified number of minutes, where the specification is accurate to the nearest millisecond.
	 * @param value A number of minutes, accurate to the nearest millisecond.
	 * @return A TimeSpan that represents value.
	 * @throws IOException 
	 * @throws OverflowException value is less than MinValue or greater than MaxValue. -or- value is PositiveInfinity. -or- value is NegativeInfinity.
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan FromMinutes(double value) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    return interval(value, MillisPerMinute);
	}

	/**
	 * Returns a TimeSpan that represents a specified number of seconds, where the specification is accurate to the nearest millisecond.
	 * @param value A number of seconds, accurate to the nearest millisecond.
	 * @return A TimeSpan that represents value.
	 * @throws IOException 
	 * @throws OverflowException A TimeSpan that represents value.
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan FromSeconds(double value) throws InvalidPropertiesFormatException, OverflowException, IOException{
		   return interval(value, MillisPerSecond);
	}

	/**
	 * Returns a TimeSpan that represents a specified time, where the specification is in units of ticks.
	 * @param value A number of ticks that represent a time.
	 * @return A TimeSpan with a value of value.
	 */
	public static TimeSpan FromTicks(long value){
	    return new TimeSpan(value);
	}

	/**
	 * Returns a hash code for this instance.
	 * @return A 32-bit signed integer hash code.
	 */
	public int GetHashCode(){
	    return (((int) this._ticks) ^ ((int) (this._ticks >> 0x20)));
	}

	/**
	 * Represents the maximum TimeSpan value. This field is read-only.
	 */
	public TimeSpan getMaxValue(){
		return MaxValue;
	}

	/**
	 * Represents the minimum TimeSpan value. This field is read-only.
	 */
	public TimeSpan getMinValue(){
		return MinValue;
	}
	
	/**
	 * Represents the number of ticks in 1 day. This field is constant.
	 */
	public long getTicksPerDay(){
		return TicksPerDay;
	}

	/**
	 * Represents the number of ticks in 1 hour. This field is constant.
	 */
	public long getTicksPerHour(){
		return TicksPerHour;
	}

	/**
	 * Represents the number of ticks in 1 millisecond. This field is constant.
	 */
	public long getTicksPerMillisecond(){
		return TicksPerMillisecond;
	}

	/**
	 * Represents the number of ticks in 1 minute. This field is constant.
	 */
	public long getTicksPerMinute(){
		return TicksPerMinute;
	}

	/**
	 * Represents the number of ticks in 1 second. 
	 */
	public long getTicksPerSecond(){
		return TicksPerSecond;
	}

	/**
	 * Represents the zero TimeSpan value. This field is read-only.
	 */
	public static TimeSpan getZero(){
		return Zero;
	}

	/**
	 * Gets the days component of the time interval represented by the current TimeSpan structure.
	 * @return The day component of this instance. The return value can be positive or negative.
	 */
	public int getDays(){
		return (int) (this._ticks / 0xc92a69c000L);
	}
	
	/**
	 * Gets the hours component of the time interval represented by the current TimeSpan structure.
	 * @return The hour component of the current TimeSpan structure. The return value ranges from -23 through 23.
	 */
	public int getHours(){
		return (int) ((this._ticks / 0x861c46800L) % 0x18L);
	}

	/**
	 * 
	 * @param value
	 * @param scale
	 * @throws IOException 
	 * @throws OverflowException 
	 * @throws InvalidPropertiesFormatException 
	 */
	private static TimeSpan interval(double value, int scale) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    if (Double.isNaN(value))
	    {
	        throw new IllegalArgumentException(Resources.getArg_CannotBeNaN());
	    }
	    double num = value * scale;
	    double num2 = num + ((value >= 0.0) ? 0.5 : -0.5);
	    if ((num2 > MaxMilliSeconds) || (num2 < MinMilliSeconds))
	    {
	        throw new OverflowException(Resources.getOverflow_TimeSpanTooLong());
	    }
	    return new TimeSpan(((long) num2) * 0x2710L);

	}

	/**
	 * 
	 * @param n
	 * @param digits
	 */
	private String intToString(int n /*, int digits */){
	    return String.valueOf(n);
	}
	
	/**
	 * Gets the milliseconds component of the time interval represented by the current TimeSpan structure.
	 * @return The millisecond component of the current TimeSpan structure. The return value ranges from -999 through 999.
	 */
	public int getMilliseconds(){
		return (int) ((this._ticks / 0x2710L) % 0x3e8L);
	}

	/**
	 * Gets the minutes component of the time interval represented by the current TimeSpan structure.
	 * @return The minute component of the current TimeSpan structure. The return value ranges from -59 through 59.
	 */
	public int getMinutes(){
		return (int) ((this._ticks / 0x23c34600L) % 60L);
	}

	/**
	 * Returns a TimeSpan whose value is the negated value of this instance. 
	 * @return The same numeric value as this instance, but with the opposite sign.
	 * @throws InvalidPropertiesFormatException
	 * @throws OverflowException The negated value of this instance cannot be represented by a TimeSpan; that is, the value of this instance is MinValue.
	 * @throws IOException
	 */
	public TimeSpan negate() throws InvalidPropertiesFormatException, OverflowException, IOException{
	    if (this._ticks == MinValue._ticks)
	    {
	        throw new OverflowException(Resources.getOverflow_NegateTwosCompNum());
	    }
	    return new TimeSpan(-this._ticks);
	}

	/**
	 * Adds two specified TimeSpan instances.
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return A TimeSpan whose value is the sum of the values of t1 and t2. 
	 * @throws IOException 
	 * @throws OverflowException The resulting TimeSpan is less than MinValue or greater than MaxValue.
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan addition(TimeSpan t1, TimeSpan t2) throws InvalidPropertiesFormatException, OverflowException, IOException{
		return t1.add(t2);
	}

	/**
	 * Indicates whether two TimeSpan instances are equal. 
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return true if the values of t1 and t2 are equal; otherwise, false. 
	 */
	public static boolean equality(TimeSpan t1, TimeSpan t2){
		return (t1._ticks == t2._ticks);
	}

	/**
	 * Indicates whether a specified TimeSpan is greater than another specified TimeSpan. 
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return true if the value of t1 is greater than the value of t2; otherwise, false.
	 */
	public static boolean greaterThan(TimeSpan t1, TimeSpan t2){
		 return (t1._ticks > t2._ticks);
	}

	/**
	 * Indicates whether a specified TimeSpan is greater than or equal to another specified TimeSpan. 
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return true if the value of t1 is greater than or equal to the value of t2; otherwise, false.
	 */
	public static boolean greaterThanOrEqual(TimeSpan t1, TimeSpan t2){
		return (t1._ticks >= t2._ticks);
	}

	/**
	 * Indicates whether two TimeSpan instances are not equal.
	 * @param t1 A TimeSpan. 
	 * @param t2 A TimeSpan. 
	 * @return true if the values of t1 and t2 are not equal; otherwise, false.
	 */
	public static boolean inequality(TimeSpan t1, TimeSpan t2){
		 return (t1._ticks != t2._ticks);
	}

	/**
	 * Indicates whether a specified TimeSpan is less than another specified TimeSpan.
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return true if the value of t1 is less than the value of t2; otherwise, false.
	 */
	public static boolean lessThan(TimeSpan t1, TimeSpan t2){
		return (t1._ticks < t2._ticks);
	}

	/**
	 * Indicates whether a specified TimeSpan is less than or equal to another specified TimeSpan.
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return true if the value of t1 is less than or equal to the value of t2; otherwise, false.
	 */
	public static boolean lessThanOrEqual(TimeSpan t1, TimeSpan t2){
		return (t1._ticks <= t2._ticks);
	}

	/**
	 * Subtracts a specified TimeSpan from another specified TimeSpan.
	 * @param t1 A TimeSpan.
	 * @param t2 A TimeSpan.
	 * @return A TimeSpan whose value is the result of the value of t1 minus the value of t2. 
	 * @throws OverflowException The return value is less than MinValue or greater than MaxValue. 
	 */
	public static TimeSpan subtraction(TimeSpan t1, TimeSpan t2) throws OverflowException{
		return t1.subtract(t2);
	}

	/**
	 * Returns a TimeSpan whose value is the negated value of the specified instance.
	 * @param t A TimeSpan.
	 * @return A TimeSpan with the same numeric value as this instance, but the opposite sign. 
	 * @throws IOException 
	 * @throws OverflowException The negated value of this instance cannot be represented by a TimeSpan; that is, the value of this instance is MinValue. 
	 * @throws InvalidPropertiesFormatException 
	 */
	public static TimeSpan unaryNegation(TimeSpan t) throws InvalidPropertiesFormatException, OverflowException, IOException{
	    if (t._ticks == MinValue._ticks)
	    {
	        throw new OverflowException(Resources.getOverflow_NegateTwosCompNum());
	    }
	    return new TimeSpan(-t._ticks);

	}

	/**
	 * Returns the specified instance of TimeSpan.
	 * @param t A TimeSpan.
	 * @return Returns t.
	 */
	public static TimeSpan unaryPlus(TimeSpan t){
	    return t;
	}

	/**
	 * Constructs a new TimeSpan object from a time interval specified in a string.
	 * @param s A string that specifies a time interval.
	 * @return A TimeSpan that corresponds to s. 
	 * @throws ArgumentNullException s is null. 
	 * @throws OverflowException s represents a number less than MinValue or greater than MaxValue. -or- At least one of the days, hours, minutes, or seconds components is outside its valid range.
	 * @throws IOException 
	 * @throws FormatException s has an invalid format. 
	 * @throws InvalidPropertiesFormatException s has an invalid format.
	 */
	public static TimeSpan parse(String s) throws InvalidPropertiesFormatException, FormatException, IOException, OverflowException, ArgumentNullException{
	    TimeSpan tx = new TimeSpan();
		StringParser parser2 = tx.new StringParser();
	    return new TimeSpan(parser2.parse(s));
	}

	/**
	 * Gets the seconds component of the time interval represented by the current TimeSpan structure. 
	 * @return The second component of the current TimeSpan structure. The return value ranges from -59 through 59.
	 */
	public int getSeconds(){
		return (int) ((this._ticks / 0x989680L) % 60L);
	}

	/**
	 * Subtracts the specified TimeSpan from this instance.
	 * @param ts A TimeSpan.
	 * @return A TimeSpan whose value is the result of the value of this instance minus the value of ts.
	 * @throws OverflowException The return value is less than MinValue or greater than MaxValue. 
	 */
	public TimeSpan subtract(TimeSpan ts) throws OverflowException{
		    long ticks = this._ticks - ts._ticks;
		    if (((this._ticks >> 0x3f) != (ts._ticks >> 0x3f)) && ((this._ticks >> 0x3f) != (ticks >> 0x3f)))
		    {
		        throw new OverflowException(Resources.getOverflow_TimeSpanTooLong());
		    }
		    return new TimeSpan(ticks);
	}

	public long getTicks(){
		return this._ticks;
	}

	/**
	 * Returns the total number of ticks according to the argument time values supplied.
	 * @param hour The number of hours.
	 * @param minute The number of hours.
	 * @param second The number of hours.
	 * @throws OverflowException The return value is less than MinValue or greater than MaxValue.
	 */
	static long timeToTicks(int hour, int minute, int second) throws OverflowException{
	    long num = ((hour * 0xe10L) + (minute * 60L)) + second;
	    if ((num > MaxSeconds) || (num < MinSeconds))
	    {
	        throw new OverflowException(Resources.getOverflow_TimeSpanTooLong());
	    }
	    return (num * 0x989680L);
	}

	/**
	 * Returns the string representation of the value of this instance.
	 * @return A string that represents the value of this instance. The return value is of the form: [-][d.]hh:mm:ss[.fffffff] Elements 
	 * in square brackets ([ and ]) may not be included in the returned string. Colons and periods (: and.) are literal characters. The non-literal 
	 * elements are listed in the following table. Item Description "-" A minus sign, which indicates a negative time span. No sign is included for a 
	 * positive time span. "d" The number of days in the time span. This element is omitted if the time span is less than one day. "hh" The number of 
	 * hours in the time span, ranging from 0 to 23. "mm" The number of minutes in the time span, ranging from 0 to 59. "ss" The number of seconds in 
	 * the time span, ranging from 0 to 59. "fffffff" Fractional seconds in the time span. This element is omitted if the time span does not include 
	 * fractional seconds. If present, fractional seconds are always expressed using 7 decimal digits.
	 */
	public String toString(){
	    StringBuffer builder = new StringBuffer();
	    int num = (int) (this._ticks / 0xc92a69c000L);
	    long num2 = this._ticks % 0xc92a69c000L;
	    if (this._ticks < 0L)
	    {
	        builder.append("-");
	        num = -num;
	        num2 = -num2;
	    }
	    if (num != 0)
	    {
	        builder.append(num);
	        builder.append(".");
	    }
	    builder.append(this.intToString((int) ((num2 / 0x861c46800L) % 0x18L)));
	    builder.append(":");
	    builder.append(this.intToString((int) ((num2 / 0x23c34600L) % 60L)));
	    builder.append(":");
	    builder.append(this.intToString((int) ((num2 / 0x989680L) % 60L)));
	    int n = (int) (num2 % 0x989680L);
	    if (n != 0)
	    {
	        builder.append(".");
	        builder.append(this.intToString(n));
	    }
	    return builder.toString();
	}

	/**
	 * Gets the value of the current TimeSpan structure expressed in whole and fractional days. 
	 * @return The total number of days represented by this instance.
	 */
	public double getTotalDays(){
		return (this._ticks * DaysPerTick);
	}

	/**
	 * Gets the value of the current TimeSpan structure expressed in whole and fractional hours. 
	 * @return The total number of hours represented by this instance.
	 */
	public double getTotalHours(){
		return (this._ticks * 2.7777777777777777E-11);
	}

	/**
	 * Gets the value of the current TimeSpan structure expressed in whole and fractional milliseconds.
	 * @return The total number of milliseconds represented by this instance.
	 */
	public double getTotalMilliseconds(){
        double num = this._ticks * 0.0001;
        if (num > MaxMilliSeconds)
        {
            return MaxMilliSeconds;
        }
        if (num < MinMilliSeconds)
        {
            return MinMilliSeconds;
        }
        return num;
	}

	/**
	 * Gets the value of the current TimeSpan structure expressed in whole and fractional minutes.
	 * @return The total number of minutes represented by this instance. 
	 */
	public double getTotalMinutes(){
		return (this._ticks * MinutesPerTick);
	}

	/**
	 * Gets the value of the current TimeSpan structure expressed in whole and fractional seconds.
	 * @return The total number of seconds represented by this instance.
	 */
	public double getTotalSeconds(){
		return (this._ticks * SecondsPerTick);
	}

	/**
	 * Constructs a new TimeSpan object from a time interval specified in a string. Parameters specify the time interval and the variable 
	 * where the new TimeSpan object is returned. 
	 * @param s A string that specifies a time interval.
	 * @param result When this method returns, contains an object that represents the time interval specified by s, or Zero if the conversion 
	 * failed. This parameter is passed uninitialized.
	 * @return true if s was converted successfully; otherwise, false. This operation returns false if the s parameter is null, has an invalid 
	 * format, represents a time interval less than MinValue or greater than MaxValue, or has at least one days, hours, minutes, or seconds 
	 * component outside its valid range.
	 */
	public static boolean tryParse(String s, TimeSpan result){
		    long num = 0;
		    TimeSpan tx = new TimeSpan();
		    StringParser parser2 = tx.new StringParser();
		    if (parser2.tryParse(s, num))
		    {
		        result = new TimeSpan(num);
		        return true;
		    }
		    result = Zero;
		    return false;
	}

	 class StringParser
	 {
	     private String str;
	     private char ch;
	     private int pos;
	     private int len;
	     private ParseError error;
	     
	     StringParser(){

	     }
	     
	     void NextChar()
	     {
	         if (this.pos < this.len)
	         {
	             this.pos++;
	         }
	         this.ch = (this.pos < this.len) ? this.str.charAt(this.pos) : '\0';
	     }

	     char NextNonDigit()
	     {
	         for (int i = this.pos; i < this.len; i++)
	         {
	             char ch = this.str.charAt(i);
	             if ((ch < '0') || (ch > '9'))
	             {
	                 return ch;
	             }
	         }
	         return '\0';
	     }

	     long parse(String s) throws InvalidPropertiesFormatException, FormatException, IOException, OverflowException, ArgumentNullException
	     {
	         long num = 0;
	         if (this.tryParse(s, num))
	         {
	             return num;
	         }
	         switch (this.error)
	         {
	             case FORMAT:
	                 throw new FormatException(Resources.getFormat_InvalidString());

	             case OVERFLOW:
	                 throw new OverflowException(Resources.getOverflow_TimeSpanTooLong());

	             case OVERFLOW_HOURS_MINUTES_SECONDS:
	                 throw new OverflowException(Resources.getOverflow_TimeSpanElementTooLarge());

	             case ARGUMENT_NULL:
	                 throw new ArgumentNullException("s");
	         }
	         return 0L;
	     }

	      boolean tryParse(String s, long value)
	     {
	         long num = 0;
	         value = 0L;
	         if (s == null)
	         {
	             this.error = ParseError.ARGUMENT_NULL;
	             return false;
	         }
	         this.str = s;
	         this.len = s.length();
	         this.pos = -1;
	         this.NextChar();
	         this.SkipBlanks();
	         boolean flag = false;
	         if (this.ch == '-')
	         {
	             flag = true;
	             this.NextChar();
	         }
	         if (this.NextNonDigit() == ':')
	         {
	             if (!this.ParseTime(num))
	             {
	                 return false;
	             }
	         }
	         else
	         {
	             int num2 = 0;
	             if (!this.ParseInt(0xa2e3ff, num2))
	             {
	                 return false;
	             }
	             num = num2 * 0xc92a69c000L;
	             if (this.ch == '.')
	             {
	                 long num3 = 0;
	                 this.NextChar();
	                 if (!this.ParseTime(num3))
	                 {
	                     return false;
	                 }
	                 num += num3;
	             }
	         }
	         if (flag)
	         {
	             num = -num;
	             if (num > 0L)
	             {
	                 this.error = ParseError.OVERFLOW;
	                 return false;
	             }
	         }
	         else if (num < 0L)
	         {
	             this.error = ParseError.OVERFLOW;
	             return false;
	         }
	         this.SkipBlanks();
	         if (this.pos < this.len)
	         {
	             this.error = ParseError.FORMAT;
	             return false;
	         }
	         value = num;
	         return true;
	     }

	     boolean ParseInt(int max, Integer i)
	     {
	         i = 0;
	         int pos = this.pos;
	         while ((this.ch >= '0') && (this.ch <= '9'))
	         {
	             if ((((long) i) & 0xf0000000L) != 0L)
	             {
	                 this.error = ParseError.OVERFLOW;
	                 return false;
	             }
	             i = ((i * 10) + this.ch) - 0x30;
	             if (i < 0)
	             {
	                 this.error = ParseError.OVERFLOW;
	                 return false;
	             }
	             this.NextChar();
	         }
	         if (pos == this.pos)
	         {
	             this.error = ParseError.FORMAT;
	             return false;
	         }
	         if (i > max)
	         {
	             this.error = ParseError.OVERFLOW;
	             return false;
	         }
	         return true;
	     }

	     boolean ParseTime(long time)
	     {
	         int num=0;
	         time = 0L;
	         if (!this.ParseInt(0x17, num))
	         {
	             if (this.error == ParseError.OVERFLOW)
	             {
	                 this.error = ParseError.OVERFLOW_HOURS_MINUTES_SECONDS;
	             }
	             return false;
	         }
	         time = num * 0x861c46800L;
	         if (this.ch != ':')
	         {
	             this.error = ParseError.FORMAT;
	             return false;
	         }
	         this.NextChar();
	         if (!this.ParseInt(0x3b, num))
	         {
	             if (this.error == ParseError.OVERFLOW)
	             {
	                 this.error = ParseError.OVERFLOW_HOURS_MINUTES_SECONDS;
	             }
	             return false;
	         }
	         time += num * 0x23c34600L;
	         if (this.ch == ':')
	         {
	             this.NextChar();
	             if (this.ch != '.')
	             {
	                 if (!this.ParseInt(0x3b, num))
	                 {
	                     if (this.error == ParseError.OVERFLOW)
	                     {
	                         this.error = ParseError.OVERFLOW_HOURS_MINUTES_SECONDS;
	                     }
	                     return false;
	                 }
	                 time += num * 0x989680L;
	             }
	             if (this.ch == '.')
	             {
	                 this.NextChar();
	                 int num2 = 0x989680;
	                 while (((num2 > 1) && (this.ch >= '0')) && (this.ch <= '9'))
	                 {
	                     num2 /= 10;
	                     time += (this.ch - '0') * num2;
	                     this.NextChar();
	                 }
	             }
	         }
	         return true;
	     }

	     void SkipBlanks()
	     {
	         while ((this.ch == ' ') || (this.ch == '\t'))
	         {
	             this.NextChar();
	         }
	     }

	 }
}