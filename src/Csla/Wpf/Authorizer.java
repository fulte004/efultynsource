package Csla.Wpf;
import Csla.Security.IAuthorizeReadWrite;

/**
 * Container for other UI controls that adds the ability for the contained
 * controls to change state based on the authorization information provided by the
 * data binding context.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:24 PM
 */
public class Authorizer extends DataDecoratorBase {

	private IAuthorizeReadWrite _source;
	/**
	 * Define attached DependencyProperty
	 */
	private static final DependencyProperty NotVisibleModeProperty = DependencyProperty.RegisterAttached(
	                "NotVisibleMode", 
	                typeof(VisibilityMode), 
	                typeof(Authorizer),
	                new FrameworkPropertyMetadata(VisibilityMode.Collapsed), 
	                new ValidateValueCallback(IsValidVisibilityMode));

	public Authorizer(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * This method is called when the data object to which the control is bound has
	 * changed.
	 */
	protected void DataObjectChanged(){
		Refresh();
	}

	/**
	 * Check the read and write status of the control based on the current user's
	 * authorization.
	 * 
	 * @param bnd    The Binding object.
	 * @param control    The control containing the binding.
	 * @param prop    The data bound DependencyProperty.
	 */
	protected void FoundBinding(Binding bnd, FrameworkElement control, DependencyProperty prop){
		SetRead(bnd, (UIElement)control, _source);
		        SetWrite(bnd, (UIElement)control, _source);
	}

	/**
	 * Gets the value controlling how controls bound to non-readable properties will
	 * be rendered.
	 * 
	 * @param obj
	 */
	public static VisibilityMode GetNotVisibleMode(DependencyObject obj){
		return (VisibilityMode)obj.GetValue(NotVisibleModeProperty);
	}

	/**
	 * Define method to validate the value
	 * 
	 * @param o
	 */
	private static bool IsValidVisibilityMode(object o){
		return (o is VisibilityMode);
	}

	/**
	 * Refresh authorization and update all controls.
	 */
	public void Refresh(){
		_source = DataObject as IAuthorizeReadWrite;
		        if (_source != null)
		          base.FindChildBindings();
	}

	/**
	 * Sets the value controlling how controls bound to non-readable properties will
	 * be rendered.
	 * 
	 * @param obj
	 * @param mode
	 */
	public static void SetNotVisibleMode(DependencyObject obj, VisibilityMode mode){
		obj.SetValue(NotVisibleModeProperty, mode);
	}

	/**
	 * 
	 * @param bnd
	 * @param ctl
	 * @param source
	 */
	private void SetRead(Binding bnd, UIElement ctl, IAuthorizeReadWrite source){
		bool canRead = source.CanReadProperty(bnd.Path.Path);
		        VisibilityMode visibilityMode = GetNotVisibleMode(ctl);
		        
		        if (canRead)
		          switch (visibilityMode)
		          {
		            case VisibilityMode.Collapsed:
		              if (ctl.Visibility == Visibility.Collapsed)
		                ctl.Visibility = Visibility.Visible;
		              break;
		            case VisibilityMode.Hidden:
		              if (ctl.Visibility == Visibility.Hidden)
		                ctl.Visibility = Visibility.Visible;
		              break;
		            default:
		              break;
		          }
		        else
		          switch (visibilityMode)
		          {
		            case VisibilityMode.Collapsed:
		              ctl.Visibility = Visibility.Collapsed;
		              break;
		            case VisibilityMode.Hidden:
		              ctl.Visibility = Visibility.Hidden;
		              break;
		            default:
		              break;
		          }
	}

	/**
	 * 
	 * @param bnd
	 * @param ctl
	 * @param source
	 */
	private void SetWrite(Binding bnd, UIElement ctl, IAuthorizeReadWrite source){
		bool canWrite = source.CanWriteProperty(bnd.Path.Path);
		        
		        // enable/disable writing of the value
		        PropertyInfo propertyInfo =
		          ctl.GetType().GetProperty("IsReadOnly",
		          BindingFlags.FlattenHierarchy |
		          BindingFlags.Instance |
		          BindingFlags.Public);
		        if (propertyInfo != null)
		        {
		          propertyInfo.SetValue(
		            ctl, !canWrite, new object[] { });
		        }
		        else
		        {
		          ctl.IsEnabled = canWrite;
		        }
	}

}