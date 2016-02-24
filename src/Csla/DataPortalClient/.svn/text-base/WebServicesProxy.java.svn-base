package Csla.DataPortalClient;

import java.net.URISyntaxException;

import Csla.ApplicationContext;
import Csla.Server.DataPortalContext;
import Csla.Server.DataPortalResult;

/**
 * Implements a data portal proxy to relay data portal calls to a remote
 * application server by using Web services.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:51 PM
 */
public class WebServicesProxy implements IDataPortalProxy {

	public WebServicesProxy(){

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
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context){
		Object result;
		Csla.Server.Hosts.WebServicePortal.CreateRequest
		request = new Csla.Server.Hosts.WebServicePortal.CreateRequest();
		request.setObjectType(objectType);
		request.setCriteria(criteria);
		request.setContext(context);

		WebServiceHost.WebServicePortal wsvc = GetPortal();
		{
			byte[] rd = Serialize(request);
			byte[] rp = wsvc.Create(rd);
			result = Deserialize(rp);
		}

		if (result is Exception)
			throw (Exception)result;
		return (Server.DataPortalResult)result;
	}

	/**
	 * Called by <see cref="DataPortal" /> to delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context){
		Object result;
		Csla.Server.Hosts.WebServicePortal.DeleteRequest request = new Csla.Server.Hosts.WebServicePortal.DeleteRequest();
		request.setCriteria(criteria);
		request.setContext(context);

		WebServiceHost.WebServicePortal wsvc = GetPortal();
		{
			result = Deserialize(wsvc.Delete(Serialize(request)));
		}

		if (result.getClass().isInstance(Exception.class))
			throw (Exception)result;
		return (DataPortalResult)result;
	}

	/**
	 * 
	 * @param obj
	 */
	private static Object Deserialize(byte[] obj){
		if (obj != null)
		{
			using (MemoryStream buffer = new MemoryStream(obj))
			{
				BinaryFormatter formatter = new BinaryFormatter();
				return formatter.Deserialize(buffer);
			}
		}
		return null;
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
		Object result;
		Csla.Server.Hosts.WebServicePortal.FetchRequest request = 
			new Csla.Server.Hosts.WebServicePortal.FetchRequest();
		request.setObjectType(objectType);
		request.setCriteria(criteria);
		request.setContext(context);

		Csla.WebServiceHost.WebServicePortal wsvc = GetPortal();
		{
			result = Deserialize(wsvc.fetch(Serialize(request)));
		}

		if (result.getClass().isInstance(Exception.class))
			throw (Exception)result;
		return (DataPortalResult)result;
	}

	private Csla.WebServiceHost.WebServicePortal GetPortal(){
		Csla.WebServiceHost.WebServicePortal wsvc = 
			new Csla.WebServiceHost.WebServicePortal();
		try {
			wsvc.setUrl(ApplicationContext.getDataPortalUrl().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wsvc;
	}

	/**
	 * Get a value indicating whether this proxy will invCsla.oke a remote data portal
	 * server, or run the "server-side" data portal in the caller's process and
	 * AppDomain.
	 */
	public bool IsServerRemote(){
		get { return true; }
	}

	/**
	 * 
	 * @param obj
	 */
	private static byte[] Serialize(Object obj){
		if (obj != null)
		{
			using (MemoryStream buffer = new MemoryStream())
			{
				BinaryFormatter formatter = new BinaryFormatter();
				formatter.Serialize(buffer, obj);
				return buffer.ToArray();
			}
		}
		return null;
	}

	/**
	 * Called by <see cref="DataPortal" /> to update a business Object.
	 * 
	 * @param obj    The business Object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 */
	public Server.DataPortalResult Update(Object obj, DataPortalContext context){
		Object result;
		Csla.Server.Hosts.WebServicePortal.UpdateRequest request = new Csla.Server.Hosts.WebServicePortal.UpdateRequest();
		request.setObject(obj);
		request.setContext(context);

		Csla.Server.Hosts.WebServicePortal wsvc = GetPortal();
		{
			result = Deserialize(wsvc.Update(Serialize(request)));
		}

		if (result is Exception)
			throw (Exception)result;
		return (Server.DataPortalResult)result;
	}

}