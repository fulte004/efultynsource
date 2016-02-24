package Csla.Workflow;

/**
 * Manages execution of a WF workflow.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:51 PM
 */
public class WorkflowManager {

	private WorkflowInstance _instance = null;
	private WorkflowStatus _status = WorkflowStatus.Initializing;
	private ManualResetEvent _waitHandle = null;
	private Exception _workflowError = null;
	private WorkflowRuntime _workflowRuntime = null;



	public void finalize() throws Throwable {

	}

	/**
	 * Creates an instance of the object.
	 */
	public WorkflowManager(){

	}

	/**
	 * Creates an instance of the object.
	 * 
	 * @param workflowRuntime    A workflow runtime instance to use for all operations.
	 */
	public WorkflowManager(WorkflowRuntime workflowRuntime){
		_workflowRuntime = workflowRuntime;
	}

	/**
	 * Loads a workflow from persisted storage and resumes asynchronous execution of
	 * that workflow.
	 * 
	 * @param instanceId    Id of the workflow instance to load and resume.
	 */
	public void BeginResumeWorkflow(Guid instanceId){
		InitializeRuntime();
		        
		        if (!_workflowRuntime.IsStarted)
		          _workflowRuntime.StartRuntime();
		        
		        // get workflow instance
		        _instance = _workflowRuntime.GetWorkflow(instanceId);
		        
		        BeginResumeWorkflow();
	}

	/**
	 * Resumes asynchronous execution of the current workflow.
	 */
	public void BeginResumeWorkflow(){
		InitializeRuntime();
		        
		        if (!_workflowRuntime.IsStarted)
		          _workflowRuntime.StartRuntime();
		        
		        // execute workflow
		        _instance.Resume();
		        _status = WorkflowStatus.Executing;
	}

	/**
	 * Asynchronously starts executing workflow.
	 * 
	 * @param typeName    Assembly qualified type name of the workflow.
	 */
	public void BeginWorkflow(string typeName){
		Type workflowType = Type.GetType(typeName);
		        BeginWorkflow(workflowType, null);
	}

	/**
	 * Asynchronously starts executing workflow.
	 * 
	 * @param workflowType    Type object referencing the workflow.
	 */
	public void BeginWorkflow(Type workflowType){
		BeginWorkflow(workflowType, null);
	}

	/**
	 * Asynchronously starts executing workflow.
	 * 
	 * @param typeName    Assembly qualified type name of the workflow.
	 * @param parameters    Name/value list of parameters to be passed to the workflow
	 * instance.
	 */
	public void BeginWorkflow(string typeName, Dictionary<string, object> parameters){
		Type workflowType = Type.GetType(typeName);
		        BeginWorkflow(workflowType, parameters);
	}

	/**
	 * Asynchronously starts executing workflow.
	 * 
	 * @param workflowType    Type object referencing the workflow.
	 * @param parameters    Name/value list of parameters to be passed to the workflow
	 * instance.
	 */
	public void BeginWorkflow(Type workflowType, Dictionary<string, object> parameters){
		InitializeRuntime();
		        
		        if (!_workflowRuntime.IsStarted)
		          _workflowRuntime.StartRuntime();
		        
		        // create workflow instance
		        if (parameters != null)
		          _instance = _workflowRuntime.CreateWorkflow(
		            workflowType,
		            parameters);
		        else
		          _instance = _workflowRuntime.CreateWorkflow(
		            workflowType);
		        
		        // execute workflow
		        _instance.Start();
		        _status = WorkflowStatus.Executing;
	}

	/**
	 * Diposes the workflow runtime.
	 */
	public void DisposeRuntime(){
		// dispose runtime
		        if (_workflowRuntime != null) 
		          _workflowRuntime.Dispose();
		        _workflowRuntime = null;
		        if (_waitHandle != null)
		          _waitHandle.Close();
		        _waitHandle = null;
		        _instance = null;
	}

