package Csla.DataPortalClient;
import java.rmi.Remote;

import Csla.Server.IDataPortalServer;

/**
 * Interface implemented by client-side data portal proxy objects.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:36 PM
 */
public interface IDataPortalProxy extends IDataPortalServer, Remote {

	/**
	 * Get a value indicating whether this proxy will invoke a remote data portal
	 * server, or run the "server-side" data portal in the caller's process and
	 * AppDomain.
	 */
	public boolean isServerRemote();

}