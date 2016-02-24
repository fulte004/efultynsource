package Csla;

public interface PropertyChangedListener {
	/**
	 * Represents the method that will handle the System.ComponentModel.INotifyPropertyChanged.PropertyChanged event raised when a 
	 * property is changed on a component.
	 * @param e A Csla.PropertyChangedEvent that contains the event data.
	 */
	public void onPropertyChanged(PropertyChangedEvent e);
}
