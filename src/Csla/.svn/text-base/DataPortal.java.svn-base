package Csla;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import Csla.Core.ObjectCloner;
import Csla.DataPortalClient.IDataPortalProxy;
import Csla.Properties.Resources;
import Csla.Server.DataPortalContext;
import Csla.Server.DataPortalException;
import Csla.Server.DataPortalResult;


/**
 * This is the client-side DataPortal as described in Chapter 4.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:32 PM
 */
public class DataPortal {

	private static IDataPortalProxy _localPortal;
	private static IDataPortalProxy _portal;
	private static ArrayList<DataPortalEventListener> _listeners = new ArrayList<DataPortalEventListener>();
	private static int EmptyCriteria = 1;

	/**
	 * Adds an object that implements the DataPortalEventListener interface to the list of listeners for DataPortal events.
	 * @param listener An object that implements the DataPortalEventListener interface
	 */
	public void add_DataPortalEvent(DataPortalEventListener listener){
		_listeners.add(listener);
	}
	
	/**
	 * Removes an object that implements the DataPortalEventListener interface from the list of listeners for DataPortal events.
	 * @param listener An object that implements the DataPortalEventListener interface
	 */
	public void remove_DataPortalEvent(DataPortalEventListener listener){
		_listeners.remove(listener);
	}
	/**
	 * Called by a factory method in a business class to create a new object, which is
	 * loaded with default values from the database.
	 * 
	 *      @returns A new object, populated with default values.
	 * 
	 * @param criteria    Object-specific criteria.
	 * @throws DataPortalException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public static <T> T create(Object criteria) throws SecurityException, NoSuchMethodException, DataPortalException{
		T type = null;
		return (T)create(type.getClass(), criteria);
	}

	/**
	 * Called by a factory method in a business class to create a new Object, which is
	 * loaded with default values from the database.
	 * @throws DataPortalException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * 
	 *      @returns A new Object, populated with default values.
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public  static <T> T create() throws SecurityException, NoSuchMethodException, DataPortalException{
		T type = null;
		return (T)create(type.getClass(), EmptyCriteria);
	}

	/**
	 * Called by a factory method in a business class to create a new Object, which is
	 * loaded with default values from the database.
	 * 
	 *      @returns A new Object, populated with default values.
	 * 
	 * @param criteria    Object-specific criteria.
	 */
	//	public static Object Create(Object criteria){
	//		return Create(MethodCaller.GetObjectType(criteria), criteria);
	//	}

	/**
	 * 
	 * @param objectType
	 * @param criteria
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 */
	private static Object create(Class<?> objectType, Object criteria) throws SecurityException, NoSuchMethodException, DataPortalException{
		DataPortalResult result;
		_listeners = new ArrayList<DataPortalEventListener>();
		Method method = MethodCaller.getCreateMethod(objectType, criteria);

		IDataPortalProxy proxy;
		proxy = getDataPortalProxy(runLocal(method));

		onDataPortalInitInvoke(null);

		DataPortalContext dpContext = 
			new DataPortalContext(getPrincipal(), proxy.isServerRemote());

		for(DataPortalEventListener l : _listeners)
			l.onDataPortalInvoke(new DataPortalEvent(dpContext));

		try
		{
			result = proxy.create(objectType, criteria, dpContext);
		}
		catch (DataPortalException ex)
		{
			result = ex.getResult();
			if (proxy.isServerRemote())
				ApplicationContext.setGlobalContext(result.getGlobalContext());
			throw new DataPortalException(
					String.format("DataPortal.Create $1% ($2%)", Resources.getFailed(), ex), 
					ex, result);
		}

		if (proxy.isServerRemote())
			ApplicationContext.setGlobalContext(result.getGlobalContext());

		for(DataPortalEventListener l : _listeners)
			l.onDataPortalInvokeComplete(new DataPortalEvent(dpContext));

		return result.getReturnObject();
	}

	public void addDataPortalEventListener(DataPortalEventListener listener){
		_listeners.add(listener);
	}

	public void removeDataPortalEventListener(DataPortalEventListener listener){
		_listeners.remove(listener);
	}

