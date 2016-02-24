package Csla.ComponentModel;

import java.util.EventObject;


/**
 * @author Eric
 * @version 1.0
 * @created 06-Jan-2010 9:47:59 PM
 */
public class AddingNewEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object _newObject;

	/**
	 * 
	 * @param newObject
	 */
	public AddingNewEvent(Object source, Object newObject){
		super(source);
		_newObject = newObject;
	}

	public Object getNewObject(){
		return _newObject;
	}
	public void setNewObject(Object value){
		_newObject = value;
	}
}