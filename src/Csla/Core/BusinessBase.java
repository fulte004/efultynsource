package Csla.Core;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import Csla.ApplicationContext;
import Csla.DataPortalEvent;
import Csla.Validation.BrokenRule;
import Csla.Validation.BrokenRulesCollection;
import Csla.Validation.RuleSeverity;
import Csla.Validation.SharedValidationRules;
import Csla.Validation.ValidationException;
import Csla.Validation.ValidationRules;
import Csla.Properties.Resources;
import Csla.Security.AuthorizationRules;
import Csla.Security.AuthorizeReadWrite;
import Csla.Security.SharedAuthorizationRules;


/**
 * This is the non-generic base class from which most business objects will be
 * derived.
 * 
 *      @remark See Chapter 3 for details.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:25 PM
 */
public abstract class BusinessBase extends UndoableBase implements EditableBusinessObject, AuthorizeReadWrite, /*, IEditableObject, IDataErrorInfo,*/ Cloneable  {

	private Csla.Security.AuthorizationRules _authorizationRules;
	private boolean _disableIEditableObject;
	/**
	 * we need to keep track of the edit level when we weere added so if the user
	 * cancels below that level we can be destroyed
	 */
	private int _editLevelAdded;
	private HashMap<String, Boolean> _executeResultCache;
	private boolean _isChild;
	private boolean _isDeleted;
	private boolean _isDirty = true;
	/**
	 * keep track of whether we are new, deleted or dirty
	 */
	private boolean _isNew = true;
	private java.security.Principal _lastPrincipal;
	private boolean _neverCommitted = true;
	private Parent _parent;
	private HashMap<String, Boolean> _readResultCache;
	private ValidationRules _validationRules;
	private HashMap<String, Boolean> _writeResultCache;

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the Object.
	 */
	protected BusinessBase(){
		Initialize();
		AddInstanceBusinessRules();
		if (!Csla.Validation.SharedValidationRules.RulesExistFor(this.getClass()))
		{
			synchronized (this.getClass())
			{
				if (!SharedValidationRules.RulesExistFor(this.getClass()))
				{
					SharedValidationRules.GetManager(this.getClass(), true);
					AddBusinessRules();
				}
			}
		}
		AddInstanceAuthorizationRules();
		if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
		{
			synchronized (this.getClass())
			{
				if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
				{
					SharedAuthorizationRules.GetManager(this.getClass(), true);
					AddAuthorizationRules();
				}
			}
		}
	}

	/**
	 * Notifies the parent Object (if any) that this child Object's edits have been
	 * accepted.
	 */
	protected void AcceptChangesComplete(){
		if (getParent() != null)
			getParent().applyEditChild(this);
		super.acceptChangesComplete();
	}

	/**
	 * Override this method to add per-type authorization rules for your type's
	 * properties.
	 * 
	 *        @remark AddAuthorizationRules is automatically called by CSLA .NET when
	 * your Object should associate per-type authorization roles with its properties.
	 */
	protected void AddAuthorizationRules(){

	}

	/**
	 * Override this method in your business class to be notified when you need to set
	 * up shared business rules.
	 * 
	 *        @remark This method is automatically called by CSLA .NET when your
	 * Object should associate per-type validation rules with its properties.
	 */
	protected void AddBusinessRules(){

	}

	/**
	 * Override this method to add authorization rules for your Object's properties.
	 * 
	 *        @remark AddInstanceAuthorizationRules is automatically called by CSLA .
	 * NET when your Object should associate per-instance authorization roles with its
	 * properties.
	 */
	protected void AddInstanceAuthorizationRules(){

	}

	/**
	 * Override this method in your business class to be notified when you need to set
	 * up business rules.
	 * 
	 *        @remark This method is automatically called by CSLA .NET when your
	 * Object should associate per-instance validation rules with its properties.
	 */
	protected void AddInstanceBusinessRules(){

	}

