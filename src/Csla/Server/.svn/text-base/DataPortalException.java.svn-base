package Csla.Server;

/**
 * This exception is returned from the server-side DataPortal and contains the
 * exception and context data from the server.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:32 PM
 */
public class DataPortalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _innerStackTrace;
	private DataPortalResult _result;

	public DataPortalException(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param message    Text describing the exception.
	 * @param ex    Inner exception.
	 * @param result    The data portal result object.
	 */
	public DataPortalException(String message, Exception ex, DataPortalResult result){
		_innerStackTrace = ex.getStackTrace().toString();
		        _result = result;
	}

	/**
	 * Returns the DataPortalResult object from the server.
	 */
	public DataPortalResult getResult(){
		  return _result; 
	}

	/**
	 * Get the server-side stack trace from the original exception.
	 */
	public StackTraceElement[] getStackTrace(){
		  return super.getStackTrace(); 
	}

}