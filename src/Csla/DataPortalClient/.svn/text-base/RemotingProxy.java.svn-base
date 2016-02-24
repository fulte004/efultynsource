package Csla.DataPortalClient;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.InvalidPropertiesFormatException;
import Csla.ApplicationContext;
import Csla.Configuration;
import Csla.Server.DataPortalException;
import Csla.Server.IDataPortalServer;
import Csla.Server.Hosts.RemotingPortal;

/**
 * Implements a data portal proxy to relay data portal calls to a remote
 * application server by using the .NET Remoting technology.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:41 PM
 */
public class RemotingProxy implements IDataPortalProxy {

    private IDataPortalServer _portal;
	/**
	 * Configure .NET Remoting to use a binary serialization technology even when
	 * using the HTTP channel. Also ensures that the user's Windows credentials are
	 * passed to the server appropriately.
	 */
	private RemotingProxy(){
		// create and register a custom HTTP channel
		// that uses the binary formatter
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put("name", "HttpBinary");

		if (ApplicationContext.getAuthenticationType() == "Windows" || getAlwaysImpersonate())
		{
			// make sure we pass the user's Windows credentials
			// to the server
			properties.put("useDefaultCredentials", true);
		}

		
//		BinaryClientFormatterSinkProvider 
//		formatter = new BinaryClientFormatterSinkProvider();
//		HttpChannel channel = new HttpChannel(properties, formatter, null);
//		ChannelServices.RegisterChannel(channel, EncryptChannel);
	}

	protected static boolean getAlwaysImpersonate() {
		boolean result = false;
		result = (Configuration.getSetting("CslaAlwaysImpersonate") == "true");
		return result;
	}

	/**
	 * Called by <see cref="DataPortal" /> to create a new business Object.
	 * 
	 * @param objectType    Type of business Object to create.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public Csla.Server.DataPortalResult create(Class<?> objectType, Object criteria, Csla.Server.DataPortalContext context) throws DataPortalException{
		return getPortal().create(objectType, criteria, context);
	}

	/**
	 * Called by <see cref="DataPortal" /> to delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws Exception 
	 */
	public Csla.Server.DataPortalResult delete(Object criteria, Csla.Server.DataPortalContext context) throws Exception{
		return getPortal().delete(criteria, context);
	}

	protected static boolean getEncryptChannel() throws InvalidPropertiesFormatException, IOException{
		boolean encrypt = 
			(Configuration.getSetting("CslaEncryptRemoting") == "true");
		return encrypt; 
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
	public Csla.Server.DataPortalResult fetch(Class<?> objectType, Object criteria, Csla.Server.DataPortalContext context) throws Exception{
		return getPortal().fetch(objectType, criteria, context);
	}

	/**
	 * Get a value indicating whether this proxy will invoke a remote data portal
	 * server, or run the "server-side" data portal in the caller's process and
	 * AppDomain.
	 */
	public boolean isServerRemote(){
		 return true; 
	}

	private Csla.Server.IDataPortalServer getPortal() {
		if (_portal == null){
			Class<Csla.Server.Hosts.RemotingPortal> tmp = Csla.Server.Hosts.RemotingPortal.class;
			Constructor<RemotingPortal> cn = null;
			try {
				cn = tmp.getConstructor(String.class);
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}
			try {
				_portal = cn.newInstance(ApplicationContext.getDataPortalUrl().toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return _portal;
	}

	/**
	 * Called by <see cref="DataPortal" /> to update a business Object.
	 * 
	 * @param obj    The business Object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public Csla.Server.DataPortalResult update(Object obj, Csla.Server.DataPortalContext context) throws DataPortalException{
		return getPortal().update(obj, context);
	}

}