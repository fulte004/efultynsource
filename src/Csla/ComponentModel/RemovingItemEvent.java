package Csla.ComponentModel;

import java.util.EventObject;

/**
 * Contains event data for the RemovingItem event.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:41 PM
 */
public class RemovingItemEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object _removingItem;

	/**
	 * Create an instance of the Object.
	 * 
	 * @param removingItem    A reference to the item that was removed from the list.
	 */
	public RemovingItemEvent(Object removingItem){
		super(removingItem);
		_removingItem = removingItem;
	}

	/**
	 * Gets a reference to the item that was removed from the list.
	 */
	public Object getRemovingItem(){
		  return _removingItem; 
	}

}