	/**
	 * Called by a Shared (static in C#) method in the business class to cause
	 * immediate deletion of a specific Object from the database.
	 * 
	 * @param criteria    Object-specific criteria.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 */
	public static void delete(Object criteria) throws SecurityException, NoSuchMethodException, DataPortalException{
		DataPortalResult result = null;

		Method method = MethodCaller.getMethod(
				MethodCaller.GetObjectType(criteria).getClass(), "DataPortal_Delete", criteria);

		IDataPortalProxy proxy;
		proxy = getDataPortalProxy(runLocal(method));

		onDataPortalInitInvoke(null);

		DataPortalContext dpContext = new DataPortalContext(getPrincipal(), proxy.isServerRemote());

		for(DataPortalEventListener l : _listeners)
			l.onDataPortalInvoke(new DataPortalEvent(dpContext));

		try
		{
			result = proxy.delete(criteria, dpContext);
		}
		catch (DataPortalException ex)
		{
			result = ex.getResult();
			if (proxy.isServerRemote())
				ApplicationContext.setGlobalContext(result.getGlobalContext());
			throw new DataPortalException(
					String.format("DataPortal.Delete $1% ($2%)", Resources.getFailed(), ex.getMessage()), 
					ex, result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (proxy.isServerRemote())
			ApplicationContext.setGlobalContext(result.getGlobalContext());

		for(DataPortalEventListener l : _listeners)
			l.onDataPortalInvokeComplete(new DataPortalEvent(dpContext));
	}

	/**
	 * Called to execute a Command Object on the server.
	 * 
	 *      @remark  To be a Command Object, the Object must inherit from
	 *      <see cref="CommandBase">CommandBase</see>.
	 *       Note that this method returns a reference to the updated business Object.
	 * If the server-side DataPortal is running remotely, this will be a new and
	 * different Object from the original, and all Object references MUST be updated
	 * to use this new Object.
	 *       On the server, the Command Object's DataPortal_Execute() method will be
	 * invoked. Write any server-side code in that method.
	 *      @returns A reference to the updated Command Object.
	 * 
	 * @param obj    A reference to the Command Object to be executed.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T execute(T obj) throws SecurityException, NoSuchMethodException, DataPortalException{
		return (T)update(obj);
	}

	/**
	 * Called to execute a Command Object on the server.
	 * 
	 *      @remark  Note that this method returns a reference to the updated business
	 * Object. If the server-side DataPortal is running remotely, this will be a new
	 * and different Object from the original, and all Object references MUST be
	 * updated to use this new Object.
	 *       On the server, the Command Object's DataPortal_Execute() method will be
	 * invoked. Write any server-side code in that method.
	 *      @returns A reference to the updated Command Object.
	 * 
	 * @param obj    A reference to the Command Object to be executed.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 */
	public static CommandBase execute(CommandBase obj) throws SecurityException, NoSuchMethodException, DataPortalException{
		return (CommandBase)update(obj);
	}

	/**
	 * Called by a factory method in a business class to retrieve an Object, which is
	 * loaded with values from the database.
	 * 
	 *      @returns An Object populated with values from the database.
	 * 
	 * @param criteria    Object-specific criteria.
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public static <T> T fetch(Object criteria) throws Exception{
		T type = null;
		return (T)fetch(type.getClass(), criteria);
	}

	/**
	 * Called by a factory method in a business class to retrieve an Object, which is
	 * loaded with values from the database.
	 * @throws Exception 
	 * @returns An Object populated with values from the database.
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public static <T> T fetch() throws Exception{
		T type = null;
		return (T)fetch(type.getClass(), EmptyCriteria);
	}

	/**
	 * Called by a factory method in a business class to retrieve an Object, which is
	 * loaded with values from the database.
	 * 
	 *      @returns An Object populated with values from the database.
	 * 
	 * @param criteria    Object-specific criteria.
	 */
	//	public static Object fetch(Object criteria){
	//		return fetch(MethodCaller.GetObjectType(criteria), criteria);
	//	}

	/**
	 * 
	 * @param objectType
	 * @param criteria
	 * @throws Exception 
	 */
	private static Object fetch(Class<?> objectType, Object criteria) throws Exception{
		DataPortalResult result;

		Method method = MethodCaller.getFetchMethod(objectType, criteria);

		IDataPortalProxy proxy;
		proxy = getDataPortalProxy(runLocal(method));

		onDataPortalInitInvoke(null);

		DataPortalContext dpContext = 
			new DataPortalContext(getPrincipal(), 
					proxy.isServerRemote());

		onDataPortalInvoke(new DataPortalEvent(dpContext));

		//		      try
		//		      {
			result = proxy.fetch(objectType, criteria, dpContext);
			//		      }
			//		      catch (DataPortalException ex)
			//		      {
			//		        result = ex.getResult();
			//		        if (proxy.isServerRemote())
			//		          ApplicationContext.setGlobalContext(result.getGlobalContext());
			//		        String innerMessage = "";
			//		        if (ex.getCause().getClass().isInstance(Csla.Server.CallMethodException.class))
			//		        {
			//		          if (ex.getCause() != null)
			//		            innerMessage = ex.getCause().getMessage();
			//		        }
			//		        else
			//		        {
			//		          innerMessage = ex.getCause().getMessage();
			//		        }
			//		        throw new DataPortalException(
			//		          String.format("DataPortal.Fetch $1% ($2%)", Resources.getFailed(), innerMessage), 
			//		          (Exception)ex.getCause(), result);
			//		      }

			if (proxy.isServerRemote())
				ApplicationContext.setGlobalContext(result.getGlobalContext());

			onDataPortalInvokeComplete(new DataPortalEvent(dpContext));

			return result.getReturnObject();
	}

	/**
	 * 
	 * @param forceLocal
	 */
	private static IDataPortalProxy getDataPortalProxy(boolean forceLocal){
		if (forceLocal)
		{
			if (_localPortal == null)
				_localPortal = new Csla.DataPortalClient.LocalProxy();
			return _localPortal;
		}
		else
		{
			if (_portal == null)
			{
				String proxyTypeName = ApplicationContext.getDataPortalProxy(null);
				if (proxyTypeName == "Local")
					_portal = new Csla.DataPortalClient.LocalProxy();
				else
				{
					String className = proxyTypeName.substring(0, proxyTypeName.indexOf(",")).trim();
					try {
						Class<?> classDefinition = Class.forName(className);
					_portal = (Csla.DataPortalClient.IDataPortalProxy)classDefinition.newInstance();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			return _portal;
		}
	}

	private static Principal getPrincipal(){
		if (ApplicationContext.getAuthenticationType() == "Windows")
		{
			// Windows integrated security
			return null;
		}
		else
		{
			// we assume using the CSLA framework security
			return ApplicationContext.getUser(null);
		}
	}

	/**
	 * 
	 * @param e
	 */
	private static void onDataPortalInitInvoke(Object e){
		for(DataPortalEventListener listener : _listeners)
			listener.onDataPortalInitInvoke(e);
	}

	/**
	 * 
	 * @param e
	 */
	private static void onDataPortalInvoke(DataPortalEvent e){
		for(DataPortalEventListener listener : _listeners)
			listener.onDataPortalInvoke(e);
	}

	/**
	 * 
	 * @param e
	 */
	private static void onDataPortalInvokeComplete(DataPortalEvent e){
		for(DataPortalEventListener listener : _listeners)
			listener.onDataPortalInvokeComplete(e);
	}

	/**
	 * Releases any remote data portal proxy Object, so the next data portal call will
	 * create a new proxy instance.
	 */
	public static void releaseProxy(){
		_portal = null;
	}

	/**
	 * 
	 * @param method
	 */
	private static boolean runLocal(Method method){
		RunLocal test = method.getAnnotation(RunLocal.class);
		return test != null;
	}

	/**
	 * Called by the business Object's Save() method to insert, update or delete an
	 * Object in the database.
	 * 
	 *      @remark Note that this method returns a reference to the updated business
	 * Object. If the server-side DataPortal is running remotely, this will be a new
	 * and different Object from the original, and all Object references MUST be
	 * updated to use this new Object.
	 *      @returns A reference to the updated business Object.
	 * 
	 * @param obj    A reference to the business Object to be updated.
	 */
//	public static <T> T update(T obj){
//		return (T)update((Object)obj);
//	}

	/**
	 * Called by the business Object's Save() method to insert, update or delete an
	 * Object in the database.
	 * 
	 *      @remark Note that this method returns a reference to the updated business
	 * Object. If the server-side DataPortal is running remotely, this will be a new
	 * and different Object from the original, and all Object references MUST be
	 * updated to use this new Object.
	 *      @returns A reference to the updated business Object.
	 * 
	 * @param obj    A reference to the business Object to be updated.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws DataPortalException 
	 */
	public static Object update(Object obj) throws SecurityException, NoSuchMethodException, DataPortalException{
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

		IDataPortalProxy proxy;
		proxy = getDataPortalProxy(runLocal(method));

		onDataPortalInitInvoke(null);

		DataPortalContext dpContext = 
			new DataPortalContext(getPrincipal(), proxy.isServerRemote());

		for(DataPortalEventListener l : _listeners)
			l.onDataPortalInvoke(new DataPortalEvent(dpContext));

//		try
//		{
			if (!proxy.isServerRemote() && ApplicationContext.getAutoCloneOnUpdate())
			{
				// when using local data portal, automatically
				// clone original Object before saving
				Cloneable cloneable = (Cloneable)obj;
				if (cloneable != null)
					obj = ObjectCloner.clone(obj); //cloneable.clone();
			}
			result = proxy.update(obj, dpContext);
//		}
//		catch (DataPortalException ex)
//		{
//			result = ex.getResult();
//			if (proxy.isServerRemote())
//				ApplicationContext.setGlobalContext(result.getGlobalContext());
//			throw new DataPortalException(
//					String.format("DataPortal.Update $1% ($2%)", Resources.getFailed(), ex), 
//					ex, result.getReturnObject());
//		}

		if (proxy.isServerRemote())
			ApplicationContext.setGlobalContext(result.getGlobalContext());

		onDataPortalInvokeComplete(new DataPortalEvent(dpContext));

		return result.getReturnObject();
	}

}