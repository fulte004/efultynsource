package Csla.Data;

import java.sql.ResultSet;
/**
 * This is a DataReader that 'fixes' any null values before they are returned to
 * our business code.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:43 PM
 */
public class SafeDataReader implements ResultSet {

	private ResultSet _dataReader;
	/**
	 * To detect redundant calls
	 */
	private boolean _disposedValue;

	/**
	 * Initializes the SafeDataReader Object to use data from the provided DataReader
	 * Object.
	 * 
	 * @param dataReader    The source DataReader Object containing the data.
	 */
	public SafeDataReader(ResultSet dataReader){
		_dataReader = dataReader;
	}

	/**
	 * Closes the datareader.
	 */
	public void close(){
		_dataReader.Close();
	}

	/**
	 * Get a reference to the underlying data reader Object that actually contains the
	 * data from the data source.
	 */
	protected ResultSet getDataReader(){
		{ return _dataReader; }
	}

	/**
	 * Returns the depth property value from the datareader.
	 */
	public int getDepth(){

		{
			return _dataReader.Depth;
		}
	}

	/**
	 * Returns the FieldCount property from the datareader.
	 */
	public int getFieldCount(){
		return _dataReader.FieldCount;
	}

	/**
	 * Gets a boolean value from the datareader.
	 * 
	 *        @remark Returns <see langword="false" /> for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public boolean getBoolean(String name){
		return GetBoolean(_dataReader.GetOrdinal(name));
	}

	/**
	 * Gets a boolean value from the datareader.
	 * 
	 *        @remark Returns <see langword="false" /> for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public boolean getBoolean(int i){
		if (_dataReader.IsDBNull(i))
			return false;
		else
			return _dataReader.GetBoolean(i);
	}

	/**
	 * Gets a byte value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public byte getByte(String name){
		return getByte(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a byte value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public byte getByte(int i){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getByte(i);
	}

	/**
	 * Invokes the getBytes method of the underlying datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 * @param fieldOffset    Offset position within the field.
	 * @param buffer    Array containing the data.
	 * @param bufferOffset    Offset position within the buffer.
	 * @param length    Length of data to read.
	 */
	public Int64 getBytes(String name, Int64 fieldOffset, byte[] buffer, int bufferOffset, int length){
		return getBytes(_dataReader.getOrdinal(name), fieldOffset, buffer, bufferOffset, length);
	}

	/**
	 * Invokes the getBytes method of the underlying datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 * @param fieldOffset    Offset position within the field.
	 * @param buffer    Array containing the data.
	 * @param bufferOffset    Offset position within the buffer.
	 * @param length    Length of data to read.
	 */
	public Int64 getBytes(int i, Int64 fieldOffset, byte[] buffer, int bufferOffset, int length){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getBytes(i, fieldOffset, buffer, bufferOffset, length);
	}

	/**
	 * Gets a char value from the datareader.
	 * 
	 *        @remark Returns Char.MinValue for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public char getChar(String name){
		return getChar(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a char value from the datareader.
	 * 
	 *        @remark Returns Char.MinValue for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public char getChar(int i){
		if (_dataReader.IsDBNull(i))
			return char.MinValue;
		else
		{
			char[] myChar = new char[1];
			_dataReader.getChars(i, 0, myChar, 0, 1);
			return myChar[0];
		}
	}

	/**
	 * Invokes the getChars method of the underlying datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 * @param fieldOffset    Offset position within the field.
	 * @param buffer    Array containing the data.
	 * @param bufferOffset    Offset position within the buffer.
	 * @param length    Length of data to read.
	 */
	public Int64 getChars(String name, Int64 fieldOffset, char[] buffer, int bufferOffset, int length){
		return getChars(_dataReader.getOrdinal(name), fieldOffset, buffer, bufferOffset, length);
	}

	/**
	 * Invokes the getChars method of the underlying datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 * @param fieldOffset    Offset position within the field.
	 * @param buffer    Array containing the data.
	 * @param bufferOffset    Offset position within the buffer.
	 * @param length    Length of data to read.
	 */
	public Int64 getChars(int i, Int64 fieldOffset, char[] buffer, int bufferOffset, int length){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getChars(i, fieldOffset, buffer, bufferOffset, length);
	}

