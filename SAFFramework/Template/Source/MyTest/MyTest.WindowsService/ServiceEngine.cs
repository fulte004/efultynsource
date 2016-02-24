using System;
using System.Collections;
using System.Collections.ObjectModel;
using System.ComponentModel.Composition;
using System.Threading;
using GalaSoft.MvvmLight.Messaging;
using Microsoft.Practices.EnterpriseLibrary.Common.Configuration;
using Microsoft.Practices.EnterpriseLibrary.Security.Cryptography;
using MyTest.Library.WindowsService;
using MyTest.WindowsService.Events;

namespace MyTest.WindowsService
{
    [Export]
    public class ServiceEngine
    {
        [ImportMany(AllowRecomposition = true)]
        public ObservableCollection<Lazy<IService, IServiceMetadata>> Services { get; set; }
        private static CryptographyManager crypto = EnterpriseLibraryContainer.Current.GetInstance<CryptographyManager>();

        private ArrayList threadArray = new ArrayList();
        private ArrayList instanceArray = new ArrayList();
        /// <summary>
        /// Set things in motion so your service can do its work.
        /// </summary>
        public void OnStart(string[] args) {
            ServiceEngineEvents.StartingServices.Source = this.GetType().Name;
            Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.StartingServices);

            //obtain the configuration information for MyTest.Service
            //loop through service nodes and start them one by one
            foreach (var item in Services) {
                try {
                    IService service = item.Value;
                    //initialize the service
                    service.Initialize();
                    instanceArray.Add(service);

                    //create SecuritySwitchThread object to process the service
                    ThreadStart ts = new ThreadStart(service.Start);
                    SecuritySwitchThread sst = new SecuritySwitchThread(ts, service.ServiceInfo.RunAs, crypto);

                    //start the SecuritySwitchThread's thread.
                    sst.Start();
                    threadArray.Add(sst.BaseThread);
                    Thread t = new Thread(ts);

                }
                catch (Exception ex) {
                    ServiceEngineEvents.OnServiceEngineError.Source = this.GetType().Name;
                    ServiceEngineEvents.OnServiceEngineError.FormatArgs = new object[] { ex.Source, "OnStart", ex.ToString() };
                    Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.OnServiceEngineError);
                }
            }
            ServiceEngineEvents.ServicesStarted.Source = this.GetType().Name;
            Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.ServicesStarted);
        }

        /// <summary>
        /// delegate used when invoke the OnStop method asynchronous during service shut down.
        /// </summary>
        public delegate void OnStopDelegate();

        /// <summary>
        /// Stop this service.
        /// </summary>
        public void OnStop() {
            ServiceEngineEvents.StoppingServices.Source = this.GetType().Name;
            Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.StoppingServices);
            foreach (object o in instanceArray) {
                try {
                    IService service = (IService)o;
                    if (service != null) {
                        //invoke the delegate asynchronous to stop each started service.
                        OnStopDelegate osd = new OnStopDelegate(service.Stop);
                        osd.BeginInvoke(null, null);
                    }
                }
                catch (Exception ex) {
                    ServiceEngineEvents.OnServiceEngineError.Source = this.GetType().Name;
                    ServiceEngineEvents.OnServiceEngineError.FormatArgs = new object[] { ex.Source, "OnStop", ex.ToString() };
                    Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.OnServiceEngineError);
                }
            }

            //give sometime for the each instance to shut down gracefully
            Thread.Sleep(5000);
            foreach (object o in threadArray) {
                try {
                    Thread t = (Thread)o;
                    if (t != null) {
                        //if the thread is still live at this point, shut it down forcefully.
                        if (t.IsAlive == true) {
                            t.Abort();
                        }
                    }
                }
                catch (Exception ex) {
                    ServiceEngineEvents.OnServiceEngineError.Source = this.GetType().Name;
                    ServiceEngineEvents.OnServiceEngineError.FormatArgs = new object[] { ex.Source, "OnStop", ex.ToString() };
                    Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.OnServiceEngineError);
                }
            }
            ServiceEngineEvents.ServicesStopped.Source = this.GetType().Name;
            Messenger.Default.Send<LogEventMessage>(ServiceEngineEvents.ServicesStopped);
        }

    }
}
