package Csla.Data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Csla.Utilities;
import Csla.Properties.Resources;
import Csla.c5.Convert;
import Csla.c5.TypeConverter;
import Csla.c5.TypeDescriptor;

/**
 * Map data from a source into a target object by copying public property values.
 * 
 *      @remark
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:31 PM
 */
public class DataMapper {


	/**
	 * 
	 * @param propertyType
	 * @param valueType
	 * @param value
	 * @throws Exception 
	 */
	private static Object coerceValue(Class<?> propertyType, Class<?> valueType, Object value) throws Exception{
		if (propertyType.equals(valueType))
		        {
		          // types match, just return value
		          return value;
		        }
		        else
		        {
		          if (propertyType.getGenericInterfaces().length > 0)
		          {
		            if (!propertyType.isPrimitive())
		            {
		              if (value == null) 
		                return null;
		              else if (valueType.equals(String.class) && (String)value == "")
		                return null;
		            }
		            propertyType = Utilities.getPropertyType(propertyType).getClass();
		          }
		        
		          if (propertyType.isEnum() && valueType.equals(String.class))
		            return value.toString();
		        
		          if (propertyType.isPrimitive() && valueType.equals(String.class) && ((String)value).isEmpty())
		            value = 0;
		          
		          try
		          {
		            return Convert.ChangeType(value, Utilities.getPropertyType(propertyType));
		          }
		          catch(Exception e)
		          {
		            TypeConverter cnv = TypeDescriptor.GetConverter(Utilities.getPropertyType(propertyType));
		            if (cnv != null && cnv.CanConvertFrom(value.getClass()))
		              return cnv.ConvertFrom(value);
		            else
		              throw e;
		          }
		        }
	}

	/**
	 * 
	 * @param sourceType
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	private static Method[] GetSourceProperties(Class<?> sourceType) throws SecurityException, NoSuchMethodException{
		List<Method> result = new ArrayList<Method>();
		Method[] props = sourceType.getMethods();
		        for (Method item : props)
		          if (item.isAccessible())
		            result.add(sourceType.getMethod(item.getName()));
		        return (Method[]) result.toArray();
	}

	/**
	 * 
	 * @param member
	 * @param source
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected static Object GetValue(Member member, Object source) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if (member.getClass() == Method.class)
		          return ((Method)member).invoke(source);
		        else
		          return ((Field)member).get(source);
	}

	/**
	 * Copies values from the source into the properties of the target.
	 * 
	 *        @remark The key names in the dictionary must match the property names on
	 * the target Object. Target properties may not be readonly or indexed.
	 * 
	 * @param source    A name/value dictionary containing the source values.
	 * @param target    An Object with properties to be set from the dictionary.
	 */
	public static void Map(Map<String, Object> source, Object target){
		Map(source, target, false);
	}

	/**
	 * Copies values from the source into the properties of the target.
	 * 
	 *        @remark The key names in the dictionary must match the property names on
	 * the target Object. Target properties may not be readonly or indexed.
	 * 
	 * @param source    A name/value dictionary containing the source values.
	 * @param target    An Object with properties to be set from the dictionary.
	 * @param ignoreList    A list of property names to ignore. These properties will
	 * not be set on the target Object.
	 */
	public static void Map(Map<String, Object> source, Object target, String[] ignoreList){
		Map(source, target, false, ignoreList);
	}

	/**
	 * Copies values from the source into the properties of the target.
	 * 
	 *        @remark The key names in the dictionary must match the property names on
	 * the target Object. Target properties may not be readonly or indexed.
	 * 
	 * @param source    A name/value dictionary containing the source values.
	 * @param target    An Object with properties to be set from the dictionary.
	 * @param suppressExceptions    If <see langword="true" />, any exceptions will be
	 * supressed.
	 * @param ignoreList    A list of property names to ignore. These properties will
	 * not be set on the target Object.
	 */
	public static void Map(Map<String, Object> source, Object target, boolean suppressExceptions, String... ignoreList){
		List<String> ignore = new ArrayList<String>();
		for(String item : ignoreList)
			ignore.add(item);
		        for (String propertyName : source.keySet())
		        {
		          if (!ignore.contains(propertyName))
		          {
		            try
		            {
		              SetPropertyValue(target, propertyName, source.get(propertyName));
		            }
		            catch (Exception ex)
		            {
		              if (!suppressExceptions)
		                throw new IllegalArgumentException(
		                  String.format("$1% ($2%)", 
		                  Resources.getPropertyCopyFailed(), propertyName), ex);
		            }
		          }
		        }
	}

