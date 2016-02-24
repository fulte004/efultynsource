package Csla.Wpf;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for other UI controls that adds the ability for the contained
 * controls to change appearance based on the error information provided by the
 * data binding context.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:49 PM
 */
public class Validator extends DataDecoratorBase {

	/**
	 * Contains details about each binding that are required to handle the validation
	 * processing.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:49 PM
	 */
	private class BindingInfo {

		private Binding _bindingObject;
		private FrameworkElement _element;
		private DependencyProperty _property;

		public BindingInfo(){

		}

		public void finalize() throws Throwable {

		}

		/**
		 * 
		 * @param binding
		 * @param element
		 * @param property
		 */
		public BindingInfo(Binding binding, FrameworkElement element, DependencyProperty property){
			_bindingObject = binding;
			          _element = element;
			          _property = property;
		}

		public Binding getBindingObject(){
			 { return _bindingObject; }
		}
		public void setBindingObject(Binding value){
			           { _bindingObject = value; }
		}

		public FrameworkElement getElement(){
			 { return _element; }
		}
		public void setElement(FrameworkElement value){
			           { _element = value; }
		}

		public DependencyProperty getProperty(){
			 { return _property; }
		}
		public void setProperty(DependencyProperty value){
			           { _property = value; }
		}

	}

	private List<BindingInfo> _bindings = new ArrayList<BindingInfo>();
	private boolean _haveRecentChange;



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates a new instance of the Object.
	 */
	public Validator(){
		this.Loaded += new RoutedEventHandler(ValidationPanel_Loaded);
	}

	/**
	 * This method is called if the data Object is an IBindingList, and the
	 * ListChanged event was raised by the data Object.
	 * 
	 * @param e
	 */
	protected void DataBindingListChanged(ListChangedEventArgs e){
		Refresh();
	}

	/**
	 * This method is called when the data Object to which the control is bound has
	 * changed.
	 */
	protected void DataObjectChanged(){
		ReloadBindings();
	}

	/**
	 * This method is called if the data Object is an INotifyCollectionChanged, and
	 * the CollectionChanged event was raised by the data Object.
	 * 
	 * @param e
	 */
	protected void DataObservableCollectionChanged(System.Collections.Specialized.NotifyCollectionChangedEventArgs e){
		Refresh();
	}

	/**
	 * This method is called when a property of the data Object to which the control
	 * is bound has changed.
	 * 
	 * @param e
	 */
	protected void DataPropertyChanged(PropertyChangedEventArgs e){
		// note that there's been a change, so the 
		        // next scan will perform validation
		        _haveRecentChange = true;
	}

	private void ErrorScan(){
		IDataErrorInfo source = (IDataErrorInfo)DataObject;
		        if (_haveRecentChange && source != null)
		        {
		          _haveRecentChange = false;
		          if (_bindings.size() == 0)
		            ReloadBindings(false);
		        
		          if (source != null && _bindings.size() > 0)
		            for (BindingInfo item : _bindings)
		            {
		              BindingExpression expression = item.Element.GetBindingExpression(item.Property);
		              if (expression != null)
		              {
		                string text = source[item.BindingObject.Path.Path];
		                if (string.IsNullOrEmpty(text))
		                  System.Windows.Controls.Validation.ClearInvalid(expression);
		                else
		                {
		                  ValidationError error = 
		                    new ValidationError(new ExceptionValidationRule(), expression, text, null);
		                  System.Windows.Controls.Validation.MarkInvalid(expression, error);
		                }
		              }
		            }
		        }
	}

	/**
	 * Store the binding for use in validation processing.
	 * 
	 * @param bnd    The Binding Object.
	 * @param control    The control containing the binding.
	 * @param prop    The data bound DependencyProperty.
	 */
	protected void FoundBinding(Binding bnd, FrameworkElement control, DependencyProperty prop){
		_bindings.add(new BindingInfo(bnd, control, prop));
		        control.GotFocus += new RoutedEventHandler(ValidationPanel_GotFocus);
	}

	/**
	 * Force the panel to refresh all validation error status information for all
	 * controls it contains.
	 */
	public void Refresh(){
		_haveRecentChange = true;
		        ErrorScan();
	}

	/**
	 * Reload all the binding information for the controls contained within the
	 * ErrorDisplayContainer, and refresh the validation status.
	 */
	public void ReloadBindings(){
		ReloadBindings(true);
	}

	/**
	 * 
	 * @param refreshAfter
	 */
	private void ReloadBindings(boolean refreshAfter){
		_bindings.clear();
		        super.FindChildBindings();
		        if (refreshAfter)
		          Refresh();
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void ValidationPanel_GotFocus(Object sender, RoutedEventArgs e){
		ErrorScan();
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void ValidationPanel_Loaded(Object sender, RoutedEventArgs e){
		((FrameworkElement)this).LostFocus += new RoutedEventHandler(ValidationPanel_LostFocus);
		        _haveRecentChange = true;
		        //ErrorScan();
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void ValidationPanel_LostFocus(Object sender, RoutedEventArgs e){
		ErrorScan();
	}

}