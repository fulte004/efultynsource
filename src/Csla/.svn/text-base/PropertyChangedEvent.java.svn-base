package Csla;

import java.util.EventObject;

public class PropertyChangedEvent extends EventObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _propertyName;

	/** Summary:
    *     Initializes a new instance of the System.ComponentModel.PropertyChangedEventArgs
    *     class.
    *
    * Parameters:
    *   propertyName:
    *     The name of the property that changed.
    */
    public PropertyChangedEvent(Object source, String propertyName) {
    	super(propertyName);
    	_propertyName = propertyName;
    }

    /** Summary:
    *     Gets the name of the property that changed.
    *
    * Returns:
    *     The name of the property that changed.
     * 
     */
    public String getPropertyName() {
    	return _propertyName;
    }
}
