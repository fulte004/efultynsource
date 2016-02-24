package Csla.Core;

import java.util.EventObject;

/**
 * Event arguments containing a reference to the new object that was returned as a
 * result of the Save() operation.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:44 PM
 */
public class SavedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object _newObject;

	/**
	 * Creates an instance of the Object.
	 * 
	 * @param newObject    The Object that was returned as a result of the Save()
	 * operation.
	 */
	public SavedEvent(Object newObject){
		super(newObject);
		_newObject = newObject;
	}

	/**
	 * Gets the Object that was returned as a result of the Save() operation.
	 */
	public Object getNewObject(){
		  return _newObject; 
	}

}