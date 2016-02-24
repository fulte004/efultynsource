using System.IO;
using System.Threading;
using GalaSoft.MvvmLight.Messaging;
using MyTest.Library.WindowsService;
using MyTest.Library.WindowsService.Events;
using MyTest.WindowsService.EmptyService.Properties;
using MyTest.WindowsService.Events;

namespace MyTest.WindowsService.EmptyService
{
    /// <summary>
    /// EmptyService is a sample implementation of ISerivce class that is
    /// pluggable to MyTest.WindowsService.  When started, EmplyService writes out some text to 
    /// a file every 3 seconds.
    /// </summary>
    [ServiceMetadata(Name = "EmptyService")]
    public class EmptyService : IService
    {
        private bool continueLoop = true;

        public void Initialize() {
        }

        public void Start() {
            StreamWriter sr = null;
            try {
                StandardServiceEvents.ServiceStarted.Source = this.GetType().Name;
                StandardServiceEvents.ServiceStarted.FormatArgs = new object[] { Settings.Default.ServiceName };
                Messenger.Default.Send<LogEventMessage>(StandardServiceEvents.ServiceStarted);

                sr = new StreamWriter(ServiceInfo.File);
                while (continueLoop) {
                    try {
                        Thread.Sleep(3000);
                        sr.WriteLine(System.DateTime.Now.ToLongTimeString());
                        sr.Flush();
                    }
                    catch (ThreadInterruptedException) {
                        StandardServiceEvents.ServiceInterruptedWarning.Source = this.GetType().Name;
                        StandardServiceEvents.ServiceInterruptedWarning.FormatArgs = new object[] { Settings.Default.ServiceName };
                        Messenger.Default.Send<LogEventMessage>(StandardServiceEvents.ServiceInterruptedWarning);
                    }

                }
            }
            finally {
                if (sr != null) { sr.Close(); }

            }

        }
        public void Stop() {
            continueLoop = false;
            StandardServiceEvents.ServiceStopped.Source = this.GetType().Name;
            StandardServiceEvents.ServiceStopped.FormatArgs = new object[] { Settings.Default.ServiceName };
            Messenger.Default.Send<LogEventMessage>(StandardServiceEvents.ServiceStopped);
            Thread.CurrentThread.Interrupt();
        }

        public Service ServiceInfo {
            get { return Settings.Default.EmptyService; }
        }
    }
}
