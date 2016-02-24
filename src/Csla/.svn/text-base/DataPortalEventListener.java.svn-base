package Csla;

import java.util.EventListener;

public interface DataPortalEventListener extends EventListener {
	/**
	 * Raised by DataPortal before it starts
     * setting up to call a server-side
     * DataPortal method.
	 * @param e
	 */
	public void onDataPortalInitInvoke(Object e);
	
	/**
	 * Raised by DataPortal prior to calling the 
     * requested server-side DataPortal method.
	 * @param e
	 */
	public void onDataPortalInvoke(DataPortalEvent e);
	
	/**
	 * Raised by DataPortal after the requested 
     * server-side DataPortal method call is complete.
	 * @param e
	 */
	public void onDataPortalInvokeComplete(DataPortalEvent e);
}
