package Csla.Core;

/**
 * Exception indicating a problem with the use of the n-level undo feature in CSLA
 * .NET.
 * 
 *      @remark
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:47 PM
 */
public class UndoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UndoException(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param message    Text describing the exception.
	 */
	public UndoException(String message){
		super(message);
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param message    Text describing the exception.
	 * @param ex    Inner exception.
	 */
	public UndoException(String message, Exception ex){
		super(message, ex);
	}

	/**
	 * Creates an instance of the object for serialization.
	 * 
	 * @param info    Serialiation info object.
	 * @param context    Serialization context object.
	 */
	protected UndoException(Exception ex){
		super(ex);
	}

}