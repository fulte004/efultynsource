package Csla;
import Csla.Server.DataPortalContext;

/**
 * Provides information about the DataPortal call.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:32 PM
 */
public class DataPortalEvent extends EventArgs {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataPortalContext _dataPortalContext;

	/**
	 * Creates an instance of the object.
	 * 
	 * @param dataPortalContext    Data portal context object.
	 */
	public DataPortalEvent(DataPortalContext dataPortalContext){
		_dataPortalContext = dataPortalContext;
	}

	/**
	 * The DataPortalContext object passed to the server-side DataPortal.
	 */
	public DataPortalContext getDataPortalContext(){
		  return _dataPortalContext; 
	}

}