	/**
	 * Commits the current edit process.
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * @remark Calling this method causes the most recently taken snapshot of
	 * the Object's state to be discarded, thus committing any changes made to the
	 * Object's state since the last BeginEdit call.
	 */
	public void ApplyEdit() throws IllegalArgumentException, UndoException, IllegalAccessException{
		_neverCommitted = false;
		acceptChanges(this.getEditLevel() - 1);
		setBindingEdit(false);
	}

	/**
	 * Provides access to the AuthorizationRules Object for this Object.
	 * 
	 *        @remark Use this Object to add a list of allowed and denied roles for
	 * reading and writing properties of the Object. Typically these values are added
	 * once when the business Object is instantiated.
	 */
	protected AuthorizationRules getAuthorizationRules(){
		if (_authorizationRules == null)
			_authorizationRules = new AuthorizationRules(this.getClass());
		return _authorizationRules;		              
	}

	/**
	 * Starts a nested edit on the Object.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * 
	 *        @remark  When this method is called the Object takes a snapshot of its
	 * current state (the values of its variables). This snapshot can be restored by
	 * calling CancelEdit or committed by calling ApplyEdit.
	 *         This is a nested operation. Each call to BeginEdit adds a new snapshot
	 * of the Object's state to a stack. You should ensure that for each call to
	 * BeginEdit there is a corresponding call to either CancelEdit or ApplyEdit to
	 * remove that snapshot from the stack.
	 *         See Chapters 2 and 3 for details on n-level undo and state stacking.
	 */
	public void BeginEditLocal() throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		copyState(this.getEditLevel() + 1);
	}

	/**
	 * Provides access to the readonly collection of broken business rules for this
	 * Object.
	 * 
	 *        @returns A Csla.Validation.RulesCollection Object.
	 */
	public BrokenRulesCollection getBrokenRulesCollection(){
		return getValidationRules().GetBrokenRules(); 
	}

	/**
	 * Cancels the current edit process, restoring the Object's state to its previous
	 * values.
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * 
	 *        @remark Calling this method causes the most recently taken snapshot of
	 * the Object's state to be restored. This resets the Object's values to the point
	 * of the last BeginEdit call.
	 */
	public void CancelEditLocal() throws IllegalArgumentException, IllegalAccessException, UndoException, IOException{
		undoChanges(this.getEditLevel() - 1);
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the calling
	 * method.
	 * 
	 *        @returns <see langword="true" /> if execute is allowed.
	 * 
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean CanExecuteMethod(boolean throwOnFalse){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String methodName = callStack[1].getMethodName();
		boolean result = CanExecuteMethod(methodName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(String.format("%1$ (%2$)", Resources.getMethodExecuteNotAllowed(), methodName));
			//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the specified
	 * method.
	 * 
	 *        @returns <see langword="true" /> if execute is allowed.
	 * 
	 * @param methodName    Name of the method to execute.
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean CanExecuteMethod(String methodName, boolean throwOnFalse){
		boolean result = CanExecuteMethod(methodName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(String.format("%1$ (%2$)", Resources.getMethodExecuteNotAllowed(), methodName));
			//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the calling
	 * method.
	 * 
	 *        @returns <see langword="true" /> if execute is allowed.
	 */
	public boolean CanExecuteMethod(){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String methodName = callStack[1].getMethodName();
		return CanExecuteMethod(methodName);
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the specified
	 * method.
	 * 
	 *        @returns <see langword="true" /> if execute is allowed.
	 *        @remark  If a list of allowed roles is provided then only users in those
	 * roles can execute the method. If no list of allowed roles is provided then the
	 * list of denied roles is checked.
	 *         If a list of denied roles is provided then users in the denied roles
	 * are not allowed to execute the method. All other users are allowed.
	 *         If neither a list of allowed nor denied roles is provided then all
	 * users will be allowed to execute the method..
	 * 
	 * @param methodName    Name of the method to execute.
	 */
	public boolean CanExecuteMethod(String methodName){
		boolean result = true;

		VerifyAuthorizationCache();

		if (_executeResultCache.containsKey(methodName))
		{
			// cache contains value - get cached value
			result = _executeResultCache.get(methodName);

		}
		else
		{
			if (getAuthorizationRules().hasExecuteAllowedRoles(methodName))
			{
				// some users are explicitly granted read access
				// in which case all other users are denied
				if (!(getAuthorizationRules().isExecuteAllowed(methodName, null)))
				{
					result = false;
				}

			}
			else if (getAuthorizationRules().hasExecuteDeniedRoles(methodName))
			{
				// some users are explicitly denied read access
				if (getAuthorizationRules().isExecuteDenied(methodName, null))
				{
					result = false;
				}
			}
			// store value in cache
			_executeResultCache.put(methodName, result);
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to read the calling
	 * property.
	 * 
	 *        @returns <see langword="true" /> if read is allowed.
	 * 
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean CanReadProperty(boolean throwOnFalse){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String propertyName = callStack[1].getMethodName().substring(4);
		boolean result = CanReadProperty(propertyName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(
					String.format("%1$ (%2$)",
							Resources.getPropertyGetNotAllowed(), propertyName));
			//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to read the calling
	 * property.
	 * 
	 *        @returns <see langword="true" /> if read is allowed.
	 * 
	 * @param propertyName    Name of the property to read.
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean CanReadProperty(String propertyName, boolean throwOnFalse){
		boolean result = CanReadProperty(propertyName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(
					String.format("%1$ (%2$)",
							Resources.getPropertyGetNotAllowed(), propertyName));
			//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to read the calling
	 * property.
	 * 
	 *        @returns <see langword="true" /> if read is allowed.
	 */
	public boolean CanReadProperty(){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String propertyName = callStack[1].getMethodName().substring(4);

		return CanReadProperty(propertyName);
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to read the specified
	 * property.
	 * 
	 *        @returns <see langword="true" /> if read is allowed.
	 *        @remark  If a list of allowed roles is provided then only users in those
	 * roles can read. If no list of allowed roles is provided then the list of denied
	 * roles is checked.
	 *         If a list of denied roles is provided then users in the denied roles
	 * are denied read access. All other users are allowed.
	 *         If neither a list of allowed nor denied roles is provided then all
	 * users will have read access.
	 * 
	 * @param propertyName    Name of the property to read.
	 */
	public boolean CanReadProperty(String propertyName){
		boolean result = true;

		VerifyAuthorizationCache();

		if (_readResultCache.containsKey(propertyName))
		{
			// cache contains value - get cached value
			result = _readResultCache.get(propertyName);
		}
		else
		{
			if (getAuthorizationRules().hasReadAllowedRoles(propertyName))
			{
				// some users are explicitly granted read access
				// in which case all other users are denied
				if (!getAuthorizationRules().isReadAllowed(propertyName, null))
					result = false;
			}
			else if (getAuthorizationRules().hasReadDeniedRoles(propertyName))
			{
				// some users are explicitly denied read access
				if (getAuthorizationRules().isReadDenied(propertyName, null))
					result = false;
			}
			// store value in cache
			_readResultCache.put(propertyName, result);
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to write the calling
	 * property.
	 * 
	 *        @returns <see langword="true" /> if write is allowed.
	 * 
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean CanWriteProperty(boolean throwOnFalse){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String propertyName = callStack[1].getMethodName().substring(4);
		//		String propertyName = new System.Diagnostics.StackTrace().GetFrame(1).GetMethod().Name.Substring(4);
		boolean result = CanWriteProperty(propertyName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(
					String.format("%1$ (%2$)", Resources.getPropertySetNotAllowed(), propertyName));
			//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to write the calling
	 * property.
	 * 
	 *        @returns <see langword="true" /> if write is allowed.
	 * 
	 * @param propertyName    Name of the property to write.
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean CanWriteProperty(String propertyName, boolean throwOnFalse){
		boolean result = CanWriteProperty(propertyName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(
					String.format("%1$ (%2$)", Resources.getPropertySetNotAllowed(), propertyName));
			//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to write the calling
	 * property.
	 * 
	 *        @returns <see langword="true" /> if write is allowed.
	 */
	public boolean CanWriteProperty(){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String propertyName = callStack[1].getMethodName().substring(4);
		//		String propertyName = new System.Diagnostics.StackTrace().GetFrame(1).GetMethod().Name.Substring(4);
		return CanWriteProperty(propertyName);
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to write the specified
	 * property.
	 * 
	 *        @returns <see langword="true" /> if write is allowed.
	 *        @remark  If a list of allowed roles is provided then only users in those
	 * roles can write. If no list of allowed roles is provided then the list of
	 * denied roles is checked.
	 *         If a list of denied roles is provided then users in the denied roles
	 * are denied write access. All other users are allowed.
	 *         If neither a list of allowed nor denied roles is provided then all
	 * users will have write access.
	 * 
	 * @param propertyName    Name of the property to write.
	 */
	public boolean CanWriteProperty(String propertyName){
		boolean result = true;

		VerifyAuthorizationCache();

		if (_writeResultCache.containsKey(propertyName))
		{
			// cache contains value - get cached value
			result = _writeResultCache.get(propertyName);
		}
		else
		{
			if (this.getAuthorizationRules().hasWriteAllowedRoles(propertyName))
			{
				// some users are explicitly granted write access
				// in which case all other users are denied
				if (!getAuthorizationRules().isWriteAllowed(propertyName, null))
					result = false;
			}
			else if (getAuthorizationRules().hasWriteDeniedRoles(propertyName))
			{
				// some users are explicitly denied write access
				if (getAuthorizationRules().isWriteDenied(propertyName, null))
					result = false;
			}
			_writeResultCache.put(propertyName, result);
		}
		return result;
	}

	protected Object Clone(){
		return GetClone();
	}

	/**
	 * Allow data binding to start a nested edit on the Object.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * 
	 *        @remark Data binding may call this method many times. Only the first
	 * call should be honored, so we have extra code to detect this and do nothing for
	 * subsquent calls.
	 */
	public void BeginEdit() throws IllegalArgumentException, UndoException, IllegalAccessException, IOException{
		if (!_disableIEditableObject && !getBindingEdit())
		{
			setBindingEdit(true);
			BeginEditLocal();
		}
	}

	/**
	 * Allow data binding to cancel the current edit.
	 * 
	 *        @remark Data binding may call this method many times. Only the first
	 * call to either IEditableObject.CancelEdit or IEditableObject.EndEdit should be
	 * honored. We include extra code to detect this and do nothing for subsequent
	 * calls.
	 */
	public void CancelEdit(){
		if (!_disableIEditableObject && getBindingEdit())
		{
			CancelEdit();
			if (isNew() && _neverCommitted && getEditLevel() <= getEditLevelAdded())
			{
				// we're new and no EndEdit or ApplyEdit has ever been
				// called on us, and now we've been cancelled back to
				// where we were added so we should have ourselves
				// removed from the parent collection
				if (getParent() != null)
					getParent().removeChild(this);
			}
		}
	}

	/**
	 * Allow data binding to apply the current edit.
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 * 
	 *        @remark Data binding may call this method many times. Only the first
	 * call to either IEditableObject.EndEdit or IEditableObject.CancelEdit should be
	 * honored. We include extra code to detect this and do nothing for subsequent
	 * calls.
	 */
	protected void EndEdit() throws IllegalArgumentException, UndoException, IllegalAccessException{
		if (!_disableIEditableObject && getBindingEdit())
		{
			ApplyEdit();
		}
	}

	/**
	 * Override this method to load a new business Object with default values from the
	 * datasuper.
	 * 
	 *        @remark Normally you will overload this method to accept a strongly-
	 * typed criteria parameter, rather than overriding the method with a loosely-
	 * typed criteria parameter.
	 */
	protected void DataPortal_Create(){
		throw new UnsupportedOperationException(Resources.getCreateNotSupportedException());
	}

	/**
	 * Override this method to allow immediate deletion of a business Object.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 */
	protected void DataPortal_Delete(Object criteria){
		throw new UnsupportedOperationException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to allow deferred deletion of a business Object.
	 */
	protected void DataPortal_DeleteSelf(){
		throw new UnsupportedOperationException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to allow retrieval of an existing business Object based on
	 * data in the datasuper.
	 * 
	 *        @remark Normally you will overload this method to accept a strongly-
	 * typed criteria parameter, rather than overriding the method with a loosely-
	 * typed criteria parameter.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 */
	protected void DataPortal_Fetch(Object criteria){
		throw new UnsupportedOperationException(Resources.getFetchNotSupportedException());
	}

	/**
	 * Override this method to allow insertion of a business Object.
	 */
	protected void DataPortal_Insert(){
		throw new UnsupportedOperationException(Resources.getInsertNotSupportedException());
	}

	/**
	 * Called by the server-side DataPortal if an exception occurs during data access.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 * @param ex    The Exception thrown during data access.
	 */
	protected void DataPortal_OnDataPortalException(DataPortalEvent e, Exception ex){

	}

	/**
	 * Called by the server-side DataPortal prior to calling the requested
	 * DataPortal_XYZ method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvoke(DataPortalEvent e){

	}

	/**
	 * Called by the server-side DataPortal after calling the requested DataPortal_XYZ
	 * method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvokeComplete(DataPortalEvent e){

	}

	/**
	 * Override this method to allow update of a business Object.
	 */
	protected void DataPortal_Update(){
		throw new UnsupportedOperationException(Resources.getUpdateNotSupportedException());
	}

	/**
	 * Marks the Object for deletion. The Object will be deleted as part of the next
	 * save operation.
	 * 
	 *        @remark  CSLA .NET supports both immediate and deferred deletion of
	 * objects. This method is part of the support for deferred deletion, where an
	 * Object can be marked for deletion, but isn't actually deleted until the Object
	 * is saved to the datasuper. This method is called by the UI developer to mark the
	 * Object for deletion.
	 *         To 'undelete' an Object, use n-level undo as discussed in Chapters 2
	 * and 3.
	 */
	public void Delete(){
		if (this.isChild())
			throw new UnsupportedOperationException(Resources.getChildDeleteException());

		MarkDeleted();
	}

	/**
	 * Called by a parent Object to mark the child for deferred deletion.
	 */
	public void DeleteChild(){
		if (!this.isChild())
			throw new UnsupportedOperationException(Resources.getNoDeleteRootException());

		setBindingEdit(false);
		MarkDeleted();
	}

	/**
	 * Gets or sets a value indicating whether the IEditableObject interface methods
	 * should be disabled for this Object.
	 * 
	 *        @value Defaults to False, indicating that the IEditableObject methods
	 * will behave normally.
	 *        @remark If you disable the IEditableObject methods then Windows Forms
	 * data binding will no longer automatically call BeginEdit, CancelEdit or
	 * ApplyEdit on your Object, and you will have to call these methods manually to
	 * get proper n-level undo behavior.
	 */
	protected boolean getDisableIEditableObject(){

		return _disableIEditableObject;
	}
	protected void setDisableIEditableObject(boolean value){
		_disableIEditableObject = value;
	}

	public int getEditLevel(){
		return super.getEditLevel();
	}

	/**
	 * Gets or sets the current edit level of the Object.
	 * 
	 *        @remark Allow the collection Object to use the edit level as needed.
	 */
	public int getEditLevelAdded(){
		return _editLevelAdded; 
	}
	public void setEditLevelAdded(int value){
		_editLevelAdded = value; 
	}

	protected String getError(){
		if (!isValid())
			return getValidationRules().GetBrokenRules().ToString(
					RuleSeverity.ERROR);
		else
			return "";
	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *        @returns A new Object containing the exact data of the original Object.
	 */
	protected Object GetClone(){
		return ObjectCloner.clone(this);
	}

	/**
	 * Override this method to set up event handlers so user code in a partial class
	 * can respond to events raised by generated code.
	 */
	protected void Initialize(){
		/* allows subclass to initialize events before any other activity occurs */
	}

	/**
	 * Returns <see langword="true" /> if this is a child (non-root) Object.
	 */
	public boolean isChild(){
		return _isChild; 
	}

	/**
	 * Returns <see langword="true" /> if this Object is marked for deletion.
	 * 
	 *        @remark CSLA .NET supports both immediate and deferred deletion of
	 * objects. This property is part of the support for deferred deletion, where an
	 * Object can be marked for deletion, but isn't actually deleted until the Object
	 * is saved to the datasuper. This property indicates whether or not the current
	 * Object has been marked for deletion. If it is <see langword="true" /> , the
	 * Object will be deleted when it is saved to the database, otherwise it will be
	 * inserted or updated by the save operation.
	 *        @returns A value indicating if this Object is marked for deletion.
	 */
	public boolean isDeleted(){
		return _isDeleted; 
	}

	/**
	 * Returns <see langword="true" /> if this Object's data has been changed.
	 * 
	 *        @remark  When an Object's data is changed, CSLA .NET makes note of that
	 * change and considers the Object to be 'dirty' or changed. This value is used to
	 * optimize data updates, since an unchanged Object does not need to be updated
	 * into the datasuper. All new objects are considered dirty. All objects marked for
	 * deletion are considered dirty.
	 *         Once an Object's data has been saved to the database (inserted or
	 * updated) the dirty flag is cleared and the Object is considered unchanged.
	 * Objects newly loaded from the database are also considered unchanged.
	 *        @returns A value indicating if this Object's data has been changed.
	 */
	public boolean isDirty(){
		return _isDirty; 
	}

	/**
	 * Returns <see langword="true" /> if this is a new Object,
	 *        <see langword="false" /> if it is a pre-existing Object.
	 * 
	 *        @remark An Object is considered to be new if its primary identifying
	 * (key) value doesn't correspond to data in the datasuper. In other words, if the
	 * data values in this particular Object have not yet been saved to the database
	 * the Object is considered to be new. Likewise, if the Object's data has been
	 * deleted from the database then the Object is considered to be new.
	 *        @returns A value indicating if this Object is new.
	 */
	public boolean isNew(){
		return _isNew;
	}

	/**
	 * Returns <see langword="true" /> if this Object is both dirty and valid.
	 * 
	 *        @remark An Object is considered dirty (changed) if
	 *        <see cref="P:Csla.BusinessBase.IsDirty" /> returns <see langword="true"
	 * />. It is considered valid if IsValid returns <see langword="true" />. The
	 * IsSavable property is a combination of these two properties.
	 *        @returns A value indicating if this Object is both dirty and valid.
	 */
	public boolean isSavable(){
		return (isDirty() && isValid());
	}

	/**
	 * Returns <see langword="true" /> if the Object is currently valid, <see
	 * langword="false" /> if the Object has broken rules or is otherwise invalid.
	 * 
	 *        @remark  By default this property relies on the underling
	 * ValidationRules Object to track whether any business rules are currently broken
	 * for this Object.
	 *         You can override this property to provide more sophisticated
	 * implementations of the behavior. For instance, you should always override this
	 * method if your Object has child objects, since the validity of this Object is
	 * affected by the validity of all child objects.
	 *        @returns A value indicating if the Object is currently valid.
	 */
	public boolean isValid(){
		return getValidationRules().isValid();
	}

	/**
	 * Marks the Object as being a child Object.
	 */
	protected void MarkAsChild(){
		_isChild = true;
	}

	/**
	 * Forces the Object's IsDirty flag to <see langword="false" />.
	 * 
	 *        @remark This method is normally called automatically and is not intended
	 * to be called manually.
	 */
	protected void MarkClean(){
		_isDirty = false;
		onUnknownPropertyChanged();
	}

	/**
	 * Marks an Object for deletion. This also marks the Object as being dirty.
	 * 
	 *        @remark You should call this method in your business logic in the case
	 * that you want to have the Object deleted when it is saved to the datasuper.
	 */
	protected void MarkDeleted(){
		_isDeleted = true;
		MarkDirty();
	}

	/**
	 * Marks an Object as being dirty, or changed.
	 * 
	 *        @remark  You should call this method in your business logic any time the
	 * Object's public data changes. Any time any instance variable changes within
	 * the Object, this method should be called to tell CSLA .NET that the Object's
	 * data has been changed.
	 *         Marking an Object as dirty does two things. First it ensures that CSLA .
	 * NET will properly save the Object as appropriate. Second, it causes CSLA .NET
	 * to tell Windows Forms data binding that the Object's data has changed so any
	 * bound controls will update to reflect the new values.
	 */
	protected void MarkDirty(){
		MarkDirty(false);
	}

	/**
	 * Marks an Object as being dirty, or changed.
	 * 
	 * @param suppressEvent    <see langword="true" /> to supress the PropertyChanged
	 * event that is otherwise raised to indicate that the Object's state has changed.
	 */
	protected void MarkDirty(boolean suppressEvent){
		_isDirty = true;
		if (!suppressEvent)
			onUnknownPropertyChanged();
	}

	/**
	 * Marks the Object as being a new Object. This also marks the Object as being
	 * dirty and ensures that it is not marked for deletion.
	 * 
	 *        @remark  Newly created objects are marked new by default. You should
	 * call this method in the implementation of DataPortal_Update when the Object is
	 * deleted (due to being marked for deletion) to indicate that the Object no
	 * longer reflects data in the datasuper.
	 *         If you override this method, make sure to call the base implementation
	 * after executing your new code.
	 */
	protected void MarkNew(){
		_isNew = true;
		_isDeleted = false;
		MarkDirty();
	}

	/**
	 * Marks the Object as being an old (not new) Object. This also marks the Object
	 * as being unchanged (not dirty).
	 * 
	 *        @remark  You should call this method in the implementation of
	 * DataPortal_Fetch to indicate that an existing Object has been successfully
	 * retrieved from the datasuper.
	 *         You should call this method in the implementation of DataPortal_Update
	 * to indicate that a new Object has been successfully inserted into the datasuper.
	 * 
	 *         If you override this method, make sure to call the base implementation
	 * after executing your new code.
	 */
	protected void MarkOld(){
		_isNew = false;
		MarkClean();
	}

	/**
	 * This method is called on a newly deserialized Object after deserialization is
	 * complete.
	 * 
	 * @param context    Serialization context Object.
	 */
	protected void onDeserialized(Serializable context){
		// do nothing - this is here so a subclass
		// could override if needed
	}

	/**
	 * 
	 * @param context
	 */
	protected void onDeserializedHandler(Serializable context){
		onDeserialized(context);
		getValidationRules().SetTarget(this);
		AddInstanceBusinessRules();
		if (!SharedValidationRules.RulesExistFor(this.getClass()))
		{
			synchronized (this.getClass())
			{
				if (!SharedValidationRules.RulesExistFor(this.getClass()))
				{
					SharedValidationRules.GetManager(this.getClass(), true);
					AddBusinessRules();
				}
			}
		}
		AddInstanceAuthorizationRules();
		if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
		{
			synchronized (this.getClass())
			{
				if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
				{
					SharedAuthorizationRules.GetManager(this.getClass(), true);
					AddAuthorizationRules();
				}
			}
		}
	}

	/**
	 * Provide access to the parent reference for use in child Object code.
	 * 
	 *        @remark This value will be Nothing for root objects.
	 */
	protected Parent getParent(){
		return _parent; 
	}
	/**
	 * Used by BusinessListBase as a child Object is created to tell the child Object
	 * about its parent.
	 * 
	 * @param parent    A reference to the parent collection Object.
	 */
	public void setParent(Parent parent){
		_parent = parent;
	}
	/**
	 * Performs processing required when the current property has changed.
	 * @throws ValidationException 
	 * 
	 *        @remark  This method calls CheckRules(propertyName), MarkDirty and
	 * OnPropertyChanged(propertyName). MarkDirty is called such that no event is
	 * raised for IsDirty, so only the specific property changed event for the current
	 * property is raised.
	 *         This implementation uses System.Diagnostics.StackTrace to determine the
	 * name of the current property, and so must be called directly from the property
	 * to be checked.
	 */
	protected void PropertyHasChanged() throws ValidationException{
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String propertyName = 
			callStack[1].getMethodName().substring(4);
		PropertyHasChanged(propertyName, null, null);
	}

	/**
	 * Performs processing required when a property has changed.
	 * 
	 *        @remark This method calls CheckRules(propertyName), MarkDirty and
	 * OnPropertyChanged(propertyName). MarkDirty is called such that no event is
	 * raised for IsDirty, so only the specific property changed event for the current
	 * property is raised.
	 * 
	 * @param propertyName    Name of the property that has changed.
	 * @throws ValidationException 
	 */
	protected void PropertyHasChanged(String propertyName, Object newValue, Object oldValue) throws ValidationException{
		getValidationRules().CheckRules(propertyName);
		MarkDirty(true);
		onPropertyChanged(propertyName, oldValue, newValue);
	}



	/**
	 * 
	 * @param parent
	 */
	//	 private void SetParent(IParent parent){
	//		 this.SetParent(parent);
	//	 }

	/**
	 * 
	 * @param columnName
	 */
	protected String get(String columnName){
		String result = "";
		if (!isValid())
		{
			BrokenRule rule = 
				getValidationRules().GetBrokenRules().GetFirstBrokenRule(columnName);
			if (rule != null)
				result = rule.getDescription();
		}
		return result;
	}

	/**
	 * Called when an undo operation has completed.
	 * 
	 *        @remark This method resets the Object as a result of deserialization and
	 * raises PropertyChanged events to notify data binding that the Object has
	 * changed.
	 */
	protected void UndoChangesComplete(){
		setBindingEdit(false);
		getValidationRules().SetTarget(this);
		AddInstanceBusinessRules();
		if (!SharedValidationRules.RulesExistFor(this.getClass()))
		{
			synchronized (this)
			{
				if (!SharedValidationRules.RulesExistFor(this.getClass()))
				{
					SharedValidationRules.GetManager(this.getClass(), true);
					AddBusinessRules();
				}
			}
		}
		onUnknownPropertyChanged();
		super.undoChangesComplete();
	}

	/**
	 * Provides access to the broken rules functionality.
	 * 
	 *        @remark This property is used within your business logic so you can
	 * easily call the AddRule() method to associate validation rules with your
	 * Object's properties.
	 */
	protected Csla.Validation.ValidationRules getValidationRules(){


		if (_validationRules == null)
			_validationRules = new Csla.Validation.ValidationRules(this);
		return _validationRules;

	}

	private void VerifyAuthorizationCache(){
		if (_readResultCache == null)
			_readResultCache = new HashMap<String, Boolean>();
		if (_writeResultCache == null)
			_writeResultCache = new HashMap<String, Boolean>();
		if (_executeResultCache == null)
			_executeResultCache = new HashMap<String, Boolean>();
		if (!ApplicationContext.getUser(null).equals(_lastPrincipal))
		{
			// the principal has changed - reset the cache
			_readResultCache.clear();
			_writeResultCache.clear();
			_executeResultCache.clear();
			_lastPrincipal = ApplicationContext.getUser(null);
		}
	}

}