package Csla.Web;

/**
 * Argument object used in the DeleteObject event.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:33 PM
 */
public class DeleteObjectArgs extends EventArgs {

	private System.Collections.IDictionary _keys;
	private System.Collections.IDictionary _oldValues;
	private int _rowsAffected;

	public DeleteObjectArgs(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Create an instance of the object.
	 * 
	 * @param keys
	 * @param oldValues
	 */
	public DeleteObjectArgs(System.Collections.IDictionary keys, System.Collections.IDictionary oldValues){
		_keys = keys;
		        _oldValues = oldValues;
	}

	/**
	 * The list of key values entered by the user.
	 * 
	 *        @remark It is up to the event handler in the web page to use the values
	 * to identify the object to be deleted.
	 */
	public System.Collections.IDictionary Keys(){
		get { return _keys; }
	}

	/**
	 * The list of old data values maintained by data binding.
	 * 
	 *        @remark It is up to the event handler in the web page to use the values
	 * to identify the object to be deleted.
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

}