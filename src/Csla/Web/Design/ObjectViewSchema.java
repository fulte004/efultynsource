package Csla.Web.Design;

/**
 * Object providing schema information for a business object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:40 PM
 */
public class ObjectViewSchema extends IDataSourceViewSchema {

	private CslaDataSourceDesigner _designer;
	private string _typeName = "";

	public ObjectViewSchema(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Create an instance of the object.
	 * 
	 * @param site    Site containing the control.
	 * @param typeName    The business class for which to generate the schema.
	 */
	public ObjectViewSchema(CslaDataSourceDesigner site, string typeName){
		_typeName = typeName;
		          _designer = site;
	}

	/**
	 * Returns a list of child schemas belonging to the object.
	 * 
	 *          @remark This schema object only returns schema for the object itself,
	 * so GetChildren will always return Nothing (null in C#).
	 */
	public System.Web.UI.Design.IDataSourceViewSchema[] GetChildren(){
		return null;
	}

	/**
	 * Returns schema information for each property on the object.
	 * 
	 *          @remark All public properties on the object will be reflected in this
	 * schema list except for those properties where the
	 *          <see cref="BrowsableAttribute">Browsable</see> attribute is False.
	 */
	public System.Web.UI.Design.IDataSourceFieldSchema[] GetFields(){
		ITypeResolutionService typeService = null;
		          List<ObjectFieldInfo> result = new List<ObjectFieldInfo>();
		          
		          if (_designer != null)
		          {
		            Type objectType = null;
		            try
		            {
		              typeService = (ITypeResolutionService)(_designer.Site.GetService(typeof(ITypeResolutionService)));
		              objectType = typeService.GetType(_typeName, true, false);
		          
		              if (typeof(IEnumerable).IsAssignableFrom(objectType))
		              {
		                // this is a list so get the item type
		                objectType = Utilities.GetChildItemType(objectType);
		              }
		              PropertyDescriptorCollection props = TypeDescriptor.GetProperties(objectType);
		              foreach (PropertyDescriptor item in props)
		              {
		                if (item.IsBrowsable)
		                {
		                  result.Add(new ObjectFieldInfo(item));
		                }
		              }
		            }
		            catch
		            { /* do nothing - just swallow exception */ }
		          }
		          
		          return result.ToArray();
	}

	/**
	 * Returns the name of the schema.
	 */
	public string Name(){
		get
		                {
		                  return "Default";
		                }
	}

}