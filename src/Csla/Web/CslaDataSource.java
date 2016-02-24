package Csla.Web;

/**
 * A Web Forms data binding control designed to support CSLA .NET business objects
 * as data sources.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:30 PM
 */
public class CslaDataSource extends DataSourceControl {

	private CslaDataSourceView _defaultView;
	private static System.Collections.Generic.Dictionary<string,Type> _typeCache = new System.Collections.Generic.Dictionary<string,Type>();

	public CslaDataSource(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Event raised when an object is to be deleted.
	 * 
	 *        @remark Handle this event in a page to delete an object from the
	 * database.
	 */
	public EventHandler<DeleteObjectArgs> DeleteObject(){
		return null;
	}

	/**
	 * Returns a <see cref="Type">Type</see> object based on the assembly and type
	 * information provided.
	 * 
	 *        @remark
	 * 
	 * @param typeAssemblyName    Optional assembly name.
	 * @param typeName    Full type name of the class, including assembly name.
	 */
	internal static Type GetType(string typeAssemblyName, string typeName){
		Type result = null;
		        if (!string.IsNullOrEmpty(typeAssemblyName))
		        {
		          // explicit assembly name provided
		          result = Type.GetType(string.Format(
		            "{0}, {1}", typeName, typeAssemblyName), true, true);
		        }
		        else if (typeName.IndexOf(",") > 0)
		        {
		          // assembly qualified type name provided
		          result = Type.GetType(typeName, true, true);
		        }
		        else
		        {
		          // no assembly name provided
		          result = _typeCache[typeName];
		          if (result == null)
		            foreach (Assembly asm in AppDomain.CurrentDomain.GetAssemblies())
		            {
		              result = asm.GetType(typeName, false, true);
		              if (result != null)
		              {
		                _typeCache.Add(typeName, result);
		                break;
		              }
		            }
		        }
		        
		        if (result == null)
		          throw new TypeLoadException(String.Format(Resources.TypeLoadException, typeName));
		        
		        return result;
	}

	/**
	 * Returns the default view for this data control.
	 * 
	 *        @returns
	 *        @remark This control only contains a "Default" view.
	 * 
	 * @param viewName    Ignored.
	 */
	protected DataSourceView GetView(string viewName){
		if (_defaultView == null)
		          _defaultView = new CslaDataSourceView(this, "Default");
		        return _defaultView;
	}

	/**
	 * Returns a list of views available for this control.
	 * 
	 *        @remark This control only provides the "Default" view.
	 */
	protected System.Collections.ICollection GetViewNames(){
		return new string[] { "Default" };
	}

	/**
	 * Event raised when an object is to be populated with data and inserted.
	 * 
	 *        @remark Handle this event in a page to create an instance of the object,
	 * load the object with data and insert the object into the database.
	 */
	public EventHandler<InsertObjectArgs> InsertObject(){
		return null;
	}

	/**
	 * Raises the DeleteObject event.
	 * 
	 * @param e
	 */
	internal void OnDeleteObject(DeleteObjectArgs e){
		if (DeleteObject != null)
		          DeleteObject(this, e);
	}

	/**
	 * Raises the InsertObject event.
	 * 
	 * @param e
	 */
	internal void OnInsertObject(InsertObjectArgs e){
		if (InsertObject != null)
		          InsertObject(this, e);
	}

	/**
	 * Raises the SelectObject event.
	 * 
	 * @param e
	 */
	internal void OnSelectObject(SelectObjectArgs e){
		if (SelectObject != null)
		          SelectObject(this, e);
	}

	/**
	 * Raises the UpdateObject event.
	 * 
	 * @param e
	 */
	internal void OnUpdateObject(UpdateObjectArgs e){
		if (UpdateObject != null)
		          UpdateObject(this, e);
	}

	/**
	 * Event raised when an object is to be created and populated with data.
	 * 
	 *        @remark Handle this event in a page and set e.BusinessObject to the
	 * populated business object.
	 */
	public EventHandler<SelectObjectArgs> SelectObject(){
		return null;
	}

	/**
	 * Get or set the name of the assembly (no longer used).
	 * 
	 *        @value Obsolete - do not use.
	 */
	public string TypeAssemblyName(){
		get { return ((CslaDataSourceView)this.GetView("Default")).TypeAssemblyName; }
		        set { ((CslaDataSourceView)this.GetView("Default")).TypeAssemblyName = value; }
	}

	/**
	 * Get or set the full type name of the business object class to be used as a data
	 * source.
	 * 
	 *        @value Full type name of the business class, including assembly name.
	 */
	public string TypeName(){
		get { return ((CslaDataSourceView)this.GetView("Default")).TypeName; }
		        set { ((CslaDataSourceView)this.GetView("Default")).TypeName = value; }
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
		get { return ((CslaDataSourceView)this.GetView("Default")).TypeSupportsPaging; }
		        set { ((CslaDataSourceView)this.GetView("Default")).TypeSupportsPaging = value; }
	}

	/**
	 * Get or set a value indicating whether the business object data source supports
	 * sorting.
	 */
	public bool TypeSupportsSorting(){
		get { return ((CslaDataSourceView)this.GetView("Default")).TypeSupportsSorting; }
		        set { ((CslaDataSourceView)this.GetView("Default")).TypeSupportsSorting = value; }
	}

	/**
	 * Event raised when an object is to be updated.
	 * 
	 *        @remark Handle this event in a page to update an existing instance of an
	 * object with new data and then save the object into the database.
	 */
	public EventHandler<UpdateObjectArgs> UpdateObject(){
		return null;
	}

}