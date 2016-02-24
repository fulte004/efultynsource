package Csla;

/**
 * Provides a list of possible transactional technologies to be used by the server-
 * side DataPortal.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:47 PM
 */
public enum TransactionalTypes {
	/**
	 * Causes the server-side DataPortal to use Enterprise Services (COM+)
	 * transactions.
	 * 
	 *      
	 */
	EnterpriseServices,
	/**
	 * Causes the server-side DataPortal to use System.Transactions TransactionScope
	 * style transactions.
	 * 
	 *      
	 */
	TransactionScope,
	/**
	 * Causes the server-side DataPortal to use no explicit transactional technology.
	 * 
	 *      @remark This option allows the business developer to implement their own
	 * transactions. Common options include ADO.NET transactions and System.
	 * Transactions TransactionScope.
	 *      
	 */
	Manual
}