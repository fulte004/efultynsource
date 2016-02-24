package Csla;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
//import javax.security.auth.*;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Provides consistent context information between the client and server
 * DataPortal objects.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:23 PM
 */
public class ApplicationContext {

	/**
	 * Enum representing the serialization formatters supported by CSLA .NET.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:23 PM
	 */
	public enum SerializationFormatters {
		/**
		 * Use the standard Microsoft .NET
		 *        <see cref="BinaryFormatter"/>.
		 * 
		 *        
		 */
		BINARY_FORMATTER,
		/**
		 * Use the Microsoft .NET 3.0
		 *        <see cref="System.Runtime.Serialization.NetDataContractSerializer">
		 * NetDataContractSerializer</see> provided as part of WCF.
		 * 
		 *        
		 */
		NET_DATA_CONTRACT_SERIALIZER
	}

	/**
	 * Enum representing the locations code can execute.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:24 PM
	 */
	public enum ExecutionLocations {
		/**
		 * The code is executing on the client.
		 * 
		 *        
		 */
		CLIENT,
		/**
		 * The code is executing on the application server.
		 * 
		 *        
		 */
		SERVER
	}

	private static final ThreadLocal<Map<String, Object>> _clientContext = new ThreadLocal<Map<String, Object>>() {
        @Override protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
	};
	
	private static final ThreadLocal<Map<String, Object>> _globalContext = new ThreadLocal<Map<String, Object>>() {
        @Override protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
	};
	private static final ThreadLocal<Map<String, Object>> _localContext = new ThreadLocal<Map<String, Object>>() {
        @Override protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
	};
	
	private static ExecutionLocations _executionLocation = ExecutionLocations.CLIENT;
	private static Object _syncClientContext = new Object();

	/**
	 * Returns the authentication type being used by the CSLA .NET framework.
	 * 
	 *      @value
	 *      @returns
	 *      @remark This value is read from the application configuration file with
	 * the key value "CslaAuthentication". The value "Windows" indicates CSLA .NET
	 * should use Windows integrated (or AD) security. Any other value indicates the
	 * use of custom security derived from BusinessPrincipalBase.
	 */
	public static String getAuthenticationType(){
		return Configuration.getSetting("CslaAuthentication");
	}

	/**
	 * Gets a value indicating whether objects should be automatically cloned by the
	 * data portal Update() method when using a local data portal configuration.
	 */
	public static boolean getAutoCloneOnUpdate(){
		boolean result = false;
		String setting = Configuration.getSetting("CslaAutoCloneOnUpdate");

		if (!setting.isEmpty())
			result = Boolean.parseBoolean(setting);
		return result;

	}

	/**
	 * Clears all context collections.
	 */
	public static void clear(){
		setContext(null, null);
		setLocalContext(null);
	}

	/**
	 * Returns the application-specific context data provided by the client.
	 * 
	 *      @remark  The return value is a HybridDictionary. If one does not already
	 * exist, and empty one is created and returned.
	 *       Note that data in this context is transferred from the client to the
	 * server. No data is transferred from the server to the client.
	 *       This property is thread safe in a Windows client setting and on an
	 * application server. It is not guaranteed to be thread safe within the context
	 * of an ASP.NET client setting (i.e. in your ASP.NET UI).
	 */
	public static Map<String, Object> getClientContext(){


		synchronized (_syncClientContext)
		{
			Map<String, Object> ctx = GetClientContext();
			if (ctx == null)
			{
				ctx = new HashMap<String, Object>();
				setClientContext(ctx);
			}
			return ctx;
		}

	}

