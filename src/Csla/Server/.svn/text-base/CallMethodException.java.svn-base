package Csla.Server;

/**
 * This exception is returned from the CallMethod method in the server-side
 * DataPortal and contains the exception thrown by the underlying business object
 * method that was being invoked.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:27 PM
 */
public class CallMethodException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private String _innerStackTrace;

	public CallMethodException(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param message    Message text describing the exception.
	 * @param ex    Inner exception object.
	 */
	public CallMethodException(String message, Exception ex){
		super(message, ex);
//		_innerStackTrace = ex.fillInStackTrace().toString();
	}


	/**
	 * Get the stack trace from the original exception.
	 * 
	 *        @value
	 *        @returns
	 *        @remark 
	 */
	@Override
	public StackTraceElement[] getStackTrace(){
		return super.getStackTrace();

	}

}