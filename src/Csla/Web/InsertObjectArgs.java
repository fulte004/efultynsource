package Csla.Web;

/**
 * Argument object used in the InsertObject event.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public class InsertObjectArgs extends EventArgs {

	private int _rowsAffected;
	private System.Collections.IDictionary _values;

	public InsertObjectArgs(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Create an instance of the object.
	 * 
	 * @param values
	 */
	public InsertObjectArgs(System.Collections.IDictionary values){
		_values = values;
	}

	/**
	 * Gets or sets the number of rows affected while handling this event.
	 * 
	 *        @value
	 *        @returns
	 *        @remark The code handling the event should set this value to indicate
	 * the number of rows affected by the operation.
	 */
	public int RowsAffected(){
		get { return _rowsAffected; }
		        set { _rowsAffected = value; }
	}

	/**
	 * The list of data values entered by the user.
	 * 
	 *        @remark It is up to the event handler in the web page to take the list
	 * of values, put them into a business object and to save that object into the
	 * database.
	 */
	public System.Collections.IDictionary Values(){
		get { return _values; }
	}

}