	/**
	 * Gets the error exception returned from the workflow if the workflow terminated
	 * abnormally.
	 */
	public Exception Error(){
		get
		              {
		                return _workflowError;
		              }
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param typeName    Assembly qualified type name of the workflow.
	 */
	public void ExecuteWorkflow(string typeName){
		Type workflowType = Type.GetType(typeName);
		        ExecuteWorkflow(workflowType);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param workflowType    Type object referencing the workflow.
	 */
	public void ExecuteWorkflow(Type workflowType){
		ExecuteWorkflow(workflowType, null, true);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param typeName    Assembly qualified type name of the workflow.
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void ExecuteWorkflow(string typeName, bool disposeRuntime){
		Type workflowType = Type.GetType(typeName);
		        ExecuteWorkflow(workflowType, disposeRuntime);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param workflowType    Type object referencing the workflow.
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void ExecuteWorkflow(Type workflowType, bool disposeRuntime){
		ExecuteWorkflow(workflowType, null, disposeRuntime);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param typeName    Assembly qualified type name of the workflow.
	 * @param parameters    Name/value list of parameters to be passed to the workflow
	 * instance.
	 */
	public void ExecuteWorkflow(string typeName, Dictionary<string, object> parameters){
		Type workflowType = Type.GetType(typeName);
		        ExecuteWorkflow(workflowType, parameters, true);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param typeName    Assembly qualified type name of the workflow.
	 * @param parameters    Name/value list of parameters to be passed to the workflow
	 * instance.
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void ExecuteWorkflow(string typeName, Dictionary<string, object> parameters, bool disposeRuntime){
		Type workflowType = Type.GetType(typeName);
		        ExecuteWorkflow(workflowType, parameters, disposeRuntime);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param workflowType    Type object referencing the workflow.
	 * @param parameters    Name/value list of parameters to be passed to the workflow
	 * instance.
	 */
	public void ExecuteWorkflow(Type workflowType, Dictionary<string, object> parameters){
		ExecuteWorkflow(workflowType, parameters, true);
	}

	/**
	 * Synchronously executes a workflow.
	 * 
	 * @param workflowType    Type object referencing the workflow.
	 * @param parameters    Name/value list of parameters to be passed to the workflow
	 * instance.
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void ExecuteWorkflow(Type workflowType, Dictionary<string, object> parameters, bool disposeRuntime){
		BeginWorkflow(workflowType, parameters);
		        WaitForEnd(disposeRuntime);
	}

	/**
	 * Initializes the workflow runtime.
	 */
	public void InitializeRuntime(){
		if (_workflowRuntime != null)
		          return;
		        
		        _waitHandle = new ManualResetEvent(false);
		        
		        // initialize workflow runtime
		        _workflowRuntime = new WorkflowRuntime();
		        _workflowRuntime.WorkflowCompleted +=
		          delegate(object sender, WorkflowCompletedEventArgs e)
		          {
		            _status = WorkflowStatus.Completed;
		            _waitHandle.Set();
		          };
		        _workflowRuntime.WorkflowTerminated +=
		          delegate(object sender, WorkflowTerminatedEventArgs e)
		          {
		            _status = WorkflowStatus.Terminated;
		            _workflowError = e.Exception;
		            _waitHandle.Set();
		          };
		        _workflowRuntime.WorkflowSuspended +=
		          delegate(object sender, WorkflowSuspendedEventArgs e)
		          {
		            _status = WorkflowStatus.Suspended;
		            _waitHandle.Set();
		          };
		        _workflowRuntime.WorkflowAborted +=
		          delegate(object sender, WorkflowEventArgs e)
		          {
		            _status = WorkflowStatus.Aborted;
		            _waitHandle.Set();
		          };
		        _workflowRuntime.WorkflowIdled +=
		          delegate(object sender, WorkflowEventArgs e)
		          {
		            _status = WorkflowStatus.Idled;
		            _waitHandle.Set();
		          };
	}

	/**
	 * Resumes synchronous execution of the workflow.
	 */
	public void ResumeWorkflow(){
		ResumeWorkflow(true);
	}

	/**
	 * Resumes synchronous execution of the workflow.
	 * 
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void ResumeWorkflow(bool disposeRuntime){
		BeginResumeWorkflow();
		        WaitForEnd(disposeRuntime);
	}

	/**
	 * Resumes synchronous execution of a workflow.
	 * 
	 * @param instanceId    Id of the workflow instance to resume.
	 */
	public void ResumeWorkflow(Guid instanceId){
		ResumeWorkflow(instanceId, true);
	}

	/**
	 * Resumes synchronous execution of a workflow.
	 * 
	 * @param instanceId    Id of the workflow instance to resume.
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void ResumeWorkflow(Guid instanceId, bool disposeRuntime){
		BeginResumeWorkflow(instanceId);
		        WaitForEnd(disposeRuntime);
	}

	/**
	 * Gets the workflow runtime instance that is executing the workflow.
	 */
	public WorkflowRuntime RuntimeInstance(){
		get
		              {
		                return _workflowRuntime;
		              }
	}

	/**
	 * Gets the current status of the workflow.
	 */
	public WorkflowStatus Status(){
		get
		              {
		                return _status;
		              }
	}

	/**
	 * Waits for the workflow to complete or terminate.
	 */
	public void WaitForEnd(){
		// wait for workflow to complete
		        _waitHandle.WaitOne();
	}

	/**
	 * Waits for the workflow to complete or terminate.
	 * 
	 * @param disposeRuntime    Value indicating whether to dispose the WF runtime
	 * when workflow completes.
	 */
	public void WaitForEnd(bool disposeRuntime){
		WaitForEnd();
		        
		        if (disposeRuntime)
		          DisposeRuntime();
	}

	/**
	 * Gets the workflow instance being executed.
	 */
	public WorkflowInstance WorkflowInstance(){
		get
		              {
		                return _instance;
		              }
	}

}