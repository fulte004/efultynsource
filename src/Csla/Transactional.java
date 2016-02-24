package Csla;

/**
 * Marks a DataPortal_XYZ method to run within the specified transactional context.
 * 
 * 
 *    @remark  Each business object method may be marked with this attribute to
 * indicate which type of transactional technology should be used by the server-
 * side DataPortal. The possible options are listed in the
 *    <see cref="TransactionalTypes">TransactionalTypes</see> enum.
 *     If the Transactional attribute is not applied to a DataPortal_XYZ method
 * then the
 *    <see cref="TransactionalTypes.Manual">Manual</see> option is assumed.
 *     If the Transactional attribute is applied with no explicit choice for
 * transactionType then the
 *    <see cref="TransactionalTypes.EnterpriseServices">EnterpriseServices</see>
 * option is assumed.
 *     Both the EnterpriseServices and TransactionScope options provide 2-phase
 * distributed transactional support.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:46 PM
 */
public @interface Transactional  {
	/**
	 * Gets the type of transaction requested by the business object method.
	 */
	public TransactionalTypes getTransactionType();

}