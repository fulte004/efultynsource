using System;
using System.IO;
using SAF.Library.WindowsService;
using System.Threading;
using System.Xml;
using SAF.WindowsService.Properties;

namespace SAF.WindowsService
{
	/// <summary>
	/// EmptyService is a sample implementation of ISerivce class that is
	/// pluggable to SAF.WindowsService.  When started, EmplyService writes out some text to 
	/// a file every 3 seconds.
	/// </summary>
    /// 
    //[ServiceMetadata(Name="EmptyService")]
	public class EmptyService : IService
	{
		private bool continueLoop = true;
		public EmptyService()
		{
		}

		public void Initialize()
		{
		}

		public void Start()
		{
			StreamWriter sr = null ;
			try
			{
				sr = new StreamWriter(ServiceInfo.File);
				while (continueLoop)
				{
					try
					{
						Thread.Sleep(3000);		
						sr.WriteLine(System.DateTime.Now.ToLongTimeString());
						sr.Flush();
					}
					catch (ThreadInterruptedException){}
	
				}
			}
			finally
			{
				if (sr != null){sr.Close();}
				
			}

		}
		public void Stop()
		{
			continueLoop = false;
			Thread.CurrentThread.Interrupt();
		}

        public Service ServiceInfo {
            get { return Settings.Default.EmptyService; }
        }
    }
}
