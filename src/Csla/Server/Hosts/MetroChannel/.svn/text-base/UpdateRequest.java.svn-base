package Csla.Server.Hosts.MetroChannel;
import Csla.Server.DataPortalContext;

/**
 * Request message for updating a business object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:48 PM
 */
public class UpdateRequest {

	private DataPortalContext _context;
	private Object _object;

	/**
	 * Create new instance of Object.
	 * 
	 * @param obj    Business Object to update.
	 * @param context    Data portal context from client.
	 */
	public UpdateRequest(Object obj, DataPortalContext context){
		_object = obj;
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
	 * Business Object to be updated.
	 */
	public Object getObject(){
		return _object; 
	}
	public void setObject(Object value){
		_object = value; 
	}
}