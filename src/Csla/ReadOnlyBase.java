package Csla;
import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Csla.Core.ObjectCloner;
import Csla.Core.ReadOnlyObject;
import Csla.Properties.Resources;
import Csla.Security.AuthorizationRules;
import Csla.Security.AuthorizeReadWrite;
import Csla.Security.SharedAuthorizationRules;

/**
 * This is a base class from which readonly business classes can be derived.
 * 
 *    @remark This base class only supports data retrieve, not updating or
 * deleting. Any business classes derived from this base class should only
 * implement readonly properties.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:40 PM
 */
public abstract class ReadOnlyBase<T extends ReadOnlyBase<T>> implements Cloneable, ReadOnlyObject, AuthorizeReadWrite {

	private AuthorizationRules _authorizationRules;
	private HashMap<String, Boolean> _executeResultCache;
	private Principal _lastPrincipal;
	private HashMap<String, Boolean> _readResultCache;

	/**
	 * Creates an instance of the Object.
	 */
	protected ReadOnlyBase(){
		Initialize();
		addInstanceAuthorizationRules();
		if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
		{
			synchronized (this.getClass())
			{
				if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
				{
					SharedAuthorizationRules.GetManager(this.getClass(), true);
					addAuthorizationRules();
				}
			}
		}
	}

	/**
	 * Override this method to add per-type authorization rules for your type's
	 * properties.
	 * 
	 *      @remark AddSharedAuthorizationRules is automatically called by CSLA .NET
	 * when your Object should associate per-type authorization roles with its
	 * properties.
	 */
	protected void addAuthorizationRules(){

	}

	/**
	 * Override this method to add authorization rules for your Object's properties.
	 */
	protected void addInstanceAuthorizationRules(){

	}

