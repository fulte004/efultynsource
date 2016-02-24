package Csla.Wpf;

/**
 * Wraps and creates a CSLA .NET-style object that you can use as a binding source.
 * 
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:30 PM
 */
public class CslaDataProvider extends DataSourceProvider {

	/**
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:30 PM
	 */
	private class QueryRequest {

		private string _factoryMethod;
		private ObservableCollection<object> _factoryParameters;
		private bool _manageLifetime;
		private Type _objectType;

		public QueryRequest(){

		}

		public void finalize() throws Throwable {

		}

		public string FactoryMethod(){
			get { return _factoryMethod; }
			          set { _factoryMethod = value; }
		}

		public ObservableCollection<object> FactoryParameters(){
			get { return _factoryParameters; }
			          set { _factoryParameters = 
			            new ObservableCollection<object>(new List<object>(value)); }
		}

		public bool ManageObjectLifetime(){
			get { return _manageLifetime; }
			          set { _manageLifetime = value; }
		}

		public Type ObjectType(){
			get { return _objectType; }
			          set { _objectType = value; }
		}

	}

	private CslaDataProviderCommandManager _commandManager;
	private string _factoryMethod = string.Empty;
	private ObservableCollection<object> _factoryParameters;
	private bool _firstRun = true;
	private bool _isAsynchronous;
	private bool _manageLifetime;
	private Type _objectType = null;



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object.
	 */
	public CslaDataProvider(){
		_commandManager = new CslaDataProviderCommandManager(this);
		        _factoryParameters = new ObservableCollection<object>();
		        _factoryParameters.CollectionChanged += 
		          new System.Collections.Specialized.NotifyCollectionChangedEventHandler(_factoryParameters_CollectionChanged);
	}

