package Csla.Server;

import java.security.Principal;
import java.util.Map;

import Csla.Properties.Resources;

/**
 * Provides consistent context information between the client and server
 * DataPortal objects.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:32 PM
 */
public class DataPortalContext {

	private Map<String, Object> _clientContext;
	private String _clientCulture;
	private String _clientUICulture;
	private Map<String, Object> _globalContext;
	private Principal _principal;
	private boolean _remotePortal;

	/**
	 * Creates a new DataPortalContext object.
	 * 
	 * @param principal    The current Principal object.
	 * @param b    Indicates whether the DataPortal is remote.
	 */
	public DataPortalContext(Principal principal, boolean b){
		if (b)
		        {
		          _principal = principal;
		          _remotePortal = b;
		          _clientCulture = 
		            Resources.getLocale().getLanguage();
		          _clientUICulture = 
		            Resources.getLocale().getCountry();
		          _clientContext = Csla.ApplicationContext.GetClientContext();
		          _globalContext = Csla.ApplicationContext.GetGlobalContext();
		        }
	}

	Map<String, Object> getClientContext(){
		  return _clientContext; 
	}

	/**
	 * The culture setting on the client workstation.
	 */
	public String getClientCulture(){
		  return _clientCulture; 
	}

	/**
	 * The culture setting on the client workstation.
	 */
	public String getClientUICulture(){
		  return _clientUICulture; 
	}

	Map<String, Object> getGlobalContext(){
		  return _globalContext; 
	}

	/**
	 * Returns <see langword="true" /> if the server-side DataPortal is running on a
	 * remote server via remoting.
	 */
	public boolean isRemotePortal(){
		  return _remotePortal; 
	}

	/**
	 * The current principal object if CSLA security is being used.
	 */
	public Principal getPrincipal(){
		  return _principal; 
	}

}