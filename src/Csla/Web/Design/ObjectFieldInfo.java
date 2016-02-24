package Csla.Web.Design;

/**
 * Contains schema information for a single object property.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:39 PM
 */
public class ObjectFieldInfo extends IDataSourceFieldSchema {

	private Type _dataType;
	private bool _isIdentity;
	private bool _isNullable;
	private bool _isReadOnly;
	private int _length;
	private string _name;
	private bool _nullable;
	private bool _primaryKey;

	public ObjectFieldInfo(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param field    The PropertyInfo object describing the property.
	 */
	public ObjectFieldInfo(PropertyDescriptor field){
		DataObjectFieldAttribute attribute =
		            (DataObjectFieldAttribute)
		            field.Attributes[typeof(DataObjectFieldAttribute)];
		          if (attribute != null)
		          {
		            _primaryKey = attribute.PrimaryKey;
		            _isIdentity = attribute.IsIdentity;
		            _isNullable = attribute.IsNullable;
		            _length = attribute.Length;
		          }
		          _dataType = Utilities.GetPropertyType(
		              field.PropertyType);
		          _isReadOnly = field.IsReadOnly;
		          _name = field.Name;
		          
		          // nullable
		          Type t = field.PropertyType;
		          if (!t.IsValueType || _isNullable)
		            _nullable = true;
		          else
		          {
		            if (t.IsGenericType)
		              _nullable = (t.GetGenericTypeDefinition() == typeof(Nullable<>));
		            else
		              _nullable = false;
		          }
	}

	/**
	 * Gets the data type of the property.
	 */
	public Type DataType(){
		get
		                {
		                  return _dataType;
		                }
	}

	/**
	 * Gets a value indicating whether this property is an identity key for the object.
	 * 
	 * 
	 *          @remark Returns the optional value provided through the <see
	 * cref="DataObjectFieldAttribute">DataObjectField</see> attribute on the property.
	 */
	public bool Identity(){
		get { return _isIdentity; }
	}

	/**
	 * Gets a value indicating whether this property is readonly.
	 */
	public bool IsReadOnly(){
		get { return _isReadOnly; }
	}

	/**
	 * Gets a value indicating whether this property must contain a unique value.
	 * 
	 *          @returns Always returns True if the property is marked as a primary
	 * key, otherwise returns False.
	 */
	public bool IsUnique(){
		get { return _primaryKey; }
	}

	/**
	 * Gets the length of the property value.
	 * 
	 *          @remark Returns the optional value provided through the <see
	 * cref="DataObjectFieldAttribute">DataObjectField</see> attribute on the property.
	 */
	public int Length(){
		get { return _length; }
	}

	/**
	 * Gets the property name.
	 */
	public string Name(){
		get { return _name; }
	}

	/**
	 * Gets a value indicating whether the property is nullable
	 * 
	 *          @remark Returns True for reference types, and for value types wrapped
	 * in the Nullable generic. The result can also be set to True through the <see
	 * cref="DataObjectFieldAttribute">DataObjectField</see> attribute on the property.
	 */
	public bool Nullable(){
		get
		                { return _nullable; }
	}

	/**
	 * Gets the property's numeric precision.
	 * 
	 *          @returns Always returns -1.
	 */
	public int Precision(){
		get { return -1; }
	}

	/**
	 * Gets a value indicating whether the property is a primary key value.
	 * 
	 *          @remark Returns the optional value provided through the <see
	 * cref="DataObjectFieldAttribute">DataObjectField</see> attribute on the property.
	 */
	public bool PrimaryKey(){
		get { return _primaryKey; }
	}

	/**
	 * Gets the property's scale.
	 * 
	 *          @returns Always returns -1.
	 */
	public int Scale(){
		get { return -1; }
	}

}