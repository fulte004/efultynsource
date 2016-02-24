package Csla.Workflow;

/**
 * Status of the workflow.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:51 PM
 */
public enum WorkflowStatus {
	/**
	 * Workflow is being initialized.
	 * 
	 *        
	 */
	Initializing,
	/**
	 * Workflow is currently executing.
	 * 
	 *        
	 */
	Executing,
	/**
	 * Workflow has completed normally.
	 * 
	 *        
	 */
	Completed,
	/**
	 * Workflow has been idled.
	 * 
	 *        
	 */
	Idled,
	/**
	 * Workflow has terminated abnormally.
	 * 
	 *        
	 */
	Terminated,
	/**
	 * Workflow was aborted.
	 * 
	 *        
	 */
	Aborted,
	/**
	 * Workflow has been suspended.
	 * 
	 *        
	 */
	Suspended
}