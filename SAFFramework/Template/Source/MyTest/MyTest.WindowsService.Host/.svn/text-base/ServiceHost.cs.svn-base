using System;
using System.ComponentModel.Composition.Hosting;
//using FIS.Notifications.Messaging;
using Microsoft.Practices.EnterpriseLibrary.Common.Configuration;
using Microsoft.Practices.EnterpriseLibrary.Logging;
using MyTest.WindowsService.Properties;
using GalaSoft.MvvmLight.Messaging;
using MyTest.WindowsService.Events;

namespace MyTest.WindowsService
{
	public class ServiceHost : System.ServiceProcess.ServiceBase
	{
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		/// 
		private System.ComponentModel.Container components = null;
		private static LogWriter defaultWriter;

		private string assemblyPath;
		private DirectoryCatalog catalog;
		private CompositionContainer container;
		private ServiceEngine engine = null;

		public ServiceHost()
		{
			// This call is required by the Windows.Forms Component Designer.
			InitializeComponent();
			defaultWriter = EnterpriseLibraryContainer.Current.GetInstance<LogWriter>();
            //Messenger.Default.Register<LogMessage>(this, OnLogMessage);
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

		// This solution makes use of the Microsoft Enterprise Library 5.0 Logging Application Block.
		// Log4net or any other logger may be used instead, just update the app.config file, the references, and
		// substitue the new logging code here.  Please note: MS EL's Cryptography Application Block is also being used.
        protected void OnLogMessage(LogEventMessage e) {
            if (defaultWriter.IsLoggingEnabled()) {
                LogEntry entry = new LogEntry();
                entry.EventId = e.EventID;
                entry.Priority = e.Priority;
                entry.Message = e.Description;
                entry.ProcessName = e.Source;
                entry.Title = e.Title;
                entry.TimeStamp = DateTime.Now;
                if (e.Severity > System.Diagnostics.TraceEventType.Information) {
                    entry.Categories.Add("Errors"); // Allows configuration of other trace listeners for special notifications.
                }
                defaultWriter.Write(entry);
            }
        }
	}
}
