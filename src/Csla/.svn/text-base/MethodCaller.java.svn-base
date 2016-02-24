package Csla;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Csla.Properties.Resources;
import Csla.Server.CallMethodException;
/**
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:38 PM
 */
public class MethodCaller {

	//	private BindingFlags allLevelFlags = BindingFlags.FlattenHierarchy |
	//	            BindingFlags.Instance |
	//	            BindingFlags.Public |
	//	            BindingFlags.NonPublic;
	//	private BindingFlags oneLevelFlags = BindingFlags.DeclaredOnly |
	//	            BindingFlags.Instance |
	//	            BindingFlags.Public |
	//	            BindingFlags.NonPublic;

	public MethodCaller(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Uses reflection to dynamically invoke a method, throwing an exception if it is
	 * not implemented on the target Object.
	 * 
	 * @param obj
	 * @param method
	 * @param parameters
	 * @throws CallMethodException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Object callMethod(Object obj, String method, Object... parameters) throws UnsupportedOperationException, CallMethodException, SecurityException, NoSuchMethodException {
		Method info = getMethod(obj.getClass(), method, parameters);
		if (info == null)
			throw new UnsupportedOperationException(method + " " + Resources.getMethodNotImplemented());
		return callMethod(obj, info, parameters);
	}

	/**
	 * Uses reflection to dynamically invoke a method, throwing an exception if it is
	 * not implemented on the target Object.
	 * 
	 * @param obj
	 * @param info
	 * @param parameters
	 * @throws CallMethodException 
	 */
	public static Object callMethod(Object obj, Method info, Object... parameters) throws CallMethodException{
		// call a private method on the Object
		Object result;
		try
		{
			result = info.invoke(obj, parameters);
		}
		catch (Exception e)
		{
			throw new Csla.Server.CallMethodException(info.getName() + " " + Resources.getMethodCallFailed(), e);
		}
		return result;
	}

	/**
	 * Uses reflection to dynamically invoke a method if that method is implemented on
	 * the target Object.
	 * 
	 * @param obj
	 * @param method
	 * @param parameters
	 * @throws CallMethodException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Object callMethodIfImplemented(Object obj, String method, Object... parameters) throws CallMethodException, SecurityException, NoSuchMethodException{
		Method info = getMethod(obj.getClass(), method, parameters);
		if (info != null)
			return callMethod(obj, info, parameters);
		else
			return null;
	}

	/**
	 * Returns information about the specified method, even if the parameter types are
	 * generic and are located in an abstract generic base class.
	 * 
	 * @param objType
	 * @param method
	 * @param types
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method findMethod(Type objType, String method, Class<?>... types) throws SecurityException, NoSuchMethodException{
		Method info = null;
		Class<?> objClass = objType.getClass();
		do
		{
			//find for a strongly typed match
			info = objClass.getMethod(method, types);
			if (info != null)
				break;  //match found

			objType = objType.getClass().getGenericSuperclass();
		} while (objType != null);

		return info;
	}

	/**
	 * Returns information about the specified method, finding the method based purely
	 * on the method name and number of parameters.
	 * 
	 * @param objType
	 * @param method
	 * @param parameterCount
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method findMethod(Type objType, String method, int parameterCount) throws SecurityException, NoSuchMethodException{
		// walk up the inheritance hierarchy looking
		// for a method with the right number of
		// parameters
		Method result = null;
		Class<?> currentType = objType.getClass();
		do
		{
			Method info = currentType.getMethod(method);
			if (info != null)
			{
				if (info.getParameterTypes().length == parameterCount)
				{
					// got a match so use it
					result = info;
					break;
				}
			}
			currentType = currentType.getSuperclass();
		} while (currentType != null);
		return result;
	}

	/**
	 * Gets a reference to the DataPortal_Create method for the specified business
	 * Object type.
	 * 
	 *      @remark If the criteria parameter value is an integer, that is a special
	 * flag indicating that the parameter should be considered missing (not
	 * Nothing/null - just not there).
	 * 
	 * @param objectType    Type of the business Object.
	 * @param criteria    Criteria parameter value.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getCreateMethod(Class<?> objectType, Object criteria) throws SecurityException, NoSuchMethodException{
		Method method = null;
		if (criteria.getClass().isPrimitive())
		{
			// an "Integer" criteria is a special flag indicating
			// that criteria is empty and should not be used
			method = MethodCaller.getMethod(objectType, "DataPortal_Create");
		}
		else
			method = MethodCaller.getMethod(objectType, "DataPortal_Create", criteria.getClass());
		return method;
	}

	/**
	 * Gets a reference to the DataPortal_Fetch method for the specified business
	 * Object type.
	 * 
	 *      @remark If the criteria parameter value is an integer, that is a special
	 * flag indicating that the parameter should be considered missing (not
	 * Nothing/null - just not there).
	 * 
	 * @param objectType    Type of the business Object.
	 * @param criteria    Criteria parameter value.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getFetchMethod(Class<?> objectType, Object criteria) throws SecurityException, NoSuchMethodException{
		Method method = null;
		if (criteria.getClass().isPrimitive())
		{
			// an "Integer" criteria is a special flag indicating
			// that criteria is empty and should not be used
			method = MethodCaller.getMethod(objectType, "DataPortal_Fetch");
		}
		else
			method = MethodCaller.getMethod(objectType, "DataPortal_Fetch", criteria.getClass());
		return method;
	}

	/**
	 * Uses reflection to locate a matching method on the target Object.
	 * 
	 * @param objectType
	 * @param method
	 * @param parameters
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getMethod(Class<?> objectType, String method, Object... parameters) throws SecurityException, NoSuchMethodException{
		Method result = null;

		// try to find a strongly typed match

		// first see if there's a matching method
		// where all params match types
		result = findMethod(objectType, method, GetParameterTypes(parameters));

		if (result == null)
		{
			// no match found - so look for any method
			// with the right number of parameters
			result = findMethod(objectType, method, parameters.length);
		}

		// no strongly typed match found, get default
		if (result == null)
		{
			try
			{ 
				result = objectType.getDeclaredMethod(method, (Class<?>[]) parameters); 
			}
			catch (NoSuchMethodException ex)
			{
				Method[] methods = objectType.getMethods();
				for (Method m : methods)
					if (m.getName() == method && m.getTypeParameters().length == parameters.length)
					{
						result = m;
						break;
					}
				if (result == null)
					throw ex;
			}
		}
		return result;
	}

	/**
	 * Returns a business Object type based on the supplied criteria Object.
	 * 
	 * @param criteria
	 */
	public static Class<?> GetObjectType(Object criteria){
		if (CriteriaBase.class.isInstance(criteria))
		{
			// get the type of the actual business Object
			// from ICriteria
			return ((CriteriaBase)criteria).getClass();
		}
		else
		{
			// get the type of the actual business Object
			// based on the nested class scheme in the book
			return criteria.getClass().getDeclaringClass();
		}
	}

	/**
	 * 
	 * @param parameters
	 */
	static Class<?>[] GetParameterTypes(Object... parameters){
		List<Type> result = new ArrayList<Type>();
		for (Object item : parameters)
			if (item == null)
				result.add(Object.class);
			else
				result.add(item.getClass());
		return (Class<?>[]) result.toArray();
	}

}