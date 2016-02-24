using System;
using System.ComponentModel.Composition.Hosting;
using GalaSoft.MvvmLight.Messaging;
using Microsoft.Practices.EnterpriseLibrary.Common.Configuration;
using Microsoft.Practices.EnterpriseLibrary.Logging;
using Microsoft.Practices.EnterpriseLibrary.Security.Cryptography;
using MyTest.WindowsService.Test.Console.Properties;
using MyTest.WindowsService.Events;

namespace MyTest.WindowsService.Test.Console
{
    class Program
    {
        private static LogWriter defaultWriter;
        private string assemblyPath;
        private DirectoryCatalog catalog;
        private CompositionContainer container;
        private ServiceEngine engine = null;

        public Program() {
            defaultWriter = EnterpriseLibraryContainer.Current.GetInstance<LogWriter>();
        }

        private void GetEncryptedPassword() {
            CryptographyManager crypto = EnterpriseLibraryContainer.Current.GetInstance<CryptographyManager>();
            System.Console.Write("\nEnter new password:");
            string password = System.Console.ReadLine();
            string encPassword = crypto.EncryptSymmetric("AesManaged", password);
            System.Console.WriteLine("New encrypted password is: " + encPassword);
            System.Console.ReadKey();
        }

        static void Main(string[] args) {
            Program p = new Program();
            System.Console.Write("Select mode: [P]assword encryptor, [T]est service: ");
            ConsoleKeyInfo key = new ConsoleKeyInfo();
            key = System.Console.ReadKey();
            switch (key.Key) {
                case ConsoleKey.P:
                    p.GetEncryptedPassword();
                    break;
                default:
                    TestService(args, p);
                    break;
            }
        }

        private static void TestService(string[] args, Program p) {
            ConsoleKeyInfo key = new ConsoleKeyInfo();
            p.OnStart(args);
            System.Threading.Thread.Sleep(3000);
            System.Console.Write("\nServices started.\nE[X]it? ");
            while (key.Key != ConsoleKey.X) {
                key = System.Console.ReadKey();
            }
            System.Console.WriteLine("\nStopping services...");
            p.OnStop();
            System.Console.Write("Services have stopped.  Press any key to exit.");
            key = System.Console.ReadKey();
        }

        protected void OnStart(string[] args) {
            Messenger.Default.Register<LogEventMessage>(this, OnLogMessage);
            assemblyPath = Settings.Default.AssemblyPath;
            catalog = new DirectoryCatalog(AppDomain.CurrentDomain.BaseDirectory + assemblyPath);
            container = new CompositionContainer(catalog);

            engine = container.GetExportedValue<ServiceEngine>();
            engine.OnStart(args);
        }

        protected void OnStop() {
            engine.OnStop();
        }

        // This solution makes use of the Microsoft Enterprise Library 5.0 Logging Application Block.
        // Log4net or any other logger may be used instead, just update the app.config file, the references, and
        // substitue the new logging code here.  Please note: MS EL's Cryptography Application Block is also used.
        protected void OnLogMessage(LogEventMessage e) {
            System.Console.WriteLine(string.Format("\n{0}: {1}", e.Source, e.Description));
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
