package Csla.Server.Hosts.MetroChannel;
import Csla.Server.DataPortalContext;

/**
 * Request message for deleting a business object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:34 PM
 */
public class DeleteRequest {

	private DataPortalContext _context;
	private Object _criteria;


	/**
	 * Create new instance of Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    Data portal context from client.
	 */
	public DeleteRequest(Object criteria, DataPortalContext context){
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

}