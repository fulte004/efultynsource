package Csla;
import java.lang.reflect.Type;

import Csla.Core.BusinessObject;
import Csla.Core.ObjectCloner;
import Csla.Core.ReadOnlyBindingList;
import Csla.Properties.Resources;

/**
 * This is the base class from which readonly name/value collections should be
 * derived.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:38 PM
 */
public abstract class NameValueListBase<K, V> extends ReadOnlyBindingList<NameValueListBase<K, V>.NameValuePair> implements BusinessObject, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2366607519358385517L;

	/**
	 * Contains a key and value pair.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:38 PM
	 */
	public class NameValuePair {

		private K _key;
		private V _value;

		/**
		 * Creates an instance of the object.
		 * 
		 * @param key    The key.
		 * @param value    The value.
		 */
		public NameValuePair(K key, V value){
			_key = key;
			        _value = value;
		}

		/**
		 * The Key or Name value.
		 */
		public K getKey(){
			 return _key; 
		}

		/**
		 * Returns a string representation of the value for this item.
		 */
		public String toString(){
			return _value.toString();
		}

		/**
		 * The Value corresponding to the key/name.
		 */
		public V getValue(){
			 return _value; 
		}

	}

	/**
	 * Default Criteria for retrieving simple name/value lists.
	 * 
	 *      @remark This criteria merely specifies the type of collection to be
	 * retrieved. That type information is used by the DataPortal to create the
	 * correct type of collection object during data retrieval.
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:38 PM
	 */
	protected class Criteria extends CriteriaBase {

		/**
		 * Creates an instance of the object.
		 * 
		 * @param collectionType    The <see cref="Type"/> of the business collection
		 * class.
		 */
		public Criteria(Type collectionType){
			super(collectionType);
		}

	}



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 */
	protected NameValueListBase(){
		Initialize();
	}

	protected Object cloneObj(){
		return getClone();
	}

	/**
	 * Creates a clone of the Object.
	 */
	@SuppressWarnings("unchecked")
	public NameValueListBase<K, V> clone(){
		return (NameValueListBase<K, V>)getClone();
	}

	/**
	 * Gets a value indicating whether the list contains the specified key.
	 * 
	 * @param key    Key value for which to search.
	 */
	public boolean containsKey(K key){
		for (NameValuePair item : this)
		        if (item.getKey().equals(key))
		          return true;
		      return false;
	}

	/**
	 * Gets a value indicating whether the list contains the specified value.
	 * 
	 * @param value    Value for which to search.
	 */
	public boolean containsValue(V value){
		for (NameValuePair item : this)
		        if (item.getValue().equals(value))
		          return true;
		      return false;
	}

	/**
	 * 
	 * @param criteria
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_create(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getCreateNotSupportedException());
	}

	/**
	 * 
	 * @param criteria
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_delete(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to allow retrieval of an existing business Object based on
	 * data in the database.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_fetch(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getFetchNotSupportedException());
	}

	/**
	 * Called by the server-side DataPortal if an exception occurs during data access.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 * @param ex    The Exception thrown during data access.
	 */
	protected void DataPortal_onDataPortalException(DataPortalEvent e, Exception ex){

	}

	/**
	 * Called by the server-side DataPortal prior to calling the requested
	 * DataPortal_XYZ method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_onDataPortalInvoke(DataPortalEvent e){

	}

	/**
	 * Called by the server-side DataPortal after calling the requested DataPortal_XYZ
	 * method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_onDataPortalInvokeComplete(DataPortalEvent e){

	}

	protected void DataPortal_update() throws NotSupportedException{
		throw new NotSupportedException(Resources.getUpdateNotSupportedException());
	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *      @returns A new Object containing the exact data of the original Object.
	 */
	protected Object getClone(){
		return ObjectCloner.clone(this);
	}

	/**
	 * Get the item for the first matching key in the collection.
	 * 
	 *      @returns Item from the list.
	 * 
	 * @param key    Key to search for in the list.
	 */
	public NameValuePair getItemByKey(K key){
		for (NameValuePair item : this)
		      {
		        if (item != null && item.getKey().equals(key))
		        {
		          return item;
		        }
		      }
		      return null;
	}

	/**
	 * Get the item for the first matching value in the collection.
	 * 
	 *      @returns Item from the list.
	 * 
	 * @param value    Value to search for in the list.
	 */
	public NameValuePair getItemByValue(V value){
		for (NameValuePair item : this)
		      {
		        if (item != null && item.getValue().equals(value))
		        {
		          return item;
		        }
		      }
		      return null;
	}

	/**
	 * Override this method to set up event handlers so user code in a partial class
	 * can respond to events raised by generated code.
	 */
	protected void Initialize(){
		/* allows subclass to initialize events before any other activity occurs */
	}

	/**
	 * Returns the key corresponding to the first occurance of the specified value in
	 * the list.
	 * 
	 * @param value    Value for which to retrieve the key.
	 */
	public K Key(V value){
		for (NameValuePair item : this)
		        if (item.getValue().equals(value))
		          return item.getKey();
		      return null; //default(K);
	}

	/**
	 * Returns the value corresponding to the specified key.
	 * 
	 * @param key    Key value for which to retrieve a value.
	 */
	public V Value(K key){
		for (NameValuePair item : this)
		        if (item.getKey().equals(key))
		          return item.getValue();
		      return null; //default(V);
	}

}