	/**
	 * Returns the channel or network protocol for the DataPortal server.
	 * 
	 *      @value Fully qualified assembly/type name of the proxy class.
	 *      @returns
	 *      @remark  This value is read from the application configuration file with
	 * the key value "CslaDataPortalProxy".
	 *       The proxy class must implement Csla.Server.IDataPortalServer.
	 *       The value "Local" is a shortcut to running the DataPortal "server" in the
	 * client process.
	 *       Other built-in values include:
	 *      <list>
	 *      <item>
	 *      <term>Csla,Csla.DataPortalClient.RemotingProxy</term>
	 *      <description>Use .NET Remoting to communicate with the
	 * server</description>
	 *      </item>
	 *      <item>
	 *      <term>Csla,Csla.DataPortalClient.EnterpriseServicesProxy</term>
	 *      <description>Use Enterprise Services (DCOM) to communicate with the
	 * server</description>
	 *      </item>
	 *      <item>
	 *      <term>Csla,Csla.DataPortalClient.WebServicesProxy</term>
	 *      <description>Use Web Services (asmx) to communicate with the
	 * server</description>
	 *      </item>
	 *      </list> Each proxy type does require that the DataPortal server be hosted
	 * using the appropriate technology. For instance, Web Services and Remoting
	 * should be hosted in IIS, while Enterprise Services must be hosted in COM+.
	 */
	public static String getDataPortalProxy(ServletContext cxt){
		String result = cxt.getAttribute("CslaDataPortalProxy").toString();
		if (result.isEmpty())
			result = "Local";
		return result;
	}

	/**
	 * Returns the URL for the DataPortal server.
	 * @throws URISyntaxException 
	 * 
	 *      @value
	 *      @returns
	 *      @remark This value is read from the application configuration file with
	 * the key value "CslaDataPortalUrl".
	 */
	public static URI getDataPortalUrl() throws URISyntaxException{
		 return new URI(Configuration.getSetting("CslaDataPortalUrl")); 
	}

	/**
	 * Returns a value indicating whether the application code is currently executing
	 * on the client or server.
	 */
	public static ExecutionLocations getExecutionLocation(){
		  return _executionLocation; 
	}

	public static Map<String, Object> GetClientContext(){
//		if (hsc.Current == null)
//		{
			if (ApplicationContext.getExecutionLocation() == ExecutionLocations.CLIENT)
			synchronized (_syncClientContext)
			{
//				return (Map<String, Object>)AppDomain.CurrentDomain.GetData(_clientContextName);
				return _clientContext.get();
			}
			else
			{

//				LocalDataStoreSlot slot = 
//					Thread.GetNamedDataSlot(_clientContextName);
//				return (Map<String, Object>)Thread.GetData(slot);
				return _clientContext.get();
			}
//		}
//		else
//			return (Map<String, Object>)HttpContext.Current.Items[_clientContextName];
	}

	public static Map<String, Object> GetGlobalContext(){
//		if (HttpContext.Current == null)
//		{
//			LocalDataStoreSlot slot = Thread.GetNamedDataSlot(_globalContextName);
//			return (Map<String, Object>)Thread.GetData(slot);
			return _globalContext.get();
//		}
//		else
//			return (Map<String, Object>)HttpContext.Current.Items[_globalContextName];
	}

	private static Map<String, Object> GetLocalContext(){
//		if (HttpContext.Current == null)
//		{
//			LocalDataStoreSlot slot = Thread.GetNamedDataSlot(_localContextName);
//			return (Map<String, Object>)Thread.GetData(slot);
			return _localContext.get();
//		}
//		else
//			return (Map<String, Object>)HttpContext.Current.Items[_localContextName];
	}

	/**
	 * Returns the application-specific context data shared on both client and server.
	 * 
	 * 
	 *      @remark  The return value is a Map<String, Object>. If one does not already
	 * exist, and empty one is created and returned.
	 *       Note that data in this context is transferred to and from the client and
	 * server. Any objects or data in this context will be transferred bi-
	 * directionally across the network.
	 */
	public static Map<String, Object> getGlobalContext(){


		Map<String, Object> ctx = GetGlobalContext();
		if (ctx == null)
		{
			ctx = new HashMap<String, Object>();
			setGlobalContext(ctx);
		}
		return ctx;

	}

