package Csla.ComponentModel;

import java.util.EventListener;

public interface BindingListListener extends EventListener {
	
	/**
	 * 
	 * @param e
	 */
	public void onAddingNew(AddingNewEvent e);
	
	/**
	 * 
	 * @param e
	 */
	public void onListChanged(ListChangedEvent e);
	
	/**
	 * 
	 * @param e
	 */
	public void onRemovingItem(RemovingItemEvent e);

}
