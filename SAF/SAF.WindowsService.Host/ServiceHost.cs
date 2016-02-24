using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.ServiceProcess;
using System.Xml;
using SAF.Library.WindowsService;
using System.Threading;
using System.Configuration;
using System.ComponentModel.Composition.Hosting;
using System.ComponentModel.Composition;
using System.Collections.ObjectModel;
using SAF.WindowsService.Properties;

namespace SAF.WindowsService
{
	public class ServiceHost : System.ServiceProcess.ServiceBase
	{
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		/// 
		private System.ComponentModel.Container components = null;

        private string assemblyPath;
        private DirectoryCatalog catalog;
        private CompositionContainer container;
        private ServiceEngine engine = null;

		public ServiceHost()
		{
			// This call is required by the Windows.Forms Component Designer.
			InitializeComponent();

			// TODO: Add any initialization after the InitComponent call
		}

		// The main entry point for the process
		static void Main()
		{
			System.ServiceProcess.ServiceBase[] ServicesToRun;
	
			ServicesToRun = new System.ServiceProcess.ServiceBase[] { new ServiceHost() };
			System.ServiceProcess.ServiceBase.Run(ServicesToRun);
		}

		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			components = new System.ComponentModel.Container();
			this.ServiceName = Settings.Default.ServiceName;
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		/// <summary>
		/// Set things in motion so your service can do its work.
		/// </summary>
		protected override void OnStart(string[] args)
		{
            assemblyPath = Settings.Default.AssemblyPath;
            catalog = new DirectoryCatalog(AppDomain.CurrentDomain.BaseDirectory + assemblyPath);
            container = new CompositionContainer(catalog);

            engine = container.GetExportedValue<ServiceEngine>();
            engine.OnStart(args);
		}

		/// <summary>
		/// Stop this service.
		/// </summary>
		protected override void OnStop()
		{
            engine.OnStop();
		}
	}
}