	/**
	 * Returns the application-specific context data that is local to the current
	 * AppDomain.
	 * 
	 *      @remark  The return value is a Map<String, Object>. If one does not already
	 * exist, and empty one is created and returned.
	 *       Note that data in this context is NOT transferred to and from the client
	 * and server.
	 */
	public static Map<String, Object> getLocalContext(){

			Map<String, Object> ctx = GetLocalContext();
			if (ctx == null)
			{
				ctx = new HashMap<String, Object>();
				setLocalContext(ctx);
			}
			return ctx;
	}

	public static SerializationFormatters getSerializationFormatter() throws InvalidPropertiesFormatException, IOException{

		String tmp = Configuration.getSetting("CslaSerializationFormatter");
		if (tmp.isEmpty())
			tmp = "BinaryFormatter";
//		return (SerializationFormatters)Enum.Parse(SerializationFormatters.class, tmp);
		return SerializationFormatters.valueOf(tmp);
	}

	/**
	 * 
	 * @param clientContext
	 */
	private static void setClientContext(Map<String, Object> clientContext){
//		if (HttpContext.Current == null)
//		{
			if (ApplicationContext.getExecutionLocation() == ExecutionLocations.CLIENT)
				synchronized (_syncClientContext)
				{
//				AppDomain.CurrentDomain.SetData(_clientContextName, clientContext);
					_clientContext.set(clientContext);
				}
			else
			{
//				LocalDataStoreSlot slot = Thread.GetNamedDataSlot(_clientContextName);
//				Thread.SetData(slot, clientContext);
				_clientContext.set(clientContext);
			}
//		}
//		else
//			HttpContext.Current.Items[_clientContextName] = clientContext;
	}

	/**
	 * 
	 * @param clientContext
	 * @param globalContext
	 */
	public static void setContext(Map<String, Object> clientContext, Map<String, Object> globalContext){
		setClientContext(clientContext);
		setGlobalContext(globalContext);
	}

	/**
	 * 
	 * @param location
	 */
	public static void setExecutionLocation(ExecutionLocations location){
		_executionLocation = location;
	}

	/**
	 * 
	 * @param globalContext
	 */
	protected static void setGlobalContext(Map<String, Object> globalContext){
//		if (HttpContext.Current == null)
//		{
//			LocalDataStoreSlot slot = Thread.GetNamedDataSlot(_globalContextName);
//			Thread.SetData(slot, globalContext);
			_globalContext.set(globalContext);
//		}
//		else
//			HttpContext.Current.Items[_globalContextName] = globalContext;
	}

	/**
	 * 
	 * @param localContext
	 */
	private static void setLocalContext(Map<String, Object> localContext){
//		if (HttpContext.Current == null)
//		{
//			LocalDataStoreSlot slot = Thread.GetNamedDataSlot(_localContextName);
//			Thread.SetData(slot, localContext);
			_localContext.set(localContext);
//		}
//		else
//			HttpContext.Current.Items[_localContextName] = localContext;
	}

	/**
	 * Get or set the current <see cref="Principal" /> Object representing the user's
	 * identity.
	 * 
	 *      @remark This is discussed in Chapter 5. When running under IIS the
	 * HttpContext.Current.User value is used, otherwise the current Thread.
	 * CurrentPrincipal value is used.
	 */
	public static Principal getUser(HttpServletRequest httpContext ){
		return httpContext.getUserPrincipal();
//			if (httpContext.Current == null)
//				return Thread.CurrentPrincipal;
//			else
//				return HttpContext.Current.User;
	}
	
	// The Java Servlet container controls security.  Setting of user principals is not allowed.
	public static void setUser(Principal value){
		//TODO Add code to setUser
//			if (HttpContext.Current != null)
//				HttpContext.Current.User = value;
//			Thread.CurrentPrincipal = value;
	}
}