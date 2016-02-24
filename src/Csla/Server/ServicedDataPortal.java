package Csla.Server;

import java.lang.reflect.Type;

/**
 * Implements the server-side Serviced DataPortal described in Chapter 4.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:44 PM
 */
public class ServicedDataPortal extends ServicedComponent implements IDataPortalServer {

	/**
	 * Wraps a Create call in a ServicedComponent.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a COM+ transaction to provide transactional support.
	 *        @returns A populated business object.
	 * 
	 * @param objectType    A <see cref="Type">Type</see> object indicating the type
	 * of business object to be created.
	 * @param criteria    A custom criteria object providing any extra information
	 * that may be required to properly create the object.
	 * @param context    Context data from the client.
	 */
	public DataPortalResult create(Type objectType, Object criteria, DataPortalContext context){
		SimpleDataPortal portal = new SimpleDataPortal();
		        return portal.create(objectType, criteria, context);
	}

	/**
	 * Wraps a Delete call in a ServicedComponent.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a COM+ transaction to provide transactional support.
	 * 
	 * @param criteria    Object-specific criteria.
	 * @param context    Context data from the client.
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context){
		SimpleDataPortal portal = new SimpleDataPortal();
		        return portal.delete(criteria, context);
	}

	/**
	 * Wraps a Fetch call in a ServicedComponent.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a COM+ transaction to provide transactional support.
	 *        @returns A populated business Object.
	 * 
	 * @param objectType    Type of business Object to retrieve.
	 * @param criteria    Object-specific criteria.
	 * @param context    Object containing context data from client.
	 */
	public DataPortalResult fetch(Type objectType, Object criteria, DataPortalContext context){
		SimpleDataPortal portal = new SimpleDataPortal();
		        return portal.fetch(objectType, criteria, context);
	}

	/**
	 * Wraps an Update call in a ServicedComponent.
	 * 
	 *        @remark This method delegates to
	 *        <see cref="SimpleDataPortal">SimpleDataPortal</see> but wraps that call
	 * within a COM+ transaction to provide transactional support.
	 *        @returns A reference to the newly updated Object.
	 * 
	 * @param obj    A reference to the Object being updated.
	 * @param context    Context data from the client.
	 */
	public DataPortalResult update(Object obj, DataPortalContext context){
		SimpleDataPortal portal = new SimpleDataPortal();
		        return portal.update(obj, context);
	}

}