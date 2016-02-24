package Csla;

/**
 * This exception is returned for any errors occuring during the server-side
 * DataPortal invocation.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:33 PM
 */
public class DataPortalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3137225468458946834L;
	private Object _businessObject;
	public DataPortalException(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the Object.
	 * 
	 * @param message    Text describing the exception.
	 * @param businessObject    The business Object as it was at the time of the
	 * exception.
	 */
	public DataPortalException(String message, Object businessObject){
		_businessObject = businessObject;
	}

	/**
	 * Creates an instance of the Object.
	 * 
	 * @param message    Text describing the exception.
	 * @param ex    Inner exception.
	 * @param businessObject    The business Object as it was at the time of the
	 * exception.
	 */
	public DataPortalException(String message, Exception ex, Object businessObject){
		ex.getStackTrace()[0].toString();
		_businessObject = businessObject;
	}

	/**
	 * Gets the original server-side exception.
	 * 
	 *      @returns An exception Object.
	 *      @remark When an exception occurs in business code behind the data portal,
	 * it is wrapped in a
	 *      <see cref="Csla.Server.DataPortalException"/>, which is then wrapped in a
	 *      <see cref="Csla.DataPortalException"/>. This property unwraps and returns
	 * the original exception thrown by the business code on the server.
	 */
	public Exception getBusinessException(){
		return (Exception) this.getCause();
	}

	/**
	 * Returns a reference to the business Object from the server-side DataPortal.
	 * 
	 *      @remark Remember that this Object may be in an invalid or undefined state.
	 * This is the business Object (and any child objects) as it existed when the
	 * exception occured on the server. Thus the Object state may have been altered by
	 * the server and may no longer reflect data in the database.
	 */
	public Object getBusinessObject(){
		return _businessObject; 
	}


	/**
	 * Get the combined stack trace from the server and client.
	 */
	public StackTraceElement[] getStackTrace(){
		return super.getStackTrace(); 
	}

}