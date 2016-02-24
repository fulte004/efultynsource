package Csla;
import Csla.Core.CommandObject;
import Csla.Properties.Resources;

/**
 * This is the base class from which command objects will be derived.
 * 
 *    @remark  Command objects allow the execution of arbitrary server-side
 * functionality. Most often, this involves the invocation of a stored procedure
 * in the database, but can involve any other type of stateless, atomic call to
 * the server instead.
 *     To implement a command object, inherit from CommandBase and override the
 * DataPortal_Execute method. In this method you can implement any server-side
 * code as required.
 *     To pass data to/from the server, use instance variables within the command
 * object itself. The command object is instantiated on the client, and is passed
 * by value to the server where the DataPortal_Execute method is invoked. The
 * command object is then returned to the client by value.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:27 PM
 */
public abstract class CommandBase implements CommandObject {
	/**
	 * 
	 * @param criteria
	 */
	protected void DataPortal_Create(Object criteria){
		throw new UnsupportedOperationException(Resources.getCreateNotSupportedException());
	}

	/**
	 * 
	 * @param criteria
	 */
	protected void DataPortal_Delete(Object criteria){
		throw new UnsupportedOperationException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to implement any server-side code that is to be run when
	 * the command is executed.
	 */
	protected void DataPortal_Execute(){
		throw new UnsupportedOperationException(Resources.getExecuteNotSupportedException());
	}

	/**
	 * 
	 * @param criteria
	 */
	protected void DataPortal_Fetch(Object criteria){
		throw new UnsupportedOperationException(Resources.getFetchNotSupportedException());
	}

	/**
	 * Called by the server-side DataPortal if an exception occurs during server-side
	 * processing.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 * @param ex    The Exception thrown during processing.
	 */
	protected void DataPortal_OnDataPortalException(DataPortalEvent e, Exception ex){

	}

	/**
	 * Called by the server-side DataPortal prior to calling the requested
	 * DataPortal_xyz method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvoke(DataPortalEvent e){

	}

	/**
	 * Called by the server-side DataPortal after calling the requested DataPortal_xyz
	 * method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvokeComplete(DataPortalEvent e){

	}

	protected void DataPortal_Update(){
		throw new UnsupportedOperationException(Resources.getUpdateNotSupportedException());
	}

	/**
	 * Override this method to set up event handlers so user code in a partial class
	 * can respond to events raised by generated code.
	 */
	protected void Initialize(){
		/* allows subclass to initialize events before any other activity occurs */
	}

}