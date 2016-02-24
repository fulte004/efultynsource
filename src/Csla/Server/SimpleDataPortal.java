package Csla.Server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.*;

import Csla.CommandBase;
import Csla.CriteriaBase;
import Csla.DataPortalEvent;
import Csla.MethodCaller;
import Csla.Properties.Resources;

/**
 * Implements the server-side DataPortal as discussed in Chapter 4.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:45 PM
 */
public class SimpleDataPortal implements IDataPortalServer {

	/**
	 * Create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException{
		Remote obj = null;

		try
		{
			// create an instance of the business Object.
			Constructor<?> con = objectType.getConstructor(objectType);
			obj = (Remote) con.newInstance((Object[])null);
			String name = objectType.getName();
			Naming.rebind(name,  obj);
			//obj = Activator.CreateInstance(objectType, true);

			// tell the business Object we're about to make a DataPortal_xyz call
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvoke", new DataPortalEvent(context));

			// tell the business Object to create its data
			Method method = MethodCaller.getCreateMethod(objectType, criteria);
			if (criteria instanceof Integer)
				MethodCaller.callMethod(obj, method);
			else
				MethodCaller.callMethod(obj, method, criteria);

			// mark the Object as new
			MethodCaller.callMethodIfImplemented(
					obj, "MarkNew");

			// tell the business Object the DataPortal_xyz call is complete
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvokeComplete", 
					new DataPortalEvent(context));

			// return the populated business Object as a result
			return new DataPortalResult(obj);
		}
		catch (Exception ex)
		{
			try
			{
				// tell the business Object there was an exception
				MethodCaller.callMethodIfImplemented(
						obj, "DataPortal_OnDataPortalException", 
						new DataPortalEvent(context), ex);
			}
			catch(Exception ex1)
			{
				// ignore exceptions from the exception handler
			}
			throw new DataPortalException(
					"DataPortal.Create " + Resources.getFailedOnServer(), 
					ex, new DataPortalResult(obj));
		}
	}

	/**
	 * 
	 * @param criteria
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	private static Object createBusinessObject(Object criteria) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Class<?> businessType;
		if (criteria.getClass().isInstance(CriteriaBase.class))
		{
			// get the type of the actual business Object
			// from CriteriaBase 
			businessType = (Class<?>) ((CriteriaBase)criteria).getObjectType();
		}
		else
		{
			// get the type of the actual business Object
			// based on the nested class scheme in the book
			businessType = criteria.getClass().getDeclaringClass();
		}

		// create an instance of the business Object
		Constructor<?> con = businessType.getConstructor((Class<?>)null);
		return (Remote) con.newInstance((Object[])null);
		//		        return Activator.CreateInstance(businessType, true);
	}

	/**
	 * Delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context) throws DataPortalException{
		Object obj = null;
		try
		{
			// create an instance of the business objet
			obj = createBusinessObject(criteria);

			// tell the business Object we're about to make a DataPortal_xyz call
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvoke", 
					new DataPortalEvent(context));

			// tell the business Object to delete itself
			MethodCaller.callMethod(
					obj, "DataPortal_Delete", criteria);

			// tell the business Object the DataPortal_xyz call is complete
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvokeComplete", 
					new DataPortalEvent(context));

			return new DataPortalResult();
		}
		catch (Exception ex)
		{
			try
			{
				// tell the business Object there was an exception
				MethodCaller.callMethodIfImplemented(
						obj, "DataPortal_OnDataPortalException",
						new DataPortalEvent(context), ex);
			}
			catch(Exception ex1)
			{
				// ignore exceptions from the exception handler
			}
			throw new DataPortalException(
					"DataPortal.Delete " + Resources.getFailedOnServer(), 
					ex, new DataPortalResult());
		}
	}

	/**
	 * Get an existing business Object.
	 * 
	 * @param objectType    Type of business Object to retrieve.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult fetch(Class<?> objectType, Object criteria, DataPortalContext context) throws DataPortalException{
		Remote obj = null;
		try
		{
			// create an instance of the business Object.
			Constructor<?> con = objectType.getConstructor(objectType);
			obj = (Remote) con.newInstance((Object[])null);
			String name = objectType.getName();
			Naming.rebind(name, obj);
			//		        	obj = Activator.CreateInstance(objectType, true);

			// tell the business Object we're about to make a DataPortal_xyz call
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvoke", 
					new DataPortalEvent(context));

			// tell the business Object to fetch its data
			Method method = MethodCaller.getFetchMethod(objectType, criteria);
			if (criteria instanceof Integer)
				MethodCaller.callMethod(obj, method);
			else
				MethodCaller.callMethod(obj, method, criteria);

			// mark the Object as old
			MethodCaller.callMethodIfImplemented(
					obj, "MarkOld");

			// tell the business Object the DataPortal_xyz call is complete
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvokeComplete", 
					new DataPortalEvent(context));

			// return the populated business Object as a result
			return new DataPortalResult(obj);
		}
		catch (Exception ex)
		{
			try
			{
				// tell the business Object there was an exception
				MethodCaller.callMethodIfImplemented(
						obj, "DataPortal_OnDataPortalException", 
						new DataPortalEvent(context), ex);
			}
			catch(Exception ex1)
			{
				// ignore exceptions from the exception handler
			}
			throw new DataPortalException(
					"DataPortal.Fetch " + Resources.getFailedOnServer(), 
					ex, new DataPortalResult(obj));
		}
	}

	/**
	 * Update a business Object.
	 * 
	 * @param obj    Business Object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws DataPortalException 
	 */
	public DataPortalResult update(Object obj, DataPortalContext context) throws DataPortalException{
		try
		{
			// tell the business Object we're about to make a DataPortal_xyz call
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvoke", 
					new DataPortalEvent(context));

			// tell the business Object to update itself
			if (obj instanceof Csla.Core.BusinessBase)
			{
				Csla.Core.BusinessBase busObj = (Csla.Core.BusinessBase)obj;
				if (busObj.isDeleted())
				{
					if (!busObj.isNew())
					{
						// tell the Object to delete itself
						MethodCaller.callMethod(
								busObj, "DataPortal_DeleteSelf");
					}
					// mark the Object as new
					MethodCaller.callMethodIfImplemented(
							busObj, "MarkNew");
				}
				else
				{
					if (busObj.isNew())
					{
						// tell the Object to insert itself
						MethodCaller.callMethod(
								busObj, "DataPortal_Insert");
					}
					else
					{
						// tell the Object to update itself
						MethodCaller.callMethod(
								busObj, "DataPortal_Update");
					}
					// mark the Object as old
					MethodCaller.callMethodIfImplemented(
							busObj, "MarkOld");
				}
			}
			else if (obj instanceof CommandBase)
			{
				// tell the Object to update itself
				MethodCaller.callMethod(
						obj, "DataPortal_Execute");
			}
			else
			{
				// this is an updatable collection or some other
				// non-BusinessBase type of Object
				// tell the Object to update itself
				MethodCaller.callMethod(
						obj, "DataPortal_Update");
				// mark the Object as old
				MethodCaller.callMethodIfImplemented(
						obj, "MarkOld");
			}

			// tell the business Object the DataPortal_xyz is complete
			MethodCaller.callMethodIfImplemented(
					obj, "DataPortal_OnDataPortalInvokeComplete", 
					new DataPortalEvent(context));

			return new DataPortalResult(obj);
		}
		catch (Exception ex)
		{
			try
			{
				// tell the business Object there was an exception
				MethodCaller.callMethodIfImplemented(
						obj, "DataPortal_OnDataPortalException",
						new DataPortalEvent(context), ex);
			}
			catch(Exception ex1)
			{
				// ignore exceptions from the exception handler
			}
			throw new DataPortalException(
					"DataPortal.Update " + Resources.getFailedOnServer(), 
					ex, new DataPortalResult(obj));
		}
	}

}