package Csla.Web.Design;

/**
 * Object providing access to schema information for a business object.
 * 
 *        @remark This object returns only one view, which corresponds to the
 * business object used by data binding.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:39 PM
 */
public class ObjectSchema extends IDataSourceSchema {

	private CslaDataSourceDesigner _designer;
	private string _typeName = "";

	public ObjectSchema(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param designer    Data source designer object.
	 * @param typeName    Type name for which the schema should be generated.
	 */
	public ObjectSchema(CslaDataSourceDesigner designer, string typeName){
		_typeName = typeName;
		          _designer = designer;
	}

	/**
	 * Returns a single element array containing the schema for the CSLA .NET business
	 * object.
	 */
	public System.Web.UI.Design.IDataSourceViewSchema[] GetViews(){
		IDataSourceViewSchema[] result = null;
		          result = new IDataSourceViewSchema[] { new ObjectViewSchema(_designer, _typeName) };
		          return result;
	}

}