package Csla.DataPortalClient;
import java.lang.reflect.Type;

import Csla.Server.DataPortalException;
import Csla.Server.DataPortalResult;
import Csla.Server.DataPortalContext;
import Csla.Server.IDataPortalServer;

/**
 * Implements a data portal proxy to relay data portal calls to an application
 * server hosted locally in the client process and AppDomain.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:38 PM
 */
public class LocalProxy implements IDataPortalProxy {

	private IDataPortalServer _portal = new Csla.Server.DataPortal();

	/**
	 * Called by <see cref="DataPortal" /> to create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException{
		return _portal.create(objectType, criteria, context);
	}

	/**
	 * Called by <see cref="DataPortal" /> to delete a business object.
	 * 
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 * @throws Exception 
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context) throws Exception{
		return _portal.delete(criteria, context);
	}

	/**
	 * Called by <see cref="DataPortal" /> to load an existing business Object.
	 * 
	 * @param objectType    Type of business Object to retrieve.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws Exception 
	 */
	public DataPortalResult fetch(Class<?> objectType, Object criteria, DataPortalContext context) throws Exception{
		return _portal.fetch(objectType, criteria, context);
	}

	/**
	 * Get a value indicating whether this proxy will invoke a remote data portal
	 * server, or run the "server-side" data portal in the caller's process and
	 * AppDomain.
	 */
	public boolean isServerRemote(){
		 { return false; }
	}

	/**
	 * Called by <see cref="DataPortal" /> to update a business Object.
	 * 
	 * @param obj    The business Object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult update(Object obj, DataPortalContext context) throws DataPortalException{
		return _portal.update(obj, context);
	}

}