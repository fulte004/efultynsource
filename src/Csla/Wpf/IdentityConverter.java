package Csla.Wpf;

/**
 * Provides the functionality of a WPF value converter without affecting the value
 * as it flows to and from the UI.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:36 PM
 */
public class IdentityConverter extends IValueConverter {

	public IdentityConverter(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Returns the unchanged value.
	 * 
	 * @param value    Value to be converted.
	 * @param targetType    Desired value type.
	 * @param parameter    Conversion parameter.
	 * @param culture    Conversion culture.
	 */
	public object Convert(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture){
		return value;
	}

	/**
	 * Returns the unchanged value.
	 * 
	 * @param value    Value to be converted.
	 * @param targetType    Desired value type.
	 * @param parameter    Conversion parameter.
	 * @param culture    Conversion culture.
	 */
	public object ConvertBack(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture){
		return value;
	}

}