	/**
	 * 
	 * @param sender
	 * @param e
	 */
	private void _factoryParameters_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e){
		BeginQuery();
	}

	/**
	 * Adds a new item to the object if the object implements IBindingList and
	 * AllowNew is true.
	 */
	public void AddNew(){
		// only do something if the object implements
		        // IBindingList
		        IBindingList list = this.Data as IBindingList;
		        if (list != null && list.AllowNew)
		          list.AddNew();
	}

	/**
	 * Overridden. Starts to create the requested object, either immediately or on a
	 * background thread, based on the value of the IsAsynchronous property.
	 */
	protected void BeginQuery(){
		if (this.IsRefreshDeferred)
		          return;
		        
		        if (_firstRun)
		        {
		          _firstRun = false;
		          if (!IsInitialLoadEnabled)
		            return;
		        }
		        
		        QueryRequest request = new QueryRequest();
		        request.ObjectType = _objectType;
		        request.FactoryMethod = _factoryMethod;
		        request.FactoryParameters = _factoryParameters;
		        request.ManageObjectLifetime = _manageLifetime;
		        
		        if (IsAsynchronous)
		          System.Threading.ThreadPool.QueueUserWorkItem(DoQuery, request);
		        else
		          DoQuery(request);
	}

	/**
	 * Cancels changes to the business object, returning it to its previous state.
	 * 
	 *        @remark This metod does nothing unless ManageLifetime is set to true and
	 * the object supports n-level undo.
	 */
	public void Cancel(){
		Csla.Core.ISupportUndo undo = this.Data as Csla.Core.ISupportUndo;
		        if (undo != null && _manageLifetime)
		        {
		          undo.CancelEdit();
		          undo.BeginEdit();
		        }
	}

	/**
	 * Gets an object that can be used to execute Save and Undo commands on this
	 * CslaDataProvider through XAML command bindings.
	 */
	public CslaDataProviderCommandManager CommandManager(){
		get
		              {
		                return _commandManager;
		              }
	}

	/**
	 * 
	 * @param state
	 */
	private void DoQuery(object state){
		QueryRequest request = (QueryRequest)state;
		        object result = null;
		        Exception exceptionResult = null;
		        object[] parameters = new List<object>(request.FactoryParameters).ToArray();
		        
		        try
		        {
		          // get factory method info
		          BindingFlags flags = BindingFlags.Static | BindingFlags.Public | BindingFlags.FlattenHierarchy;
		          MethodInfo factory = request.ObjectType.GetMethod(
		            request.FactoryMethod, flags, null, 
		            MethodCaller.GetParameterTypes(parameters), null);
		        
		          if (factory == null)
		          {
		            // strongly typed factory couldn't be found
		            // so find one with the correct number of
		            // parameters 
		            int parameterCount = parameters.Length;
		            MethodInfo[] methods = request.ObjectType.GetMethods(flags);
		            foreach (MethodInfo method in methods)
		              if (method.Name == request.FactoryMethod && method.GetParameters().Length == parameterCount)
		              {
		                factory = method;
		                break;
		              }
		          }
		        
		          if (factory == null)
		          {
		            // no matching factory could be found
		            // so throw exception
		            throw new InvalidOperationException(
		              string.Format(Resources.NoSuchFactoryMethod, request.FactoryMethod));
		          }
		        
		          // invoke factory method
		          try
		          {
		            result = factory.Invoke(null, parameters);
		          }
		          catch (Csla.DataPortalException ex)
		          {
		            exceptionResult = ex.BusinessException;
		          }
		          catch (Exception ex)
		          {
		            exceptionResult = ex;
		          }
		        }
		        catch (Exception ex)
		        {
		          exceptionResult = ex;
		        }
		        
		        if (request.ManageObjectLifetime && result != null)
		        {
		          Csla.Core.ISupportUndo undo = result as Csla.Core.ISupportUndo;
		          if (undo != null)
		            undo.BeginEdit();
		        }
		        
		        // return result to base class
		        base.OnQueryFinished(result, exceptionResult, null, null);
	}

	/**
	 * Gets or sets the name of the static (Shared in Visual Basic) factory method
	 * that should be called to create the object instance.
	 */
	public string FactoryMethod(){
		get
		        {
		          return _factoryMethod;
		        }
		        set
		        {
		          _factoryMethod = value;
		          OnPropertyChanged(new PropertyChangedEventArgs("GetFactoryMethod"));
		        }
	}

	/**
	 * Get the list of parameters to pass to the factory method.
	 */
	public IList FactoryParameters(){
		get
		              {
		                return _factoryParameters;
		              }
	}

	/**
	 * Gets or sets a value that indicates whether to perform object creation in a
	 * worker thread or in the active context.
	 */
	public bool IsAsynchronous(){
		get { return _isAsynchronous; }
		        set { _isAsynchronous = value; }
	}

	/**
	 * Gets or sets a value indicating whether the data control should manage the
	 * lifetime of the business object, including using n-level undo.
	 */
	public bool ManageObjectLifetime(){
		get
		        {
		          return _manageLifetime;
		        }
		        set
		        {
		          _manageLifetime = value;
		          OnPropertyChanged(new PropertyChangedEventArgs("ManageObjectLifetime"));
		        }
	}

	/**
	 * Gets or sets the type of object to create an instance of.
	 */
	public Type ObjectType(){
		get 
		        { 
		          return _objectType; 
		        }
		        set 
		        { 
		          _objectType = value;
		          OnPropertyChanged(new PropertyChangedEventArgs("TypeName"));
		        }
	}

	/**
	 * Accepts changes to the business object, and commits them by calling the
	 * object's Save() method.
	 * 
	 *        @remark  This method does nothing unless the object implements Csla.Core.
	 * ISavable.
	 *         If the object implements IClonable, it will be cloned, and the clone
	 * will be saved.
	 *         If the object supports n-level undo and ManageLifetime is true, then
	 * this method will automatically call ApplyEdit() and BeginEdit() appropriately.
	 */
	public void Save(){
		// only do something if the object implements
		        // ISavable
		        Csla.Core.ISavable savable = this.Data as Csla.Core.ISavable;
		        if (savable != null)
		        {
		          object result = savable;
		          Exception exceptionResult = null;
		          try
		          {
		            // apply edits in memory
		            Csla.Core.ISupportUndo undo = savable as Csla.Core.ISupportUndo;
		            if (undo != null && _manageLifetime)
		              undo.ApplyEdit();
		        
		            if (!Csla.ApplicationContext.AutoCloneOnUpdate)
		            {
		              // clone the object if possible
		              ICloneable clonable = savable as ICloneable;
		              if (clonable != null)
		                savable = (Csla.Core.ISavable)clonable.Clone();
		            }
		        
		            // save the clone
		            result = savable.Save();
		        
		            if (!ReferenceEquals(savable, this.Data) && !Csla.ApplicationContext.AutoCloneOnUpdate)
		            {
		              // raise Saved event from original object
		              Core.ISavable original = this.Data as Core.ISavable;
		              if (original != null)
		                original.SaveComplete(result);
		            }
		        
		            // start editing the resulting object
		            undo = result as Csla.Core.ISupportUndo;
		            if (undo != null && _manageLifetime)
		              undo.BeginEdit();
		          }
		          catch (Exception ex)
		          {
		            exceptionResult = ex;
		          }
		          // clear previous object
		          base.OnQueryFinished(null, null, null, null);
		          // return result to base class
		          base.OnQueryFinished(result, exceptionResult, null, null);
		        }
	}

}