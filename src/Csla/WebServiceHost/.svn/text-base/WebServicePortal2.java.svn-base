package Csla.WebServiceHost;

/**
 * <remarks/>
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:50 PM
 */
public class WebServicePortal2 extends System.Web.Services.Protocols.SoapHttpClientProtocol {

	private System.Threading.SendOrPostCallback CreateOperationCompleted;
	private System.Threading.SendOrPostCallback DeleteOperationCompleted;
	private System.Threading.SendOrPostCallback FetchOperationCompleted;
	private System.Threading.SendOrPostCallback UpdateOperationCompleted;
	private bool useDefaultCredentialsSetExplicitly;



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * <remarks/>
	 */
	public WebServicePortal2(){
		this.Url = global::Csla.Properties.Settings.Default.Csla_WebServiceHost_WebServicePortal;
		        if ((this.IsLocalFileSystemWebService(this.Url) == true)) {
		            this.UseDefaultCredentials = true;
		            this.useDefaultCredentialsSetExplicitly = false;
		        }
		        else {
		            this.useDefaultCredentialsSetExplicitly = true;
		        }
	}

	/**
	 * <remarks/>
	 * 
	 * @param userState
	 */
	public void CancelAsync(object userState){
		base.CancelAsync(userState);
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public byte[] Create(byte[] requestData){
		object[] results = this.Invoke("Create", new object[] {
		                    requestData});
		        return ((byte[])(results[0]));
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public void CreateAsync(byte[] requestData){
		this.CreateAsync(requestData, null);
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 * @param userState
	 */
	public void CreateAsync(byte[] requestData, object userState){
		if ((this.CreateOperationCompleted == null)) {
		            this.CreateOperationCompleted = new System.Threading.SendOrPostCallback(this.OnCreateOperationCompleted);
		        }
		        this.InvokeAsync("Create", new object[] {
		                    requestData}, this.CreateOperationCompleted, userState);
	}

	/**
	 * <remarks/>
	 */
	public CreateCompletedEventHandler CreateCompleted(){
		return null;
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public byte[] Delete(byte[] requestData){
		object[] results = this.Invoke("Delete", new object[] {
		                    requestData});
		        return ((byte[])(results[0]));
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public void DeleteAsync(byte[] requestData){
		this.DeleteAsync(requestData, null);
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 * @param userState
	 */
	public void DeleteAsync(byte[] requestData, object userState){
		if ((this.DeleteOperationCompleted == null)) {
		            this.DeleteOperationCompleted = new System.Threading.SendOrPostCallback(this.OnDeleteOperationCompleted);
		        }
		        this.InvokeAsync("Delete", new object[] {
		                    requestData}, this.DeleteOperationCompleted, userState);
	}

	/**
	 * <remarks/>
	 */
	public DeleteCompletedEventHandler DeleteCompleted(){
		return null;
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public byte[] Fetch(byte[] requestData){
		object[] results = this.Invoke("Fetch", new object[] {
		                    requestData});
		        return ((byte[])(results[0]));
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public void FetchAsync(byte[] requestData){
		this.FetchAsync(requestData, null);
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 * @param userState
	 */
	public void FetchAsync(byte[] requestData, object userState){
		if ((this.FetchOperationCompleted == null)) {
		            this.FetchOperationCompleted = new System.Threading.SendOrPostCallback(this.OnFetchOperationCompleted);
		        }
		        this.InvokeAsync("Fetch", new object[] {
		                    requestData}, this.FetchOperationCompleted, userState);
	}

	/**
	 * <remarks/>
	 */
	public FetchCompletedEventHandler FetchCompleted(){
		return null;
	}

	/**
	 * 
	 * @param url
	 */
	private bool IsLocalFileSystemWebService(string url){
		if (((url == null) 
		                    || (url == string.Empty))) {
		            return false;
		        }
		        System.Uri wsUri = new System.Uri(url);
		        if (((wsUri.Port >= 1024) 
		                    && (string.Compare(wsUri.Host, "localHost", System.StringComparison.OrdinalIgnoreCase) == 0))) {
		            return true;
		        }
		        return false;
	}

	/**
	 * 
	 * @param arg
	 */
	private void OnCreateOperationCompleted(object arg){
		if ((this.CreateCompleted != null)) {
		            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
		            this.CreateCompleted(this, new CreateCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
		        }
	}

	/**
	 * 
	 * @param arg
	 */
	private void OnDeleteOperationCompleted(object arg){
		if ((this.DeleteCompleted != null)) {
		            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
		            this.DeleteCompleted(this, new DeleteCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
		        }
	}

	/**
	 * 
	 * @param arg
	 */
	private void OnFetchOperationCompleted(object arg){
		if ((this.FetchCompleted != null)) {
		            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
		            this.FetchCompleted(this, new FetchCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
		        }
	}

	/**
	 * 
	 * @param arg
	 */
	private void OnUpdateOperationCompleted(object arg){
		if ((this.UpdateCompleted != null)) {
		            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
		            this.UpdateCompleted(this, new UpdateCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
		        }
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public byte[] Update(byte[] requestData){
		object[] results = this.Invoke("Update", new object[] {
		                    requestData});
		        return ((byte[])(results[0]));
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 */
	public void UpdateAsync(byte[] requestData){
		this.UpdateAsync(requestData, null);
	}

	/**
	 * <remarks/>
	 * 
	 * @param requestData
	 * @param userState
	 */
	public void UpdateAsync(byte[] requestData, object userState){
		if ((this.UpdateOperationCompleted == null)) {
		            this.UpdateOperationCompleted = new System.Threading.SendOrPostCallback(this.OnUpdateOperationCompleted);
		        }
		        this.InvokeAsync("Update", new object[] {
		                    requestData}, this.UpdateOperationCompleted, userState);
	}

	/**
	 * <remarks/>
	 */
	public UpdateCompletedEventHandler UpdateCompleted(){
		return null;
	}

	public string Url(){
		get {
		            return base.Url;
		        }
		        set {
		            if ((((this.IsLocalFileSystemWebService(base.Url) == true) 
		                        && (this.useDefaultCredentialsSetExplicitly == false)) 
		                        && (this.IsLocalFileSystemWebService(value) == false))) {
		                base.UseDefaultCredentials = false;
		            }
		            base.Url = value;
		        }
	}

	public bool UseDefaultCredentials(){
		get {
		            return base.UseDefaultCredentials;
		        }
		        set {
		            base.UseDefaultCredentials = value;
		            this.useDefaultCredentialsSetExplicitly = true;
		        }
	}

}