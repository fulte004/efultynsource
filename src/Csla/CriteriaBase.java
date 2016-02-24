package Csla;

import java.lang.reflect.Type;

/**
 * Base type from which Criteria classes can be derived in a business class.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:30 PM
 */
public abstract class CriteriaBase {

	private Type _objectType;

	/**
	 * Initializes CriteriaBase with the type of business object to be created by the
	 * DataPortal.
	 * 
	 * @param type    The type of the business object the data portal should create.
	 */
	protected CriteriaBase(Type type){
		_objectType = type;
	}

	/**
	 * Type of the business object to be instantiated by the server-side DataPortal.
	 */
	public Type getObjectType(){
		  return _objectType; 
	}

}