	/**
	 * Invokes the getData method of the underlying datareader.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public ResultSet getData(String name){
		return getData(_dataReader.getOrdinal(name));
	}

	/**
	 * Invokes the getData method of the underlying datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public ResultSet getData(int i){
		return _dataReader.getData(i);
	}

	/**
	 * Invokes the getDataTypeName method of the underlying datareader.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public String getDataTypeName(String name){
		return getDataTypeName(_dataReader.getOrdinal(name));
	}

	/**
	 * Invokes the getDataTypeName method of the underlying datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public String getDataTypeName(int i){
		return _dataReader.getDataTypeName(i);
	}

	/**
	 * gets a date value from the datareader.
	 * 
	 *        @remark Returns Calendar.MinValue for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public Calendar getDateTime(String name){
		return getDateTime(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a date value from the datareader.
	 * 
	 *        @remark Returns Calendar.MinValue for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public Calendar getDateTime(int i){
		if (_dataReader.IsDBNull(i))
			return Calendar.MinValue;
		else
			return _dataReader.getDateTime(i);
	}

	/**
	 * Gets a double from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public double getDouble(String name){
		return getDouble(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a double from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public double getDouble(int i){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getDouble(i);
	}

	/**
	 * Invokes the GetFieldType method of the underlying datareader.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public Class<?> getFieldType(String name){
		return getFieldType(_dataReader.getOrdinal(name));
	}

	/**
	 * Invokes the getFieldType method of the underlying datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public Class<?> getFieldType(int i){
		return _dataReader.getFieldType(i);
	}

	/**
	 * Gets a Single value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public float getFloat(String name){
		return getFloat(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a Single value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public float getFloat(int i){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getFloat(i);
	}

	/**
	 * Gets a Guid value from the datareader.
	 * 
	 *        @remark Returns Guid.Empty for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public System.Guid getGuid(String name){
		return getGuid(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a Guid value from the datareader.
	 * 
	 *        @remark Returns Guid.Empty for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public System.Guid getGuid(int i){
		if (_dataReader.IsDBNull(i))
			return Guid.Empty;
		else
			return _dataReader.getGuid(i);
	}

	/**
	 * Gets a Short value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public short getShort(String name){
		return getShort(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a Short value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public short getShort(int i){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getShort(i);
	}

	/**
	 * Gets an integer from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public int getInteger(String name){
		return getInteger(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets an integer from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public int getInteger(int i){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getInteger(i);
	}

	/**
	 * Gets a Long value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public Long getLong(String name){
		return getLong(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a Long value from the datareader.
	 * 
	 *        @remark Returns 0 for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public Long getLong(int i){
		if (_dataReader.IsDBNull(i))
			return 0;
		else
			return _dataReader.getLong(i);
	}

	/**
	 * Invokes the getName method of the underlying datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public String getName(int i){
		return _dataReader.getName(i);
	}

	/**
	 * Gets an ordinal value from the datareader.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public int getOrdinal(String name){
		return _dataReader.getOrdinal(name);
	}

	/**
	 * Invokes the getSchemaTable method of the underlying datareader.
	 */
	public DataTable getSchemaTable(){
		return _dataReader.getSchemaTable();
	}

	/**
	 * Gets a <see cref="SmartDate" /> from the datareader.
	 * 
	 *        @remark A null is converted into min possible date See Chapter 5 for
	 * more details on the SmartDate class.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public Csla.SmartDate getSmartDate(String name){
		return getSmartDate(_dataReader.getOrdinal(name), true);
	}

	/**
	 * Gets a <see cref="SmartDate" /> from the datareader.
	 * 
	 *        @remark A null is converted into the min possible date See Chapter 5 for
	 * more details on the SmartDate class.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public Csla.SmartDate getSmartDate(int i){
		return getSmartDate(i, true);
	}

	/**
	 * Gets a <see cref="SmartDate" /> from the datareader.
	 * 
	 *        @remark A null is converted into either the min or max possible date
	 * depending on the MinIsEmpty parameter. See Chapter 5 for more details on the
	 * SmartDate class.
	 * 
	 * @param name    Name of the column containing the value.
	 * @param minIsEmpty    A flag indicating whether the min or max value of a data
	 * means an empty date.
	 */
	public Csla.SmartDate getSmartDate(String name, boolean minIsEmpty){
		return getSmartDate(_dataReader.getOrdinal(name), minIsEmpty);
	}

	/**
	 * Gets a <see cref="SmartDate"/> from the datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 * @param minIsEmpty    A flag indicating whether the min or max value of a data
	 * means an empty date.
	 */
	public Csla.SmartDate getSmartDate(int i, boolean minIsEmpty){
		if (_dataReader.IsDBNull(i))
			return new Csla.SmartDate(minIsEmpty);
		else
			return new Csla.SmartDate(
					_dataReader.getDateTime(i), minIsEmpty);
	}

	/**
	 * Gets a String value from the datareader.
	 * 
	 *        @remark Returns empty String for null.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public String getString(String name){
		return getString(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a String value from the datareader.
	 * 
	 *        @remark Returns empty String for null.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public String getString(int i){
		if (_dataReader.IsDBNull(i))
			return String.Empty;
		else
			return _dataReader.getString(i);
	}

	/**
	 * Gets a value of type <see cref="System.Object" /> from the datareader.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public Object getValue(String name){
		return getValue(_dataReader.getOrdinal(name));
	}

	/**
	 * Gets a value of type <see cref="System.Object" /> from the datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public Object getValue(int i){
		if (_dataReader.IsDBNull(i))
			return null;
		else
			return _dataReader.getValue(i);
	}

	/**
	 * Invokes the getValues method of the underlying datareader.
	 * 
	 * @param values    An array of System.Object to copy the values into.
	 */
	public int getValues(Object[] values){
		return _dataReader.getValues(values);
	}

	/**
	 * Returns the IsClosed property value from the datareader.
	 */
	public boolean isClosed(){
		return _dataReader.IsClosed;
	}

	/**
	 * Invokes the IsDBNull method of the underlying datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public boolean isDBNull(int i){
		return _dataReader.IsDBNull(i);
	}

	/**
	 * Moves to the next result set in the datareader.
	 */
	public boolean nextResult(){
		return _dataReader.NextResult();
	}

	/**
	 * Reads the next row of data from the datareader.
	 */
	public boolean read(){
		return _dataReader.Read();
	}

	/**
	 * Returns the RecordsAffected property value from the underlying datareader.
	 */
	public int getRecordsAffected(){
		return _dataReader.RecordsAffected;
	}

	/**
	 * Returns a value from the datareader.
	 * 
	 * @param name    Name of the column containing the value.
	 */
	public Object get(String name){
		Object val = _dataReader[name];
		if (DBNull.Value.Equals(val))
			return null;
		else
			return val;
	}

	/**
	 * Returns a value from the datareader.
	 * 
	 * @param i    Ordinal column position of the value.
	 */
	public Object get(int i){
		if (_dataReader.IsDBNull(i))
			return null;
		else
			return _dataReader[i];
	}

}