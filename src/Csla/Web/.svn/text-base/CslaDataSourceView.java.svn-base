package Csla.Web;

/**
 * The object responsible for managing data binding to a specific CSLA .NET object.
 * 
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:31 PM
 */
public class CslaDataSourceView extends DataSourceView {

	private CslaDataSource _owner;
	private string _typeAssemblyName;
	private string _typeName;
	private bool _typeSupportsPaging;
	private bool _typeSupportsSorting;

	public CslaDataSourceView(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param owner    The CslaDataSource object that owns this view.
	 * @param viewName    The name of the view.
	 */
	public CslaDataSourceView(CslaDataSource owner, string viewName){
		_owner = owner;
	}

	/**
	 * Gets a value indicating whether the data source can delete data.
	 */
	public bool CanDelete(){
		get
		              {
		                if (typeof(Csla.Core.IUndoableObject).IsAssignableFrom(
		                  CslaDataSource.GetType(_typeAssemblyName, _typeName)))
		                  return true;
		                else
		                  return false;
		              }
	}

	/**
	 * Gets a value indicating whether the data source can insert data.
	 */
	public bool CanInsert(){
		get
		              {
		                if (typeof(Csla.Core.IUndoableObject).IsAssignableFrom(
		                  CslaDataSource.GetType(_typeAssemblyName, _typeName)))
		                  return true;
		                else
		                  return false;
		              }
	}

	/**
	 * Gets a value indicating whether the data source supports paging of the data.
	 */
	public bool CanPage(){
		get 
		              {
		                return _typeSupportsPaging;
		              }
	}

	/**
	 * Gets a value indicating whether the data source can retrieve the total number
	 * of rows of data. Always returns <see langword="true"/>.
	 */
	public bool CanRetrieveTotalRowCount(){
		get { return true; }
	}

	/**
	 * Gets a alue indicating whether the data source supports sorting of the data.
	 * Always returns <see langword="false"/>.
	 */
	public bool CanSort(){
		get 
		              {
		                return _typeSupportsSorting;
		              }
	}

	/**
	 * Gets a value indicating whether the data source can update data.
	 */
	public bool CanUpdate(){
		get
		              {
		                if (typeof(Csla.Core.IUndoableObject).IsAssignableFrom(
		                  CslaDataSource.GetType(_typeAssemblyName, _typeName)))
		                  return true;
		                else
		                  return false;
		              }
	}

	/**
	 * Implements the delete behavior for the control by raising the
	 *        <see cref="CslaDataSource.DeleteObject"/> event.
	 * 
	 *        @returns The number of rows affected.
	 * 
	 * @param keys    The key values from the UI that are to be deleted.
	 * @param oldValues    The old values from the UI.
	 */
	protected int ExecuteDelete(IDictionary keys, IDictionary oldValues){
		// tell the page to delete the object
		        DeleteObjectArgs args = new DeleteObjectArgs(keys, oldValues);
		        _owner.OnDeleteObject(args);
		        return args.RowsAffected;
	}

	/**
	 * Implements the insert behavior for the control by raising the
	 *        <see cref="CslaDataSource.InsertObject"/> event.
	 * 
	 *        @returns The number of rows affected.
	 * 
	 * @param values    The values from the UI that are to be inserted.
	 */
	protected int ExecuteInsert(IDictionary values){
		// tell the page to insert the object
		        InsertObjectArgs args = 
		          new InsertObjectArgs(values);
		        _owner.OnInsertObject(args);
		        return args.RowsAffected;
	}

	/**
	 * Implements the select behavior for the control by raising the
	 *        <see cref="CslaDataSource.SelectObject"/> event.
	 * 
	 *        @returns The data returned from the select.
	 * 
	 * @param arguments    Arguments object.
	 */
	protected System.Collections.IEnumerable ExecuteSelect(DataSourceSelectArguments arguments){
		// get the object from the page
		        SelectObjectArgs args = new SelectObjectArgs(arguments);
		        _owner.OnSelectObject(args);
		        object result = args.BusinessObject;
		        
		        if (arguments.RetrieveTotalRowCount)
		        {
		          int rowCount;
		          if (result == null)
		            rowCount = 0;
		          else if (result is Csla.Core.IReportTotalRowCount)
		            rowCount = ((Csla.Core.IReportTotalRowCount)result).TotalRowCount;
		          else if (result is IList)
		            rowCount = ((IList)result).Count;
		          else if (result is IEnumerable)
		          {
		            IEnumerable temp = (IEnumerable)result;
		            int count = 0;
		            foreach (object item in temp)
		              count++;
		            rowCount = count;
		          }
		          else
		            rowCount = 1;
		          arguments.TotalRowCount = rowCount;
		        }
		        
		        // if the result isn't IEnumerable then
		        // wrap it in a collection
		        if (!(result is IEnumerable))
		        {
		          ArrayList list = new ArrayList();
		          if (result != null)
		            list.Add(result);
		          result = list;
		        }
		        
		        // now return the object as a result
		        return (IEnumerable)result;
	}

	/**
	 * Implements the update behavior for the control by raising the
	 *        <see cref="CslaDataSource.UpdateObject"/> event.
	 * 
	 *        @returns The number of rows affected.
	 * 
	 * @param keys    The key values from the UI that identify the object to be
	 * updated.
	 * @param values    The values from the UI that are to be inserted.
	 * @param oldValues    The old values from the UI.
	 */
	protected int ExecuteUpdate(IDictionary keys, IDictionary values, IDictionary oldValues){
		// tell the page to update the object
		        UpdateObjectArgs args = new UpdateObjectArgs(keys, values, oldValues);
		        _owner.OnUpdateObject(args);
		        return args.RowsAffected;
	}

	/**
	 * Get or set the name of the assembly (no longer used).
	 * 
	 *        @value Obsolete - do not use.
	 */
	public string TypeAssemblyName(){
		get { return _typeAssemblyName; }
		        set { _typeAssemblyName = value; }
	}

	/**
	 * Get or set the full type name of the business object class to be used as a data
	 * source.
	 * 
	 *        @value Full type name of the business class.
	 */
	public string TypeName(){
		get { return _typeName; }
		        set { _typeName = value; }
	}

	/**
	 * Get or set a value indicating whether the business object data source supports
	 * paging.
	 * 
	 *        @remark To support paging, the business object (collection) must
	 * implement
	 *        <see cref="Csla.Core.IReportTotalRowCount"/>.
	 */
	public bool TypeSupportsPaging(){
		get { return _typeSupportsPaging; }
		        set { _typeSupportsPaging = value; }
	}

	/**
	 * Get or set a value indicating whether the business object data source supports
	 * sorting.
	 */
	public bool TypeSupportsSorting(){
		get { return _typeSupportsSorting; }
		        set { _typeSupportsSorting = value; }
	}

}