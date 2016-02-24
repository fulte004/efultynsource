package Csla.ComponentModel;

import java.lang.reflect.Method;
import java.util.EventObject;

/**
 * @author Eric
 * @version 1.0
 * @created 06-Jan-2010 9:48:29 PM
 */
public class ListChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListChangedType _listChangedType;
	private Integer _newIndex;
	private Integer _oldIndex;
	private Method _propDesc;
	
	/**
	 * 
	 * @param listChangedType
	 * @param newIndex
	 */
	public ListChangedEvent(Object source, ListChangedType listChangedType, Integer newIndex){
		super(source);
		_listChangedType = listChangedType;
		_newIndex = newIndex;
	}

	/**
	 * 
	 * @param listChangedType
	 * @param newIndex
	 * @param method
	 */
	public ListChangedEvent(Object source, ListChangedType listChangedType, Integer newIndex, Method method){
		super(source);
		_listChangedType = listChangedType;
		_newIndex = newIndex;
		_propDesc = method;
	}

	/**
	 * 
	 * @param listChangedType
	 * @param propDesc
	 */
	public ListChangedEvent(Object source, ListChangedType listChangedType, Method propDesc){
		super(source);
		_listChangedType = listChangedType;
		_propDesc = propDesc;
	}

	/**
	 * 
	 * @param listChangedType
	 * @param newIndex
	 * @param oldIndex
	 */
	public ListChangedEvent(Object source, ListChangedType listChangedType, Integer newIndex, Integer oldIndex){
		super(source);
		_listChangedType = listChangedType;
		_newIndex = newIndex;
		_oldIndex = oldIndex;
	}

	public ListChangedType getListChangedType(){
		return _listChangedType;
	}

	public Integer getNewIndex(){
		return _newIndex;
	}

	public Integer getOldIndex(){
		return _oldIndex;
	}

	public Method getPropertyDescriptor(){
		return _propDesc;
	}

}