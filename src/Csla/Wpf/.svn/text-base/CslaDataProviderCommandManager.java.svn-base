package Csla.Wpf;

/**
 * Implements support for RoutedCommands that can be executed by the
 * CslaDataProvider control.
 * 
 *      @remark Use this object as the CommandTarget for command source objects
 * when you want the CslaDataProvider to execute the command.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:30 PM
 */
public class CslaDataProviderCommandManager extends System.Windows.UIElement {

	private CslaDataProvider _provider;



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param provider
	 */
	internal CslaDataProviderCommandManager(CslaDataProvider provider){
		_provider = provider;
	}

	private static CslaDataProviderCommandManager(){
		CommandBinding binding;
		        
		        binding = new CommandBinding(ApplicationCommands.Save, SaveCommand, CanExecuteSave);
		        CommandManager.RegisterClassCommandBinding(typeof(CslaDataProviderCommandManager), binding);
		        
		        binding = new CommandBinding(ApplicationCommands.Undo, UndoCommand, CanExecuteUndo);
		        CommandManager.RegisterClassCommandBinding(typeof(CslaDataProviderCommandManager), binding);
		        
		        binding = new CommandBinding(ApplicationCommands.New, NewCommand, CanExecuteNew);
		        CommandManager.RegisterClassCommandBinding(typeof(CslaDataProviderCommandManager), binding);
	}

	/**
	 * 
	 * @param target
	 * @param e
	 */
	private static void CanExecuteNew(object target, CanExecuteRoutedEventArgs e){
		bool result = false;
		        CslaDataProviderCommandManager ctl = target as CslaDataProviderCommandManager;
		        if (ctl != null && ctl.Provider != null)
		        {
		          IBindingList list = ctl.Provider.Data as IBindingList;
		          if (list != null)
		            result = list.AllowNew;
		        }
		        e.CanExecute = result;
	}

	/**
	 * 
	 * @param target
	 * @param e
	 */
	private static void CanExecuteSave(object target, CanExecuteRoutedEventArgs e){
		bool result = false;
		        CslaDataProviderCommandManager ctl = target as CslaDataProviderCommandManager;
		        if (ctl != null && ctl.Provider != null)
		        {
		          Csla.Core.IEditableBusinessObject ibiz = ctl.Provider.Data as Csla.Core.IEditableBusinessObject;
		          if (ibiz != null)
		            result = ibiz.IsSavable;
		          else
		          {
		            Csla.Core.IEditableCollection icol = ctl.Provider.Data as Csla.Core.IEditableCollection;
		            if (icol != null)
		              result = icol.IsSavable;
		          }
		        }
		        e.CanExecute = result;
	}

	/**
	 * 
	 * @param target
	 * @param e
	 */
	private static void CanExecuteUndo(object target, CanExecuteRoutedEventArgs e){
		bool result = false;
		        CslaDataProviderCommandManager ctl = target as CslaDataProviderCommandManager;
		        if (ctl != null && ctl.Provider != null)
		        {
		          Csla.Core.IEditableBusinessObject ibiz = ctl.Provider.Data as Csla.Core.IEditableBusinessObject;
		          if (ibiz != null)
		            result = ibiz.IsDirty;
		          else
		          {
		            Csla.Core.IEditableCollection icol = ctl.Provider.Data as Csla.Core.IEditableCollection;
		            if (icol != null)
		              result = icol.IsDirty;
		          }
		        }
		        e.CanExecute = result;
	}

	/**
	 * 
	 * @param target
	 * @param e
	 */
	private static void NewCommand(object target, ExecutedRoutedEventArgs e){
		CslaDataProviderCommandManager ctl = target as CslaDataProviderCommandManager;
		        if (ctl != null && ctl.Provider != null)
		          ctl.Provider.AddNew();
	}

	private CslaDataProvider Provider(){
		get { return _provider; }
	}

	/**
	 * 
	 * @param target
	 * @param e
	 */
	private static void SaveCommand(object target, ExecutedRoutedEventArgs e){
		CslaDataProviderCommandManager ctl = target as CslaDataProviderCommandManager;
		        if (ctl != null && ctl.Provider != null)
		          ctl.Provider.Save();
	}

	/**
	 * 
	 * @param target
	 * @param e
	 */
	private static void UndoCommand(object target, ExecutedRoutedEventArgs e){
		CslaDataProviderCommandManager ctl = target as CslaDataProviderCommandManager;
		        if (ctl != null && ctl.Provider != null)
		          ctl.Provider.Cancel();
	}

}