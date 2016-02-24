package Csla.Web;

/**
 * Argument object used in the UpdateObject event.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:47 PM
 */
public class UpdateObjectArgs extends EventArgs {

	private System.Collections.IDictionary _keys;
	private System.Collections.IDictionary _oldValues;
	private int _rowsAffected;
	private System.Collections.IDictionary _values;

	public UpdateObjectArgs(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param keys
	 * @param values
	 * @param oldValues
	 */
	public UpdateObjectArgs(System.Collections.IDictionary keys, System.Collections.IDictionary values, System.Collections.IDictionary oldValues){
		_keys = keys;
		        _values = values;
		        _oldValues = oldValues;
	}

	/**
	 * The list of key values entered by the user.
	 * 
	 *        @remark It is up to the event handler in the web page to take the list
	 * of values, put them into a business object and to save that object into the
	 * database.
	 */
	public System.Collections.IDictionary Keys(){
		get { return _keys; }
	}

	/**
	 * The list of old data values maintained by data binding.
	 * 
	 *        @remark It is up to the event handler in the web page to take the list
	 * of values, put them into a business object and to save that object into the
	 * database.
	 */
	public System.Collections.IDictionary OldValues(){
		get { return _oldValues; }
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