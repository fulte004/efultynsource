package Csla.Wpf;

/**
 * Base class for creating WPF panel controls that react when the DataContext,
 * data object and data property values are changed.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:31 PM
 */
public class DataDecoratorBase extends Decorator {

	private object _dataObject;
	private bool _loaded;



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 */
	public DataDecoratorBase(){
		this.DataContextChanged += new DependencyPropertyChangedEventHandler(Panel_DataContextChanged);
		        this.Loaded += new RoutedEventHandler(Panel_Loaded);
	}

	/**
	 * This method is called if the data object is an IBindingList, and the
	 * ListChanged event was raised by the data object.
	 * 
	 * @param e
	 */
	protected void DataBindingListChanged(ListChangedEventArgs e){
		// may be overridden by subclass
	}

	/**
	 * Gets a reference to the current data object.
	 * 
	 *        @remark The DataContext may not be the data object. The DataContext may
	 * be a DataSourceProvider control. This property returns a reference to the
	 * actual
	 *        <b>data object</b>, not necessarily the DataContext itself.
	 */
	protected object DataObject(){
		get
		              {
		                return _dataObject;
		              }
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void DataObject_CollectionChanged(object sender, NotifyCollectionChangedEventArgs e){
		DataObservableCollectionChanged(e);
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void DataObject_ListChanged(object sender, ListChangedEventArgs e){
		DataBindingListChanged(e);
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void DataObject_PropertyChanged(object sender, PropertyChangedEventArgs e){
		DataPropertyChanged(e);
	}

	/**
	 * This method is called when the data object to which the control is bound has
	 * changed.
	 */
	protected void DataObjectChanged(){
		// may be overridden by subclass
	}

	/**
	 * This method is called if the data object is an INotifyCollectionChanged, and
	 * the CollectionChanged event was raised by the data object.
	 * 
	 * @param e
	 */
	protected void DataObservableCollectionChanged(NotifyCollectionChangedEventArgs e){

	}

	/**
	 * This method is called when a property of the data object to which the control
	 * is bound has changed.
	 * 
	 * @param e
	 */
	protected void DataPropertyChanged(PropertyChangedEventArgs e){
		// may be overridden by subclass
	}

	/**
	 * Handle case where the Data property of the DataContext (a DataSourceProvider)
	 * has changed.
	 * 
	 * @param sender
	 * @param e
	 */
	private void DataProvider_DataChanged(object sender, EventArgs e){
		UnHookPropertyChanged(_dataObject as INotifyPropertyChanged);
		        
		        _dataObject = ((DataSourceProvider)sender).Data as IDataErrorInfo;
		        
		        HookPropertyChanged(_dataObject as INotifyPropertyChanged);
		        
		        DataObjectChanged();
	}

	/**
	 * 
	 * @param visual
	 */
	private void FindBindings(Visual visual){
		for (int i = 0; i < VisualTreeHelper.GetChildrenCount(visual); i++)
		        {
		          Visual childVisual = (Visual)VisualTreeHelper.GetChild(visual, i);
		          MemberInfo[] sharedMembers = childVisual.GetType().GetMembers(
		            BindingFlags.Static | BindingFlags.Public | BindingFlags.FlattenHierarchy);
		          foreach (MemberInfo member in sharedMembers)
		          {
		            DependencyProperty prop = null;
		            if (member.MemberType == MemberTypes.Field)
		              prop = ((FieldInfo)member).GetValue(childVisual) as DependencyProperty;
		            else if (member.MemberType == MemberTypes.Property)
		              prop = ((PropertyInfo)member).GetValue(childVisual, null) as DependencyProperty;
		        
		            if (prop != null)
		            {
		              Binding bnd = BindingOperations.GetBinding(childVisual, prop);
		              if (bnd != null && bnd.RelativeSource == null && bnd.Path != null && string.IsNullOrEmpty(bnd.ElementName))
		                FoundBinding(bnd, (FrameworkElement)childVisual, prop);
		            }
		          }
		          FindBindings(childVisual);
		        }
	}

	/**
	 * Scans all child controls of this panel for object bindings, and calls
	 *        <see cref="FoundBinding"/> for each binding found.
	 */
	protected void FindChildBindings(){
		FindBindings(this);
	}

	/**
	 * Called by
	 *        <see cref="FindChildBindings"/> each time an object binding is found.
	 * 
	 * @param bnd    The Binding object.
	 * @param control    The control containing the binding.
	 * @param prop    The data bound DependencyProperty.
	 */
	protected void FoundBinding(Binding bnd, FrameworkElement control, DependencyProperty prop){

	}

	/**
	 * 
	 * @param dataContext
	 */
	private object GetDataObject(object dataContext){
		DataSourceProvider provider = dataContext as DataSourceProvider;
		        if (provider != null)
		          return provider.Data;
		        else
		          return dataContext;
	}

	/**
	 * 
	 * @param newContext
	 */
	private void HookBindingListChanged(IBindingList newContext){
		if (newContext != null)
		          newContext.ListChanged += new ListChangedEventHandler(DataObject_ListChanged);
	}

	/**
	 * 
	 * @param newValue
	 */
	private void HookDataContextEvents(object newValue){
		// hook any new event
		        object newContext = null;
		        
		        DataSourceProvider provider = newValue as DataSourceProvider;
		        if (provider == null)
		        {
		          newContext = newValue;
		        }
		        else
		        {
		          provider.DataChanged += new EventHandler(DataProvider_DataChanged);
		          newContext = provider.Data;
		        }
		        HookPropertyChanged(newContext as INotifyPropertyChanged);
		        INotifyCollectionChanged observable = newContext as INotifyCollectionChanged;
		        if (observable != null)
		          HookObservableListChanged(observable);
		        else
		          HookBindingListChanged(newContext as IBindingList);
	}

	/**
	 * 
	 * @param newContext
	 */
	private void HookObservableListChanged(INotifyCollectionChanged newContext){
		if (newContext != null)
		          newContext.CollectionChanged +=
		            new NotifyCollectionChangedEventHandler(DataObject_CollectionChanged);
	}

	/**
	 * 
	 * @param newContext
	 */
	private void HookPropertyChanged(INotifyPropertyChanged newContext){
		if (newContext != null)
		          newContext.PropertyChanged += new PropertyChangedEventHandler(DataObject_PropertyChanged);
	}

	/**
	 * Handle case where the DataContext for the control has changed.
	 * 
	 * @param sender
	 * @param e
	 */
	private void Panel_DataContextChanged(object sender, DependencyPropertyChangedEventArgs e){
		UnHookDataContextEvents(e.OldValue);
		        
		        // store a ref to the data object
		        _dataObject = GetDataObject(e.NewValue);
		        
		        HookDataContextEvents(e.NewValue);
		        
		        if (_loaded)
		          DataObjectChanged();
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void Panel_Loaded(object sender, RoutedEventArgs e){
		_loaded = true;
		        if (_dataObject != null)
		          DataObjectChanged();
	}

	/**
	 * 
	 * @param oldContext
	 */
	private void UnHookBindingListChanged(IBindingList oldContext){
		if (oldContext != null)
		          oldContext.ListChanged -= new ListChangedEventHandler(DataObject_ListChanged);
	}

	/**
	 * 
	 * @param oldValue
	 */
	private void UnHookDataContextEvents(object oldValue){
		// unhook any old event handling
		        object oldContext = null;
		        
		        DataSourceProvider provider = oldValue as DataSourceProvider;
		        if (provider == null)
		        {
		          oldContext = oldValue;
		        }
		        else
		        {
		          provider.DataChanged -= new EventHandler(DataProvider_DataChanged);
		          oldContext = provider.Data;
		        }
		        UnHookPropertyChanged(oldContext as INotifyPropertyChanged);
		        INotifyCollectionChanged observable = oldContext as INotifyCollectionChanged;
		        if (observable != null)
		          UnHookObservableListChanged(observable);
		        else
		          UnHookBindingListChanged(oldContext as IBindingList);
	}

	/**
	 * 
	 * @param oldContext
	 */
	private void UnHookObservableListChanged(INotifyCollectionChanged oldContext){
		if (oldContext != null)
		          oldContext.CollectionChanged -=
		            new NotifyCollectionChangedEventHandler(DataObject_CollectionChanged);
	}

	/**
	 * 
	 * @param oldContext
	 */
	private void UnHookPropertyChanged(INotifyPropertyChanged oldContext){
		if (oldContext != null)
		          oldContext.PropertyChanged -= new PropertyChangedEventHandler(DataObject_PropertyChanged);
	}

}