	/**
	 * Copies values from the source into the properties of the target.
	 * 
	 *        @remark The property names and types of the source Object must match the
	 * property names and types on the target Object. Source properties may not be
	 * indexed. Target properties may not be readonly or indexed.
	 * 
	 * @param source    An Object containing the source values.
	 * @param target    An Object with properties to be set from the dictionary.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static void Map(Object source, Object target) throws SecurityException, NoSuchMethodException{
		Map(source, target, false);
	}

	/**
	 * Copies values from the source into the properties of the target.
	 * 
	 *        @remark The property names and types of the source Object must match the
	 * property names and types on the target Object. Source properties may not be
	 * indexed. Target properties may not be readonly or indexed.
	 * 
	 * @param source    An Object containing the source values.
	 * @param target    An Object with properties to be set from the dictionary.
	 * @param ignoreList    A list of property names to ignore. These properties will
	 * not be set on the target Object.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static void Map(Object source, Object target, String... ignoreList) throws SecurityException, NoSuchMethodException{
		Map(source, target, false, ignoreList);
	}

	/**
	 * Copies values from the source into the properties of the target.
	 * 
	 *        @remark  The property names and types of the source Object must match
	 * the property names and types on the target Object. Source properties may not be
	 * indexed. Target properties may not be readonly or indexed.
	 *         Properties to copy are determined based on the source Object. Any
	 * properties on the source Object marked with the <see
	 * cref="BrowsableAttribute"/> equal to false are ignored.
	 * 
	 * @param source    An Object containing the source values.
	 * @param target    An Object with properties to be set from the dictionary.
	 * @param suppressExceptions    If <see langword="true" />, any exceptions will be
	 * supressed.
	 * @param ignoreList    A list of property names to ignore. These properties will
	 * not be set on the target Object.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static void Map(Object source, Object target, boolean suppressExceptions, String... ignoreList) throws SecurityException, NoSuchMethodException{
		List<String> ignore = new ArrayList<String>();
		for(String item : ignoreList)
			ignore.add(item);
		        Method[] sourceProperties =
		          GetSourceProperties(source.getClass());
		        for (Method sourceProperty : sourceProperties)
		        {
		          String propertyName = sourceProperty.getName();
		          if (!ignore.contains(propertyName))
		          {
		            try
		            {
		              SetPropertyValue(
		                target, propertyName, 
		                sourceProperty.getDeclaringClass());
		            }
		            catch (Exception ex)
		            {
		              if (!suppressExceptions)
		                throw new IllegalArgumentException(
		                  String.format("$1% ($2%)", 
		                  Resources.getPropertyCopyFailed(), propertyName), ex);
		            }
		          }
		        }
	}

	/**
	 * Sets an Object's property with the specified value, coercing that value to the
	 * appropriate type if possible.
	 * 
	 * @param target    Object containing the property to set.
	 * @param propertyName    Name of the property to set.
	 * @param value    Value to set into the property.
	 * @throws Exception 
	 */
	public static void SetPropertyValue(Object target, String propertyName, Object value) throws Exception{
		Member propertyInfo =
				target.getClass().getMethod(propertyName);
//		          target.GetType().GetProperty(propertyName, BindingFlags.Public | BindingFlags.Instance | BindingFlags.FlattenHierarchy);
		        SetValue(target, propertyInfo, value);
	}

	/**
	 * Sets an Object's property or field with the specified value, coercing that
	 * value to the appropriate type if possible.
	 * 
	 * @param target    Object containing the member to set.
	 * @param memberInfo    MemberInfo Object for the member to set.
	 * @param value    Value to set into the member.
	 * @throws Exception 
	 */
	public static void SetValue(Object target, Member memberInfo, Object value) throws Exception{
		if (value != null)
		        {
		          Class<?> pType;
		          if (memberInfo.getClass() == Method.class)
		            pType = memberInfo.getClass();
		          else
		            pType = ((Field)memberInfo).getType();
		          Class<?> vType =
		            Utilities.getPropertyType(value.getClass()).getClass();
		          value = coerceValue(pType, vType, value);
		        }
			if (memberInfo.getClass() == Method.class)
		          ((Method)memberInfo).invoke(target, value);
		        else
		          ((Field)memberInfo).set(target, value);
	}

}