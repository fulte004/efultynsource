package Csla.Windows;

/**
 * Windows Forms extender control that automatically enables and disables detail
 * form controls based on the authorization settings from a CSLA .NET business
 * object.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:41 PM
 */
public class ReadWriteAuthorization extends Component implements IExtenderProvider {

	private Dictionary<Control, bool> _sources = new Dictionary<Control, bool>();

	public ReadWriteAuthorization(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param container    The container of the control.
	 */
	public ReadWriteAuthorization(IContainer container){
		container.Add(this);
	}

	/**
	 * 
	 * @param control
	 */
	private void ApplyAuthorizationRules(Control control){
		foreach (Binding binding in control.DataBindings)
		        {
		          // get the BindingSource if appropriate
		          if (binding.DataSource is BindingSource)
		          {
		            BindingSource bs =
		              (BindingSource)binding.DataSource;
		            // get the BusinessObject if appropriate
		            Csla.Security.IAuthorizeReadWrite ds = 
		              bs.Current as Csla.Security.IAuthorizeReadWrite;
		            if (ds != null)
		            {
		              // get the object property name
		              string propertyName =
		                binding.BindingMemberInfo.BindingField;
		        
		              ApplyReadRules(
		                control, binding,
		                ds.CanReadProperty(propertyName));
		              ApplyWriteRules(
		                control, binding,
		                ds.CanWriteProperty(propertyName));
		            }
		          }
		        }
	}

	/**
	 * 
	 * @param ctl
	 * @param binding
	 * @param canRead
	 */
	private void ApplyReadRules(Control ctl, Binding binding, bool canRead){
		// enable/disable reading of the value
		        if (canRead)
		        {
		          bool couldRead = ctl.Enabled;
		          ctl.Enabled = true;
		          binding.Format -= 
		            new ConvertEventHandler(ReturnEmpty);
		          if (!couldRead) binding.ReadValue();
		        }
		        else
		        {
		          ctl.Enabled = false;
		          binding.Format += 
		            new ConvertEventHandler(ReturnEmpty);
		        
		          // clear the value displayed by the control
		          PropertyInfo propertyInfo = 
		            ctl.GetType().GetProperty(binding.PropertyName,
		            BindingFlags.FlattenHierarchy |
		            BindingFlags.Instance |
		            BindingFlags.Public);
		          if (propertyInfo != null)
		          {
		            propertyInfo.SetValue(ctl, 
		              GetEmptyValue(
		                Utilities.GetPropertyType(
		                  propertyInfo.PropertyType)), 
		                new object[] { });
		          }
		        }
	}

	/**
	 * 
	 * @param ctl
	 * @param binding
	 * @param canWrite
	 */
	private void ApplyWriteRules(Control ctl, Binding binding, bool canWrite){
		if (ctl is Label) return;
		        
		        // enable/disable writing of the value
		        PropertyInfo propertyInfo =
		          ctl.GetType().GetProperty("ReadOnly",
		          BindingFlags.FlattenHierarchy |
		          BindingFlags.Instance |
		          BindingFlags.Public);
		        if (propertyInfo != null)
		        {
		          bool couldWrite = 
		            (!(bool)propertyInfo.GetValue(
		            ctl, new object[] { }));
		          propertyInfo.SetValue(
		            ctl, !canWrite, new object[] { });
		          if ((!couldWrite) && (canWrite))
		            binding.ReadValue();
		        }
		        else
		        {
		          bool couldWrite = ctl.Enabled;
		          ctl.Enabled = canWrite;
		          if ((!couldWrite) && (canWrite))
		            binding.ReadValue();
		        }
	}

	/**
	 * Gets a value indicating whether the extender control can extend the specified
	 * control.
	 * 
	 *        @remark Any control implementing either a ReadOnly property or Enabled
	 * property can be extended.
	 * 
	 * @param extendee    The control to be extended.
	 */
	public bool CanExtend(object extendee){
		if (IsPropertyImplemented(extendee, "ReadOnly") 
		          || IsPropertyImplemented(extendee, "Enabled"))
		          return true;
		        else
		          return false;
	}

	/**
	 * Gets the custom ApplyAuthorization extender property added to extended controls.
	 * 
	 * 
	 * @param source    Control being extended.
	 */
	public bool GetApplyAuthorization(Control source){
		if (_sources.ContainsKey(source))
		          return _sources[source];
		        else
		          return false;
	}

	/**
	 * 
	 * @param desiredType
	 */
	private object GetEmptyValue(Type desiredType){
		object result = null;
		        if (desiredType.IsValueType)
		          result = Activator.CreateInstance(desiredType);
		        return result;
	}

	/**
	 * 
	 * @param obj
	 * @param propertyName
	 */
	private static bool IsPropertyImplemented(object obj, string propertyName){
		if (obj.GetType().GetProperty(propertyName,
		          BindingFlags.FlattenHierarchy |
		          BindingFlags.Instance |
		          BindingFlags.Public) != null)
		          return true;
		        else
		          return false;
	}

	/**
	 * Causes the ReadWriteAuthorization control to apply authorization rules from the
	 * business object to all extended controls on the form.
	 * 
	 *        @remark Call this method to refresh the display of detail controls on
	 * the form any time the authorization rules may have changed. Examples include:
	 * after a user logs in or out, and after an object has been updated, inserted,
	 * deleted or retrieved from the database.
	 */
	public void ResetControlAuthorization(){
		foreach (KeyValuePair<Control, bool> item in _sources)
		        {
		          if (item.Value)
		          {
		            // apply authorization rules
		            ApplyAuthorizationRules(item.Key);
		          }
		        }
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void ReturnEmpty(object sender, ConvertEventArgs e){
		e.Value = GetEmptyValue(e.DesiredType);
	}

	/**
	 * Sets the custom ApplyAuthorization extender property added to extended controls.
	 * 
	 * 
	 * @param source    Control being extended.
	 * @param value    New value of property.
	 */
	public void SetApplyAuthorization(Control source, bool value){
		if (_sources.ContainsKey(source))
		          _sources[source] = value;
		        else
		          _sources.Add(source, value);
	}

}