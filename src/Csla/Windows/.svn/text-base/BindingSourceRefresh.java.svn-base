package Csla.Windows;

/**
 * Windows Forms extender control that resolves the data refresh issue with data
 * bound detail controls as discussed in Chapter 5.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:25 PM
 */
public class BindingSourceRefresh extends Component implements IExtenderProvider {

	private Dictionary<BindingSource, bool> _sources = new Dictionary<BindingSource, bool>();

	public BindingSourceRefresh(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param container    Container of the control.
	 */
	public BindingSourceRefresh(IContainer container){
		container.Add(this);
	}

	/**
	 * Gets a value indicating whether the extender control can extend the specified
	 * control.
	 * 
	 *        @remark This control only extends <see cref="BindingSource"/> controls.
	 * 
	 * @param extendee    The control to be extended.
	 */
	public bool CanExtend(object extendee){
		if (extendee is BindingSource)
		          return true;
		        return false;
	}

	/**
	 * Gets the value of the custom ReadValuesOnChange extender property added to
	 * extended controls.
	 * 
	 * @param source    Control being extended.
	 */
	public bool GetReadValuesOnChange(BindingSource source){
		if (_sources.ContainsKey(source))
		          return _sources[source];
		        return false;
	}

	/**
	 * Sets the value of the custom ReadValuesOnChange extender property added to
	 * extended controls.
	 * 
	 *        @remark
	 * 
	 * @param source    Control being extended.
	 * @param value    New value of property.
	 */
	public void SetReadValuesOnChange(BindingSource source, bool value){
		if (_sources.ContainsKey(source))
		          _sources[source] = value;
		        else
		          _sources.Add(source, value);
		        if (value)
		        {
		          // hook
		          source.BindingComplete += 
		            new BindingCompleteEventHandler(Source_BindingComplete);
		        }
		        else
		        {
		          // unhook
		          source.BindingComplete -= 
		            new BindingCompleteEventHandler(Source_BindingComplete);
		        }
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void Source_BindingComplete(object sender, BindingCompleteEventArgs e){
		e.Binding.ReadValue();
	}

}