package Csla.Server;

import java.util.Map;

import Csla.ApplicationContext;

/**
 * Returns data from the server-side DataPortal to the client-side DataPortal.
 * Intended for internal CSLA .NET use only.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:33 PM
 */
public class DataPortalResult {

	private Map<String, Object> _globalContext;
	private Object _returnObject;

	/**
	 * Creates an instance of the Object.
	 */
	public DataPortalResult(){
		_globalContext = ApplicationContext.GetGlobalContext();
	}

	/**
	 * Creates an instance of the Object.
	 * 
	 * @param returnObject    Object to return as part of the result.
	 */
	public DataPortalResult(Object returnObject){
		_returnObject = returnObject;
		        _globalContext = ApplicationContext.GetGlobalContext();
	}

	/**
	 * The global context being returned from the server.
	 */
	public Map<String, Object> getGlobalContext(){
		  return _globalContext; 
	}

	/**
	 * The business Object being returned from the server.
	 */
	public Object getReturnObject(){
		  return _returnObject; 
	}

}