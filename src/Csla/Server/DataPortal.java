package Csla.Server;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;

import Csla.ApplicationContext;
import Csla.CommandBase;
import Csla.MethodCaller;
import Csla.Transactional;
import Csla.TransactionalTypes;
import Csla.Properties.Resources;

/**
 * Implements the server-side DataPortal message router as discussed in Chapter 4.
 * 
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:32 PM
 */
public class DataPortal implements IDataPortalServer {

	/**
	 * 
	 * @param context
	 */
	private static void ClearContext(DataPortalContext context){
		// if the dataportal is not remote then
		// do nothing
		if (!context.isRemotePortal()) return;
		ApplicationContext.clear();
		//		        if (ApplicationContext.getAuthenticationType(null) != "Windows")
		//		          ApplicationContext.setUser(null);
	}

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
		try
		{
			setContext(context);

			DataPortalResult result;

			Method method = MethodCaller.getCreateMethod(objectType, criteria);

			IDataPortalServer portal;
			switch (TransactionalType(method))
			{
			//		            case EnterpriseServices:
				//		              portal = new ServicedDataPortal();
			//		              try
			//		              {
			//		                result = portal.Create(objectType, criteria, context);
			//		              }
			//		              finally
			//		              {
			//		                ((ServicedDataPortal)portal).Dispose();
			//		              }
			//		        
			//		              break;
			case TransactionScope:

				portal = new TransactionalDataPortal();
				result = portal.create(objectType, criteria, context);

				break;
			default:
				portal = new SimpleDataPortal();
				result = portal.create(objectType, criteria, context);
				break;
			}
			return result;
		}
		catch (Csla.Server.DataPortalException ex)
		{
			@SuppressWarnings("unused")
			Exception tmp = ex;
			throw ex;
		}
		catch (Exception ex)
		{
			throw new DataPortalException(
					"DataPortal.Create " + Resources.getFailedOnServer(),
					ex, new DataPortalResult());
		}
		finally
		{
			ClearContext(context);
		}
	}

	/**
	 * Delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 * @throws Exception 
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context) throws Exception{
		try
		{
			setContext(context);

			DataPortalResult result;

			Method method = MethodCaller.getMethod(
					MethodCaller.GetObjectType(criteria), "DataPortal_Delete", criteria);

			IDataPortalServer portal;
			switch (TransactionalType(method))
			{
			//		            case EnterpriseServices:
			//		              portal = new ServicedDataPortal();
			//		              try
			//		              {
			//		                result = portal.delete(criteria, context);
			//		              }
			//		              finally
			//		              {
			//		                ((ServicedDataPortal)portal).Dispose();
			//		              }
			//		              break;
			case TransactionScope:
				portal = new TransactionalDataPortal();
				result = portal.delete(criteria, context);
				break;
			default:
				portal = new SimpleDataPortal();
				result = portal.delete(criteria, context);
				break;
			}
			return result;
		}
		catch (Csla.Server.DataPortalException ex)
		{
			Exception tmp = ex;
			throw tmp;
		}
		catch (Exception ex)
		{
			throw new DataPortalException(
					"DataPortal.Delete " + Resources.getFailedOnServer(),
					ex, new DataPortalResult());
		}
		finally
		{
			ClearContext(context);
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
		try
		{
			setContext(context);

			DataPortalResult result;

			Method method = MethodCaller.getFetchMethod(objectType.getClass(), criteria);

			IDataPortalServer portal;
			switch (TransactionalType(method))
			{
			//		            case EnterpriseServices:
				//		              portal = new ServicedDataPortal();
			//		              try
			//		              {
			//		                result = portal.fetch(objectType, criteria, context);
			//		              }
			//		              finally
			//		              {
			//		                ((ServicedDataPortal)portal).Dispose();
			//		              }
			//		              break;
			case TransactionScope:
				portal = new TransactionalDataPortal();
				result = portal.fetch(objectType, criteria, context);
				break;
			default:
				portal = new SimpleDataPortal();
				result = portal.fetch(objectType, criteria, context);
				break;
			}
			return result;
		}
		catch (Exception ex)
		{
			throw new DataPortalException(
					"DataPortal.Fetch " + Resources.getFailedOnServer(),
					ex, new DataPortalResult());
		}
		finally
		{
			ClearContext(context);
		}
	}

	/**
	 * 
	 * @param method
	 */
	private static boolean isTransactionalMethod(Method method){
		Transactional test = method.getAnnotation(Transactional.class);
		return test != null;
	}

	/**
	 * 
	 * @param context
	 */
	@SuppressWarnings("static-access")
	private static void setContext(DataPortalContext context){
		// if the dataportal is not remote then
		// do nothing
		if (!context.isRemotePortal()) return;

		// set the context value so everyone knows the
		// code is running on the server
		ApplicationContext.setExecutionLocation(ApplicationContext.getExecutionLocation().SERVER);

		// set the app context to the value we got from the
		// client
		ApplicationContext.setContext(context.getClientContext(), context.getGlobalContext());

		// set the thread's culture to match the client
		Resources.setLocale(new Locale(context.getClientCulture()));


		if (ApplicationContext.getAuthenticationType(null) == "Windows")
		{
			// When using integrated security, Principal must be null
			if (context.getPrincipal() == null)
			{
				// Set Java to use integrated security
				AppDomain.CurrentDomain.SetPrincipalPolicy(PrincipalPolicy.WindowsPrincipal);
				return;
			}
			else
			{
				SecurityException ex =
					new SecurityException(Resources.getNoPrincipalAllowedException());
				ex.Action = Permissions.SecurityAction.Deny;
				throw ex;
			}
		}
		// We expect the Principal to be of the type BusinesPrincipal
		if (context.getPrincipal() != null)
		{
			if (context.getPrincipal().getClass().isInstance(Csla.Security.BusinessPrincipalBase.class))
			{
				ApplicationContext.setUser(context.getPrincipal());
			}
			else
			{
				SecurityException ex =
					new SecurityException(
							Resources.getBusinessPrincipalException() + " " +
							((Object)context.getPrincipal()).toString());
				ex.Action = Permissions.SecurityAction.Deny;
				throw ex;
			}
		}
		else
		{
			SecurityException ex =
				new SecurityException(
						Resources.BusinessPrincipalException + " Nothing");
			ex.Action = Permissions.SecurityAction.Deny;
			throw ex;
		}
	}

	/**
	 * 
	 * @param method
	 */
	private static TransactionalTypes TransactionalType(Method method){
		TransactionalTypes result;
		if (isTransactionalMethod(method))
		{
			Transactional attrib = method.getAnnotation(Transactional.class);
			result = attrib.getTransactionType();
		}
		else
			result = TransactionalTypes.Manual;
		return result;
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
			setContext(context);

			DataPortalResult result;

			Method method;
			String methodName;
			if (obj instanceof CommandBase)
				methodName = "DataPortal_Execute";
			else if (obj instanceof Csla.Core.BusinessBase)
			{
				Csla.Core.BusinessBase tmp = (Csla.Core.BusinessBase)obj;
				if (tmp.isDeleted())
					methodName = "DataPortal_DeleteSelf";
				else
					if (tmp.isNew())
						methodName = "DataPortal_Insert";
					else
						methodName = "DataPortal_Update";
			}
			else
				methodName = "DataPortal_Update";

			method = MethodCaller.getMethod(obj.getClass(), methodName);

			IDataPortalServer portal;
			switch (TransactionalType(method))
			{
			//		            case TransactionalTypes.EnterpriseServices:
			//		              portal = new ServicedDataPortal();
			//		              try
			//		              {
			//		                result = portal.Update(obj, context);
			//		              }
			//		              finally
			//		              {
			//		                ((ServicedDataPortal)portal).Dispose();
			//		              }
			//		              break;
			case TransactionScope:
				portal = new TransactionalDataPortal();
				result = portal.update(obj, context);
				break;
			default:
				portal = new SimpleDataPortal();
				result = portal.update(obj, context);
				break;
			}
			return result;
		}
		catch (Exception ex)
		{
			throw new DataPortalException(
					"DataPortal.Update " + Resources.getFailedOnServer(),
					ex, new DataPortalResult());
		}
		finally
		{
			ClearContext(context);
		}
	}

}