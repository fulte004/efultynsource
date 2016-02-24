package Csla;
import Csla.Core.ReadOnlyBindingList;
import Csla.Core.IReadOnlyCollection;
import Csla.Properties.Resources;

/**
 * This is the base class from which readonly collections of readonly objects
 * should be derived.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:41 PM
 */
public abstract class ReadOnlyListBase<T extends ReadOnlyListBase<T, C>, C> extends ReadOnlyBindingList<T> implements IReadOnlyCollection, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1793521451522559136L;

	/**
	 * Creates an instance of the object.
	 */
	protected ReadOnlyListBase(){
		initialize();
	}

//	public Object clone(){
//		return getClone();
//	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *      @returns A new Object containing the exact data of the original Object.
	 */
	@SuppressWarnings("unchecked")
	public T clone(){
		return (T)getClone();
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
	 * DataPortal_xyz method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_onDataPortalInvoke(DataPortalEvent e){

	}

	/**
	 * Called by the server-side DataPortal after calling the requested DataPortal_xyz
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
		return Csla.Core.ObjectCloner.clone(this);
	}

	/**
	 * Override this method to set up event handlers so user code in a partial class
	 * can respond to events raised by generated code.
	 */
	protected void initialize(){
		/* allows subclass to initialize events before any other activity occurs */
	}

}