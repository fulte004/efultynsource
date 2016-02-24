package Csla.Server;

/**
 * Implements the server-side Serviced DataPortal described in Chapter 4.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:47 PM
 */
public class TransactionalDataPortal implements IDataPortalServer {

	/**
	 * Wraps a Create call in a TransactionScope
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a
	 *        <see cref="TransactionScope">TransactionScope</see> to provide
	 * transactional support via System.Transactions.
	 *        @returns A populated business object.
	 * 
	 * @param objectType    A <see cref="Type">Type</see> object indicating the type
	 * of business object to be created.
	 * @param criteria    A custom criteria object providing any extra information
	 * that may be required to properly create the object.
	 * @param context    Context data from the client.
	 */
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context){
		DataPortalResult result;
		        TransactionScope tr = new TransactionScope();
		        {
		          SimpleDataPortal portal = new SimpleDataPortal();
		          result = portal.create(objectType, criteria, context);
		          tr.Complete();
		        }
		        return result;
	}

	/**
	 * Called by the client-side DataPortal to delete an Object.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a
	 *        <see cref="TransactionScope">TransactionScope</see> to provide
	 * transactional support via System.Transactions.
	 * 
	 * @param criteria    Object-specific criteria.
	 * @param context    Context data from the client.
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context){
		DataPortalResult result;
		        TransactionScope tr = new TransactionScope();
		        {
		          SimpleDataPortal portal = new SimpleDataPortal();
		          result = portal.delete(criteria, context);
		          tr.Complete();
		        }
		        return result;
	}

	/**
	 * Called by the client-side DataProtal to retrieve an Object.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a
	 *        <see cref="TransactionScope">TransactionScope</see> to provide
	 * transactional support via System.Transactions.
	 *        @returns A populated business Object.
	 * 
	 * @param objectType    Type of business Object to retrieve.
	 * @param criteria    Object-specific criteria.
	 * @param context    Object containing context data from client.
	 */
	public DataPortalResult fetch(Class<?> objectType, Object criteria, DataPortalContext context){
		DataPortalResult result;
		        TransactionScope tr = new TransactionScope();
		        {
		          SimpleDataPortal portal = new SimpleDataPortal();
		          result = portal.fetch(objectType, criteria, context);
		          tr.Complete();
		        }
		        return result;
	}

	/**
	 * Called by the client-side DataPortal to update an Object.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a
	 *        <see cref="TransactionScope">TransactionScope</see> to provide
	 * transactional support via System.Transactions.
	 *        @returns A reference to the newly updated Object.
	 * 
	 * @param obj    A reference to the Object being updated.
	 * @param context    Context data from the client.
	 */
	public DataPortalResult update(Object obj, DataPortalContext context){
		DataPortalResult result;
		        TransactionScope tr = new TransactionScope();
		        {
		          SimpleDataPortal portal = new SimpleDataPortal();
		          result = portal.update(obj, context);
		          tr.Complete();
		        }
		        return result;
	}

}