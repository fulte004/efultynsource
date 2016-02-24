package Csla.Web.Design;

/**
 * Object responsible for providing details about data binding to a specific CSLA .
 * NET object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:31 PM
 */
public class CslaDesignerDataSourceView extends DesignerDataSourceView {

	private CslaDataSourceDesigner _owner = null;

	public CslaDesignerDataSourceView(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param owner
	 * @param viewName
	 */
	public CslaDesignerDataSourceView(CslaDataSourceDesigner owner, string viewName){
		_owner = owner;
	}

	/**
	 * Get a value indicating whether data binding can directly delete the object.
	 * 
	 *          @remark If this returns true, the web page must handle the
	 *          <see cref="CslaDataSource.DeleteObject">DeleteObject</see> event.
	 */
	public bool CanDelete(){
		get
		                {
		                  Type objectType = GetObjectType();
		                  if (typeof(Csla.Core.IUndoableObject).IsAssignableFrom(objectType))
		                  {
		                    return true;
		                  }
		                  else if (objectType.GetMethod("Remove") != null)
		                  {
		                    return true;
		                  }
		                  else
		                  {
		                    return false;
		                  }
		                }
	}

	/**
	 * Get a value indicating whether data binding can directly insert an instance of
	 * the object.
	 * 
	 *          @remark If this returns true, the web page must handle the
	 *          <see cref="CslaDataSource.InsertObject">InsertObject</see> event.
	 */
	public bool CanInsert(){
		get
		                {
		                  Type objectType = GetObjectType();
		                  if (typeof(Csla.Core.IUndoableObject).IsAssignableFrom(objectType))
		                  {
		                    return true;
		                  }
		                  else
		                  {
		                    return false;
		                  }
		                }
	}

	/**
	 * Gets a value indicating whether the data source supports paging.
	 */
	public bool CanPage(){
		get
		                {
		                  return _owner.DataSourceControl.TypeSupportsPaging;
		                }
	}

	/**
	 * Get a value indicating whether data binding can retrieve the total number of
	 * rows of data.
	 */
	public bool CanRetrieveTotalRowCount(){
		get
		                {
		                  return true;
		                }
	}

	/**
	 * Gets a value indicating whether the data source supports sorting.
	 */
	public bool CanSort(){
		get
		                {
		                  return _owner.DataSourceControl.TypeSupportsSorting;
		                }
	}

	/**
	 * Get a value indicating whether data binding can directly update or edit the
	 * object.
	 * 
	 *          @remark If this returns true, the web page must handle the
	 *          <see cref="CslaDataSource.UpdateObject">UpdateObject</see> event.
	 */
	public bool CanUpdate(){
		get
		                {
		                  Type objectType = GetObjectType();
		                  if (typeof(Csla.Core.IUndoableObject).IsAssignableFrom(objectType))
		                  {
		                    return true;
		                  }
		                  else
		                  {
		                    return false;
		                  }
		                }
	}

	/**
	 * Returns a set of sample data used to populate controls at design time.
	 * 
	 * @param minimumRows    Minimum number of sample rows to create.
	 * @param isSampleData    Returns True if the data is sample data.
	 */
	public IEnumerable GetDesignTimeData(int minimumRows, bool isSampleData){
		IDataSourceViewSchema schema = this.Schema;
		          DataTable result = new DataTable();
		          
		          // create the columns
		          foreach (IDataSourceFieldSchema item in schema.GetFields())
		          {
		            result.Columns.Add(item.Name, item.DataType);
		          }
		          
		          // create sample data
		          for (int index = 1; index <= minimumRows; index++)
		          {
		            object[] values = new object[result.Columns.Count];
		            int colIndex = 0;
		            foreach (DataColumn col in result.Columns)
		            {
		              if (col.DataType.Equals(typeof(string)))
		                values[colIndex] = "abc";
		              else if (col.DataType.Equals(typeof(System.DateTime)))
		                values[colIndex] = System.DateTime.Today.ToShortDateString();
		              else if (col.DataType.Equals(typeof(bool)))
		                values[colIndex] = false;
		              else if (col.DataType.IsPrimitive)
		                values[colIndex] = index;
		              else if (col.DataType.Equals(typeof(Guid)))
		                values[colIndex] = Guid.Empty;
		              else if (col.DataType.IsValueType)
		                values[colIndex] = Activator.CreateInstance(col.DataType);
		              else
		                values[colIndex] = null;
		              colIndex += 1;
		            }
		            result.LoadDataRow(values, LoadOption.OverwriteChanges);
		          }
		          
		          isSampleData = true;
		          return (IEnumerable)result.DefaultView;
	}

	private Type GetObjectType(){
		Type result;
		          try
		          {
		            ITypeResolutionService typeService = null;
		            typeService = (ITypeResolutionService)(_owner.Site.GetService(typeof(ITypeResolutionService)));
		            result = typeService.GetType(this._owner.DataSourceControl.TypeName, true, false);
		          }
		          catch
		          {
		            result = typeof(object);
		          }
		          return result;
	}

	/**
	 * Returns schema information corresponding to the properties of the CSLA .NET
	 * business object.
	 * 
	 *          @remark All public properties are returned except for those marked
	 * with the <see cref="BrowsableAttribute">Browsable attribute</see> as False.
	 */
	public IDataSourceViewSchema Schema(){
		get
		                {
		                  return new ObjectSchema(
		                    _owner, 
		                    _owner.DataSourceControl.TypeName).GetViews()[0];
		                }
	}

}