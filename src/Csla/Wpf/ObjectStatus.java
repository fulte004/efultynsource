package Csla.Wpf;

/**
 * Container for other UI controls that exposes various status values from the
 * CSLA .NET business object acting as DataContext.
 * 
 *      @remark This control provides access to the IsDirty, IsNew, IsDeleted,
 * IsValid and IsSavable properties of a business object. The purpose behind this
 * control is to expose those properties in a way that supports WFP data binding
 * against those values.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:40 PM
 */
public class ObjectStatus extends DataDecoratorBase {

	private static final DependencyProperty IsDeletedProperty = DependencyProperty.Register("IsDeleted", typeof(bool), typeof(ObjectStatus), new FrameworkPropertyMetadata(false), null);
	private static final DependencyProperty IsDirtyProperty = DependencyProperty.Register("IsDirty", typeof(bool), typeof(ObjectStatus), new FrameworkPropertyMetadata(false), null);
	private static final DependencyProperty IsNewProperty = DependencyProperty.Register("IsNew", typeof(bool), typeof(ObjectStatus), new FrameworkPropertyMetadata(false), null);
	private static final DependencyProperty IsSavableProperty = DependencyProperty.Register("IsSavable", typeof(bool), typeof(ObjectStatus), new FrameworkPropertyMetadata(false), null);
	private static final DependencyProperty IsValidProperty = DependencyProperty.Register("IsValid", typeof(bool), typeof(ObjectStatus), new FrameworkPropertyMetadata(false), null);

	public ObjectStatus(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * This method is called if the data object is an IBindingList, and the
	 * ListChanged event was raised by the data object.
	 * 
	 * @param e
	 */
	protected void DataBindingListChanged(ListChangedEventArgs e){
		Refresh();
	}

	/**
	 * This method is called when the data object to which the control is bound has
	 * changed.
	 */
	protected void DataObjectChanged(){
		Refresh();
	}

	/**
	 * This method is called if the data object is an INotifyCollectionChanged, and
	 * the CollectionChanged event was raised by the data object.
	 * 
	 * @param e
	 */
	protected void DataObservableCollectionChanged(System.Collections.Specialized.NotifyCollectionChangedEventArgs e){
		Refresh();
	}

	/**
	 * This method is called when a property of the data object to which the control
	 * is bound has changed.
	 * 
	 * @param e
	 */
	protected void DataPropertyChanged(PropertyChangedEventArgs e){
		Refresh();
	}

	/**
	 * Exposes the IsDeleted property of the DataContext business object.
	 */
	public bool IsDeleted(){
		get { return (bool)base.GetValue(IsDeletedProperty); }
		        set 
		        {
		          bool old = IsDeleted;
		          base.SetValue(IsDeletedProperty, value);
		          OnPropertyChanged(
		            new DependencyPropertyChangedEventArgs(IsDeletedProperty, old, value));
		        }
	}

	/**
	 * Exposes the IsDirty property of the DataContext business object.
	 */
	public bool IsDirty(){
		get { return (bool)base.GetValue(IsDirtyProperty); }
		        set
		        {
		          bool old = IsDirty;
		          base.SetValue(IsDirtyProperty, value);
		          if (old != value)
		            OnPropertyChanged(
		              new DependencyPropertyChangedEventArgs(IsDirtyProperty, old, value));
		        }
	}

	/**
	 * Exposes the IsNew property of the DataContext business object.
	 */
	public bool IsNew(){
		get { return (bool)base.GetValue(IsNewProperty); }
		        set
		        {
		          bool old = IsNew;
		          base.SetValue(IsNewProperty, value);
		          if (old != value)
		            OnPropertyChanged(
		              new DependencyPropertyChangedEventArgs(IsNewProperty, old, value));
		        }
	}

	/**
	 * Exposes the IsSavable property of the DataContext business object.
	 */
	public bool IsSavable(){
		get { return (bool)base.GetValue(IsSavableProperty); }
		        set
		        {
		          bool old = IsSavable;
		          base.SetValue(IsSavableProperty, value);
		          if (old != value)
		            OnPropertyChanged(
		              new DependencyPropertyChangedEventArgs(IsSavableProperty, old, value));
		        }
	}

	/**
	 * Exposes the IsValid property of the DataContext business object.
	 */
	public bool IsValid(){
		get { return (bool)base.GetValue(IsValidProperty); }
		        set
		        {
		          bool old = IsValid;
		          base.SetValue(IsValidProperty, value);
		          if (old != value)
		            OnPropertyChanged(
		              new DependencyPropertyChangedEventArgs(IsValidProperty, old, value));
		        }
	}

	/**
	 * Refreshes the control's property values to reflect the values of the underlying
	 * business object.
	 */
	public void Refresh(){
		IEditableBusinessObject source = DataObject as IEditableBusinessObject;
		        if (source != null)
		        {
		          if (IsDeleted != source.IsDeleted)
		            IsDeleted = source.IsDeleted;
		          if (IsDirty != source.IsDirty)
		            IsDirty = source.IsDirty;
		          if (IsNew != source.IsNew)
		            IsNew = source.IsNew;
		          if (IsSavable != source.IsSavable)
		            IsSavable = source.IsSavable;
		          if (IsValid != source.IsValid)
		            IsValid = source.IsValid;
		        }
		        else
		        {
		          IEditableCollection sourceList = DataObject as IEditableCollection;
		          if (sourceList != null)
		          {
		            if (IsDirty != sourceList.IsDirty)
		              IsDirty = sourceList.IsDirty;
		            if (IsValid != sourceList.IsValid)
		              IsValid = sourceList.IsValid;
		            if (IsSavable != sourceList.IsSavable)
		              IsSavable = sourceList.IsSavable;
		            IsDeleted = false;
		            IsNew = false;
		          }
		        }
	}

}