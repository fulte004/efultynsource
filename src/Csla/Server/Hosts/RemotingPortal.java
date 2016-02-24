package Csla.Server.Hosts;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Csla.Server.DataPortalException;
import Csla.Server.DataPortalResult;
import Csla.Server.DataPortalContext;
import Csla.Server.IDataPortalServer;

/**
 * Exposes server-side DataPortal functionality through .NET Remoting.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:41 PM
 */
public class RemotingPortal extends UnicastRemoteObject implements IDataPortalServer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6299865348356240550L;

	protected RemotingPortal() throws RemoteException {
		super();
	}

	/**
	 * Create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException{
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		          return portal.create(objectType, criteria, context);
	}

	/**
	 * Delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Csla.Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws Exception 
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context) throws Exception{
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		          return portal.delete(criteria, context);
	}

	/**
	 * Get an existing business Object.
	 * 
	 * @param objectType    Type of business Object to retrieve.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Csla.Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult fetch(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException{
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		          return portal.fetch(objectType, criteria, context);
	}

	/**
	 * Update a business Object.
	 * 
	 * @param obj    Business Object to update.
	 * @param context    <see cref="Csla.Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult update(Object obj, DataPortalContext context) throws DataPortalException{
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		          return portal.update(obj, context);
	}

}