	/**
	 * Provides access to the AuthorizationRules Object for this Object.
	 * 
	 *      @remark Use this Object to add a list of allowed and denied roles for
	 * reading and writing properties of the Object. Typically these values are added
	 * once when the business Object is instantiated.
	 */
	protected AuthorizationRules getAuthorizationRules(){
		if (_authorizationRules == null)
			_authorizationRules = new AuthorizationRules(this.getClass());
		return _authorizationRules;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the calling
	 * method.
	 * 
	 *      @returns <see langword="true" /> if execute is allowed.
	 * 
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean canExecuteMethod(boolean throwOnFalse){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String methodName = callStack[1].getMethodName();
		boolean result = CanExecuteMethod(methodName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(String.format("$1% ($2%)", Resources.getMethodExecuteNotAllowed(), methodName));
			//		        ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the specified
	 * method.
	 * 
	 *      @returns <see langword="true" /> if execute is allowed.
	 * 
	 * @param methodName    Name of the method to execute.
	 * @param throwOnFalse    Indicates whether a negative result should cause an
	 * exception.
	 */
	public boolean canExecuteMethod(String methodName, boolean throwOnFalse){
		boolean result = CanExecuteMethod(methodName);
		if (throwOnFalse && result == false)
		{
			SecurityException ex = new SecurityException(String.format("$1% ($2%)", Resources.getMethodExecuteNotAllowed(), methodName));
//			ex.Action = System.Security.Permissions.SecurityAction.Deny;
			throw ex;
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the calling
	 * method.
	 * 
	 *      @returns <see langword="true" /> if execute is allowed.
	 */
	public boolean canExecuteMethod(){
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		String methodName = callStack[1].getMethodName();
		return CanExecuteMethod(methodName);
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to execute the specified
	 * method.
	 * 
	 *      @returns <see langword="true" /> if execute is allowed.
	 *      @remark  If a list of allowed roles is provided then only users in those
	 * roles can read. If no list of allowed roles is provided then the list of denied
	 * roles is checked.
	 *       If a list of denied roles is provided then users in the denied roles are
	 * denied read access. All other users are allowed.
	 *       If neither a list of allowed nor denied roles is provided then all users
	 * will have read access.
	 * 
	 * @param methodName    Name of the method to execute.
	 * @param request 
	 */
	public boolean CanExecuteMethod(String methodName, HttpServletRequest request){
		boolean result = true;

		VerifyAuthorizationCache(request);

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
				if (!(getAuthorizationRules().isExecuteAllowed(methodName, request)))
				{
					result = false;
				}

			}
			else if (getAuthorizationRules().hasExecuteDeniedRoles(methodName))
			{
				// some users are explicitly denied read access
				if (getAuthorizationRules().isExecuteDenied(methodName, request))
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
	 *      @returns <see langword="true" /> if read is allowed.
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
					String.format("$1% ($2%)",
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
	 *      @returns <see langword="true" /> if read is allowed.
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
					String.format("$1% ($2%)",
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
	 *      @returns <see langword="true" /> if read is allowed.
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
	 *      @returns <see langword="true" /> if read is allowed.
	 *      @remark  If a list of allowed roles is provided then only users in those
	 * roles can read. If no list of allowed roles is provided then the list of denied
	 * roles is checked.
	 *       If a list of denied roles is provided then users in the denied roles are
	 * denied read access. All other users are allowed.
	 *       If neither a list of allowed nor denied roles is provided then all users
	 * will have read access.
	 * 
	 * @param propertyName    Name of the property to read.
	 * @param request 
	 */
	public boolean CanReadProperty(String propertyName, HttpServletRequest request){
		boolean result = true;

		VerifyAuthorizationCache(request);

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
				// in which case all other users are denied.
				if (!getAuthorizationRules().isReadAllowed(propertyName, request))
					result = false;
			}
			else if (getAuthorizationRules().hasReadDeniedRoles(propertyName))
			{
				// some users are explicitly denied read access.
				if (getAuthorizationRules().isReadDenied(propertyName, request))
					result = false;
			}
			// store value in cache
			_readResultCache.put(propertyName, result);
		}
		return result;
	}

	/**
	 * Returns <see langword="true" /> if the user is allowed to write the to the
	 * specified property.
	 * 
	 *      @returns <see langword="true" /> if write is allowed.
	 * 
	 * @param propertyName    Name of the property to read.
	 */
	public boolean canWriteProperty(String propertyName){
		return false;
	}
//
//	public Object clone(){
//		return getClone();
//	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *      @returns A new Object containing the exact data of the original Object.
	 */
	@SuppressWarnings("unchecked")
	public T clone(){
		return (T)getClone();
	}

	/**
	 * 
	 * @param criteria
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_Create(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getCreateNotSupportedException());
	}

	/**
	 * 
	 * @param criteria
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_Delete(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getDeleteNotSupportedException());
	}

	/**
	 * Override this method to allow retrieval of an existing business Object based on
	 * data in the database.
	 * 
	 * @param criteria    An Object containing criteria values to identify the Object.
	 * @throws NotSupportedException 
	 */
	protected void DataPortal_Fetch(Object criteria) throws NotSupportedException{
		throw new NotSupportedException(Resources.getFetchNotSupportedException());
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
	 * DataPortal_xyz method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvoke(DataPortalEvent e){

	}

	/**
	 * Called by the server-side DataPortal after calling the requested DataPortal_xyz
	 * method.
	 * 
	 * @param e    The DataPortalContext Object passed to the DataPortal.
	 */
	protected void DataPortal_OnDataPortalInvokeComplete(DataPortalEvent e){

	}

	protected void DataPortal_Update() throws NotSupportedException{
		throw new NotSupportedException(Resources.getUpdateNotSupportedException());
	}

	/**
	 * Compares this Object for equality with another Object, using the results of
	 * <see cref="GetIdValue"/> to determine equality.
	 * 
	 * @param obj    The Object to be compared.
	 */
	public boolean Equals(Object obj){
		
		if (obj instanceof ReadOnlyBase<?>)
		{
			Object id = getIdValue();
			if (id == null)
				throw new IllegalArgumentException(Resources.getGetIdValueCantBeNull());
			return id.equals(((ReadOnlyBase<?>)obj).getIdValue());
		}
		else
			return false;
	}

	/**
	 * Creates a clone of the Object.
	 * 
	 *      @returns A new Object containing the exact data of the original Object.
	 */
	public Object getClone(){
		return ObjectCloner.clone(this);
	}

	/**
	 * Returns a hash code value for this Object, based on the results of <see
	 * cref="GetIdValue"/>.
	 */
	public int GetHashCode(){
		Object id = getIdValue();
		if (id == null)
			throw new IllegalArgumentException(Resources.getGetIdValueCantBeNull());
		return id.hashCode();
	}

	/**
	 * Override this method to return a unique identifying vlaue for this Object.
	 * 
	 *      @remark If you can not provide a unique identifying value, it is best if
	 * you can generate such a unique value (even temporarily). If you can not do that,
	 * then return
	 *      <see langword="Nothing"/> and then manually override the
	 *      <see cref="Equals"/>, <see cref="GetHashCode"/> and
	 *      <see cref="ToString"/> methods in your business Object.
	 */
	protected abstract Object getIdValue();

	/**
	 * Override this method to set up event handlers so user code in a partial class
	 * can respond to events raised by generated code.
	 */
	protected void Initialize(){
		/* allows subclass to initialize events before any other activity occurs */
	}

	/**
	 * This method is called on a newly deserialized Object after deserialization is
	 * complete.
	 * 
	 * @param context    Serialization context Object.
	 */
//	protected void OnDeserialized(StreamingContext context){
//		// do nothing - this is here so a subclass
//		// could override if needed
//	}
//
//	/**
//	 * 
//	 * @param context
//	 */
//	private void OnDeserializedHandler(StreamingContext context){
//		OnDeserialized(context);
//		addAuthorizationRules();
//		if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
//		{
//			synchronized (this.getClass())
//			{
//				if (!SharedAuthorizationRules.RulesExistFor(this.getClass()))
//				{
//					SharedAuthorizationRules.GetManager(this.getClass(), true);
//					addAuthorizationRules();
//				}
//			}
//		}
//	}

	/**
	 * 
	 * @param propertyName
	 */
//	private boolean canWriteProperty(String propertyName){
//		return false;
//	}

	/**
	 * Returns a text representation of this Object by returning the <see
	 * cref="GetIdValue"/> value in text form.
	 */
	public String toString(){
		Object id = getIdValue();
		if (id == null)
			throw new IllegalArgumentException(Resources.getGetIdValueCantBeNull());
		return id.toString();
	}

	private void VerifyAuthorizationCache(HttpServletRequest request){
		if (_readResultCache == null)
			_readResultCache = new HashMap<String, Boolean>();
		if (_executeResultCache == null)
			_executeResultCache = new HashMap<String, Boolean>();
		if (!ApplicationContext.getUser(request).equals(_lastPrincipal))
		{
			// the principal has changed - reset the cache
			_readResultCache.clear();
			_executeResultCache.clear();
			_lastPrincipal = ApplicationContext.getUser(request);
		}
	}

}