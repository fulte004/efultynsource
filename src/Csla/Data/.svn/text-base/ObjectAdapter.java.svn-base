package Csla.Data;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Csla.Utilities;
import Csla.Properties.Resources;

/**
 * An ObjectAdapter is used to convert data in an Object or collection into a
 * DataTable.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:39 PM
 */
public class ObjectAdapter {

	/**
	 * 
	 * @param dt
	 * @param ds
	 * @param columns
	 */
	private void CopyData(/*DataTable*/ ResultSet dt, List ds, ArrayList<String> columns){
		// load the data into the DataTable
		//dt.BeginLoadData();
		dt.moveToInsertRow();
		for (int index = 0; index < ds.size(); index++)
		{
			//DataRow dr = dt.NewRow();
			Array dr = dt.getArray(dt.getRow());
			for (String column : columns)
			{
				try
				{
					dr[column] = GetField(ds[index], column);
				}
				catch (Exception ex)
				{
					dr[column] = ex.Message;
				}
			}
			//dt.Rows.Add(dr);
			dt.updateArray(dt.getRow(), dr);
		}
		//dt.EndLoadData();
	}

	/**
	 * Fills the DataSet with data from an Object or collection.
	 * 
	 *        @remark The name of the DataTable being filled is will be the class name
	 * of the Object acting as the data source. The DataTable will be inserted if it
	 * doesn't already exist in the DataSet.
	 * 
	 * @param ds    A reference to the DataSet to be filled.
	 * @param source    A reference to the Object or collection acting as a data
	 * source.
	 */
	public void Fill(ResultSet ds, Object source){
		String className = source.getClass().getName();
		Fill(ds, className, source);
	}

	/**
	 * Fills the ResultSet with data from an Object or collection.
	 * 
	 *        @remark The name of the DataTable being filled is specified as a
	 * parameter. The DataTable will be inserted if it doesn't already exist in the
	 * ResultSet.
	 * 
	 * @param ds    A reference to the ResultSet to be filled.
	 * @param tableName
	 * @param source    A reference to the Object or collection acting as a data
	 * source.
	 */
	public void Fill(ResultSet ds, String tableName, Object source){
		ResultSet dt;
		boolean exists;

		dt = ds.Tables[tableName];
		exists = (dt != null);

		if (!exists)
			dt = new ResultSet(tableName);

		Fill(dt, source);

		if (!exists)
			ds.Tables.Add(dt);
	}

	/**
	 * Fills a ResultSet with data values from an Object or collection.
	 * 
	 * @param dt    A reference to the ResultSet to be filled.
	 * @param source    A reference to the Object or collection acting as a data
	 * source.
	 */
	public void Fill(ResultSet dt, Object source){
		if (source == null)
			throw new IllegalArgumentException(Resources.getNothingNotValid());

		// get the list of columns from the source
		ArrayList<String> columns = GetColumns(source);
		if (columns.size() < 1) return;

		// create columns in ResultSet if needed
		for (String column : columns)
			if (!dt.Columns.Contains(column))
				dt.Columns.Add(column);

		// get an List and copy the data
		CopyData(dt, GetIList(source), columns);
	}

	/**
	 * 
	 * @param source
	 */
	private ArrayList<String> GetColumns(Object source){
		ArrayList<String> result;
		// first handle ResultSet/ResultSet
		Object innerSource;
		IListSource iListSource = (IListSource)source;
		if (iListSource != null)
			innerSource = iListSource.GetList();
		else
			innerSource = source;

		DataView dataView = (DataView)innerSource;
		if (dataView != null)
			result = ScanDataView(dataView);
		else
		{
			// now handle lists/arrays/collections
			IEnumerable iEnumerable = (IEnumerable)innerSource;
			if (iEnumerable != null)
			{
				Type childType = Utilities.getChildItemType(
						innerSource.getClass());
				result = ScanObject(childType);
			}
			else
			{
				// the source is a regular Object
				result = ScanObject(innerSource.getClass());
			}
		}
		return result;
	}

	/**
	 * 
	 * @param obj
	 * @param fieldName
	 */
	private static String GetField(Object obj, String fieldName){
		String result;
		DataRowView dataRowView = obj as DataRowView;
		if (dataRowView != null)
		{
			// this is a DataRowView from a DataView
			result = dataRowView[fieldName].ToString();
		}
		else if (obj is ValueType && obj.GetType().IsPrimitive)
		{
			// this is a primitive value type
			result = obj.ToString();
		}
		else
		{
			String tmp = obj as String;
			if (tmp != null)
			{
				// this is a simple String
				result = (String)obj;
			}
			else
			{
				// this is an Object or Structure
				try
				{
					Type sourceType = obj.GetType();

					// see if the field is a property
					PropertyInfo prop = sourceType.GetProperty(fieldName);

					if ((prop == null) || (!prop.CanRead))
					{
						// no readable property of that name exists - 
						// check for a field
						FieldInfo field = sourceType.GetField(fieldName);
						if (field == null)
						{
							// no field exists either, throw an exception
							throw new DataException(
									Resources.NoSuchValueExistsException +
									" " + fieldName);
						}
						else
						{
							// got a field, return its value
							result = field.GetValue(obj).ToString();
						}
					}
					else
					{
						// found a property, return its value
						result = prop.GetValue(obj, null).ToString();
					}
				}
				catch (Exception ex)
				{
					throw new DataException(
							Resources.ErrorReadingValueException +
							" " + fieldName, ex);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param source
	 */
	private List GetIList(Object source){
		if (source is IListSource)
			return ((IListSource)source).GetList();
		else if (source instanceof List)
			return (List)source;
		else
		{
			// this is a regular Object - create a list
			ArrayList col = new ArrayList();
			col.Add(source);
			return col;
		}
	}

	/**
	 * 
	 * @param ds
	 */
	private ArrayList<String> ScanDataView(DataView ds){
		ArrayList<String> result = new ArrayList<String>();
		for (int field = 0; field < ds.Table.Columns.size(); field++)
			result.Add(ds.Table.Columns[field].ColumnName);
		return result;
	}

	/**
	 * 
	 * @param sourceType
	 */
	private ArrayList<String> ScanObject(Class<?> sourceType){
		ArrayList<String> result = new ArrayList<String>();

		if (sourceType != null)
		{
			// retrieve a list of all public properties
			PropertyInfo[] props = sourceType.GetProperties();
			if (props.Length >= 0)
				for (int column = 0; column < props.Length; column++)
					if (props[column].CanRead)
						result.Add(props[column].Name);

			// retrieve a list of all public fields
			FieldInfo[] fields = sourceType.GetFields();
			if (fields.Length >= 0)
				for (int column = 0; column < fields.Length; column++)
					result.Add(fields[column].Name);
		}
		return result;
	}

}