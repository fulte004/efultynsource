package Csla.Web.Design;
import Csla.Web.CslaDataSource;

/**
 * Implements designer support for CslaDataSource.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:31 PM
 */
public class CslaDataSourceDesigner extends DataSourceDesigner {

	private DataSourceControl _control = null;
	private CslaDesignerDataSourceView _view = null;

	public CslaDataSourceDesigner(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Get a value indicating whether the control can be resized.
	 */
	public bool AllowResize(){
		get
		                {
		                  return false;
		                }
	}

	/**
	 * Get a value indicating whether this control supports design time configuration.
	 */
	public bool CanConfigure(){
		get
		                {
		                  return true;
		                }
	}

	/**
	 * Get a value indicating whether the control can refresh its schema.
	 */
	public bool CanRefreshSchema(){
		get
		                {
		                  return true;
		                }
	}

	/**
	 * Invoke the design time configuration support provided by the control.
	 */
	public void Configure(){
		InvokeTransactedChange(_control, ConfigureCallback, null, "ConfigureDataSource");
	}

	/**
	 * 
	 * @param context
	 */
	private bool ConfigureCallback(object context){
		bool result = false;
		          
		          string oldTypeName;
		          if (string.IsNullOrEmpty(((CslaDataSource)DataSourceControl).TypeAssemblyName))
		            oldTypeName = ((CslaDataSource)DataSourceControl).TypeName;
		          else
		            oldTypeName = string.Format("{0}, {1}", 
		              ((CslaDataSource)DataSourceControl).TypeName, ((CslaDataSource)DataSourceControl).TypeAssemblyName);
		          
		          IUIService uiService = (IUIService)_control.Site.GetService(typeof(IUIService));
		          CslaDataSourceConfiguration cfg = new CslaDataSourceConfiguration(_control, oldTypeName);
		          if (uiService.ShowDialog(cfg) == System.Windows.Forms.DialogResult.OK)
		          {
		            SuppressDataSourceEvents();
		            try
		            {
		              ((CslaDataSource)DataSourceControl).TypeAssemblyName = string.Empty;
		              ((CslaDataSource)DataSourceControl).TypeName = cfg.TypeName;
		              OnDataSourceChanged(EventArgs.Empty);
		              result = true;
		            }
		            finally
		            {
		              ResumeDataSourceEvents();
		            }
		          }
		          cfg.Dispose();
		          return result;
	}

	/**
	 * Get a reference to the CslaDataSource control being designed.
	 */
	internal CslaDataSource DataSourceControl(){
		get
		                {
		                  return (CslaDataSource)_control;
		                }
	}

	/**
	 * Returns the default view for this designer.
	 * 
	 *          @returns
	 *          @remark This designer supports only a "Default" view.
	 * 
	 * @param viewName    Ignored
	 */
	public DesignerDataSourceView GetView(string viewName){
		if (_view == null)
		          {
		            _view = new CslaDesignerDataSourceView(this, "Default");
		          }
		          return _view;
	}

	/**
	 * Return a list of available views.
	 * 
	 *          @remark This designer supports only a "Default" view.
	 */
	public string[] GetViewNames(){
		return new string[] { "Default" };
	}

	/**
	 * Initialize the designer component.
	 * 
	 * @param component    The CslaDataSource control to be designed.
	 */
	public void Initialize(IComponent component){
		base.Initialize(component);
		          _control = (DataSourceControl)component;
	}

	/**
	 * Refreshes the schema for the data.
	 * 
	 *          @remark
	 * 
	 * @param preferSilent
	 */
	public void RefreshSchema(bool preferSilent){
		this.OnSchemaRefreshed(EventArgs.Empty);
	}

	internal System.ComponentModel.ISite Site(){
		get
		                {
		                  return _control.Site;
		                }
	}

}