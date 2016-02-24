package Csla.Core;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import Csla.NotifyPropertyChanged;

/**
 * This class implements INotifyPropertyChanged in a serialization-safe manner.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:25 PM
 */
public abstract class BindableBase extends NotifyPropertyChanged {
	
	
	private PropertyChangeSupport _nonSerializableHandlers;
	private PropertyChangeSupport _serializableHandlers;

	/**
	 * Creates an instance of the object.
	 */
	protected BindableBase(){

	}

	/**
	 * Call this method to raise the PropertyChanged event for all object properties.
	 * 
	 *        @remark This method is for backward compatibility with CSLA .NET 1.x.
	 */
	protected void onIsDirtyChanged(){
		onUnknownPropertyChanged();
	}

	/**
	 * Call this method to raise the PropertyChanged event for a specific property.
	 * 
	 *        @remark This method may be called by properties in the business class to
	 * indicate the change in a specific property.
	 * 
	 * @param propertyName    Name of the property that has changed.
	 */
	protected void onPropertyChanged(String propertyName, Object oldValue, Object newValue){
		PropertyChangeEvent e = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		if (_nonSerializableHandlers != null)
			_nonSerializableHandlers.firePropertyChange(e);
		if (_serializableHandlers != null)
			_serializableHandlers.firePropertyChange(e);
	}

	/**
	 * Call this method to raise the PropertyChanged event for all object properties.
	 * 
	 *        @remark This method is automatically called by MarkDirty. It actually
	 * raises PropertyChanged for an empty String, which tells data binding to refresh
	 * all properties.
	 */
	protected void onUnknownPropertyChanged(){
		onPropertyChanged("", null, null);
	}

	/**
	 * Implements a serialization-safe PropertyChanged event.
	 */
	public PropertyChangeSupport PropertyChanged(){
		return null;
	}

}