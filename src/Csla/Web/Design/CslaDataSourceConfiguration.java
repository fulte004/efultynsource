package Csla.Web.Design;

/**
 * CslaDataSource configuration form.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:30 PM
 */
public class CslaDataSourceConfiguration extends Form {

	private DataSourceControl _control;



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Create instance of object.
	 */
	public CslaDataSourceConfiguration(){
		InitializeComponent();
	}

	/**
	 * Create instance of object.
	 * 
	 * @param control    Reference to the data source control.
	 * @param oldTypeName    Existing type name.
	 */
	public CslaDataSourceConfiguration(DataSourceControl control, string oldTypeName){
		_control = control;
		          DiscoverTypes();
		          this.TypeComboBox.Text = oldTypeName;
	}

	private void DiscoverTypes(){
		// try to get a reference to the type discovery service
		          ITypeDiscoveryService discovery = null;
		          if (_control.Site != null)
		            discovery = (ITypeDiscoveryService)_control.Site.GetService(typeof(ITypeDiscoveryService));
		          
		          if (discovery != null)
		          {
		            // saves the cursor and sets the wait cursor
		            Cursor previousCursor = Cursor.Current;
		            Cursor.Current = Cursors.WaitCursor;
		            try
		            {
		              // gets all types using the type discovery service
		              ICollection types = discovery.GetTypes(typeof(object), true);
		              TypeComboBox.BeginUpdate();
		              TypeComboBox.Items.Clear();
		              // adds the types to the list
		              foreach (Type type in types)
		              {
		                if (type.Assembly.FullName.Substring(0, type.Assembly.FullName.IndexOf(",")) != "Csla" &&
		                  typeof(Csla.Core.IBusinessObject).IsAssignableFrom(type))
		                {
		                  string name = type.AssemblyQualifiedName;
		                  if (name.Substring(name.Length - 19, 19) == "PublicKeyToken=null")
		                    name = name.Substring(0, name.IndexOf(",", name.IndexOf(",") + 1));
		                  TypeComboBox.Items.Add(name);
		                }
		              }
		            }
		            finally
		            {
		              Cursor.Current = previousCursor;
		              TypeComboBox.EndUpdate();
		            }
		          }
	}

	/**
	 * Gets the type name entered by the user.
	 */
	public string TypeName(){
		get { return this.TypeComboBox.Text; }
	}

}

/**
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:31 PM
 */
public class CslaDataSourceConfiguration {

	private System.Windows.Forms.Button cancelButton;
	/**
	 * Required designer variable.
	 * 
	 *          
	 */
	private System.ComponentModel.IContainer components = null;
	private System.Windows.Forms.Label label2;
	private System.Windows.Forms.Button okButton;
	private System.Windows.Forms.ComboBox TypeComboBox;

	public CslaDataSourceConfiguration(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Clean up any resources being used.
	 * 
	 * @param disposing    true if managed resources should be disposed; otherwise,
	 * false.
	 */
	protected void Dispose(bool disposing){
		if (disposing && (components != null))
		          {
		            components.Dispose();
		          }
		          base.Dispose(disposing);
	}

	/**
	 * Required method for Designer support - do not modify the contents of this
	 * method with the code editor.
	 */
	private void InitializeComponent(){
		System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CslaDataSourceConfiguration));
		          this.label2 = new System.Windows.Forms.Label();
		          this.okButton = new System.Windows.Forms.Button();
		          this.cancelButton = new System.Windows.Forms.Button();
		          this.TypeComboBox = new System.Windows.Forms.ComboBox();
		          this.SuspendLayout();
		          // 
		          // label2
		          // 
		          resources.ApplyResources(this.label2, "label2");
		          this.label2.Name = "label2";
		          // 
		          // okButton
		          // 
		          resources.ApplyResources(this.okButton, "okButton");
		          this.okButton.DialogResult = System.Windows.Forms.DialogResult.OK;
		          this.okButton.Name = "okButton";
		          this.okButton.UseVisualStyleBackColor = true;
		          // 
		          // cancelButton
		          // 
		          resources.ApplyResources(this.cancelButton, "cancelButton");
		          this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
		          this.cancelButton.Name = "cancelButton";
		          this.cancelButton.UseVisualStyleBackColor = true;
		          // 
		          // TypeComboBox
		          // 
		          resources.ApplyResources(this.TypeComboBox, "TypeComboBox");
		          this.TypeComboBox.FormattingEnabled = true;
		          this.TypeComboBox.Name = "TypeComboBox";
		          this.TypeComboBox.Sorted = true;
		          // 
		          // CslaDataSourceConfiguration
		          // 
		          this.AcceptButton = this.okButton;
		          resources.ApplyResources(this, "$this");
		          this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
		          this.CancelButton = this.cancelButton;
		          this.ControlBox = false;
		          this.Controls.Add(this.TypeComboBox);
		          this.Controls.Add(this.cancelButton);
		          this.Controls.Add(this.okButton);
		          this.Controls.Add(this.label2);
		          this.MaximizeBox = false;
		          this.MinimizeBox = false;
		          this.Name = "CslaDataSourceConfiguration";
		          this.ShowInTaskbar = false;
		          this.ResumeLayout(false);
		          this.PerformLayout();
	}

}