package Csla.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

/**
 * Interface implemented by server-side data portal components.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:36 PM
 */
public interface IDataPortalServer {

	/**
	 * Create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException;

	/**
	 * Delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws Exception 
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context) throws DataPortalException, Exception;

	/**
	 * Get an existing business Object.
	 * 
	 * @param objectType    Type of business Object to retrieve.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 * @throws Exception 
	 */
	public DataPortalResult fetch(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException, Exception;

	/**
	 * Update a business Object.
	 * 
	 * @param obj    Business Object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 * @throws NoSuchMethodException 
	 * @throws URISyntaxException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public DataPortalResult update(Object obj, DataPortalContext context) throws DataPortalException;

}