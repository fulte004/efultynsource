package Csla;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Contains utility methods used by the CSLA .NET framework.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:48 PM
 */
public class Utilities {

	/**
	 * Allows late bound invocation of properties and methods.
	 * 
	 *      @returns The result of the property or method invocation.
	 * 
	 * @param target    Object implementing the property or method.
	 * @param methodName    Name of the property or method.
	 * @param callType    Specifies how to invoke the property or method.
	 * @param args    List of arguments to pass to the method.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Object callByName(Object target, String methodName, CallType callType, Object... args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		switch (callType)
		{
		case GET:
		{
			Method m;
			m = target.getClass().getMethod(methodName, (Class[]) args);
			return m.invoke(target, args);
		}
		case LET:
		case SET:
		{
			Method m;
			m = target.getClass().getMethod(methodName, (Class[]) args);
			m.invoke(target, args);
			return null;
		}
		case METHOD:
		{
			Method m;
			m = target.getClass().getMethod(methodName, (Class[]) args);
			return m.invoke(target, args);
		}
		}
		return null;
	}

	/**
	 * Returns the type of child Object contained in a collection or list.
	 * 
	 * @param listType    Type of the list.
	 */
	public static Type getChildItemType(Type listType){
		Type result = null;
		Class<?> listClass = listType.getClass();
		if (listClass.isArray())
			result = listClass.getComponentType();
		else
		{
			//		        DefaultMemberAttribute indexer =
			//		          (DefaultMemberAttribute)Attribute.GetCustomAttribute(
			//		          listType, typeof(DefaultMemberAttribute));
			//		        if (indexer != null)
			//		          for (Field prop : listClass.getFields()(
			//		            BindingFlags.Public | 
			//		            BindingFlags.Instance | 
			//		            BindingFlags.FlattenHierarchy))
			//		          {
			//		            if (prop.getName() == indexer.MemberName)
			//		              result = Utilities.getPropertyType(prop.getType());
			//		          }
		}
		return result;
	}

	/**
	 * Returns a property's type, dealing with Nullable(Of T) if necessary.
	 * 
	 * @param propertyType    Type of the property as returned by reflection.
	 */
	public static Type getPropertyType(Type propertyType){
		Class<?> type = propertyType.getClass();

		if (type.getGenericSuperclass() != null &&
				(type.isPrimitive()))
			return type.getGenericSuperclass();
		return type;
	}

	/**
	 * Determines whether the specified value can be converted to a valid number.
	 * 
	 * @param value
	 */
	public static boolean isNumeric(Object value){
		boolean result = false;
		try {
			Double.parseDouble(value.toString());
			result = true;
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}

}