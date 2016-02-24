package Csla.DataPortalClient;

/**
 * Implements a data portal proxy to relay data portal calls to an application
 * server hosted in COM+.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:34 PM
 */
public abstract class EnterpriseServicesProxy implements IDataPortalProxy {

	public EnterpriseServicesProxy(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Called by <see cref="DataPortal" /> to create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public Server.DataPortalResult Create(Type objectType, object criteria, Server.DataPortalContext context){
		Server.Hosts.EnterpriseServicesPortal svc = GetServerObject();
		        try
		        {
		          return svc.Create(objectType, criteria, context);
		        }
		        finally
		        {
		          if (svc != null)
		            svc.Dispose();
		        }
	}

	/**
	 * Called by <see cref="DataPortal" /> to delete a business object.
	 * 
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public Server.DataPortalResult Delete(object criteria, Server.DataPortalContext context){
		Server.Hosts.EnterpriseServicesPortal svc = GetServerObject();
		        try
		        {
		          return svc.Delete(criteria, context);
		        }
		        finally
		        {
		          if (svc != null)
		            svc.Dispose();
		        }
	}

	/**
	 * Called by <see cref="DataPortal" /> to load an existing business object.
	 * 
	 * @param objectType    Type of business object to retrieve.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public Server.DataPortalResult Fetch(Type objectType, object criteria, Server.DataPortalContext context){
		Server.Hosts.EnterpriseServicesPortal svc = GetServerObject();
		        try
		        {
		          return svc.Fetch(objectType, criteria, context);
		        }
		        finally
		        {
		          if (svc != null)
		            svc.Dispose();
		        }
	}

	/**
	 * Override this method to return a reference to the server-side COM+
	 * (ServicedComponent) object implementing the data portal server functionality.
	 */
	protected abstract Server.Hosts.EnterpriseServicesPortal GetServerObject();

	/**
	 * Get a value indicating whether this proxy will invoke a remote data portal
	 * server, or run the "server-side" data portal in the caller's process and
	 * AppDomain.
	 */
	public bool IsServerRemote(){
		get { return true; }
	}

	/**
	 * Called by <see cref="DataPortal" /> to update a business object.
	 * 
	 * @param obj    The business object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public Server.DataPortalResult Update(object obj, Server.DataPortalContext context){
		Server.Hosts.EnterpriseServicesPortal svc = GetServerObject();
		        try
		        {
		          return svc.Update(obj, context);
		        }
		        finally
		        {
		          if (svc != null)
		            svc.Dispose();
		        }
	}

}