package Csla.Server.Hosts.MetroChannel;
import Csla.Server.DataPortalContext;

/**
 * Request message for retrieving an existing business object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:35 PM
 */
public class FetchRequest {

	private DataPortalContext _context;
	private Object _criteria;
	private Class<?> _objectType;


	/**
	 * Create new instance of Object.
	 * 
	 * @param objectType    Type of business Object to create.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    Data portal context from client.
	 */
	public FetchRequest(Class<?> objectType, Object criteria, DataPortalContext context){
		_objectType = objectType;
		_criteria = criteria;
		_context = context;
	}

	/**
	 * Data portal context from client.
	 */
	public DataPortalContext getContext(){
		return _context; 
	}
	public void setContext(DataPortalContext value){
		_context = value; 
	}

	/**
	 * Criteria Object describing business Object.
	 */
	public Object getCriteria(){
		return _criteria; 
	}
	public void setCriteria(Object value){
		_criteria = value; 
	}

	/**
	 * The type of the business Object to be retrieved.
	 */
	public Class<?> getObjectType(){
		return _objectType; 
	}
	public void setObjectType(Class<?> value){
		_objectType = value; 
	}
}