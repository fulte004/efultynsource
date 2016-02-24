using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel.Composition.Hosting;
using Microsoft.Practices.EnterpriseLibrary.Security.Cryptography;
using Microsoft.Practices.EnterpriseLibrary.Common.Configuration;
using SAF.Library.WindowsService.Configuration;
using SAF.WindowsService.Test.Console.Properties;

namespace SAF.WindowsService.Test.Console
{
    class Program
    {
        private string assemblyPath;
        private DirectoryCatalog catalog;
        private CompositionContainer container;
        private ServiceEngine engine = null;

        public Program() {

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
            assemblyPath = Settings.Default.AssemblyPath;
            catalog = new DirectoryCatalog(AppDomain.CurrentDomain.BaseDirectory + assemblyPath);
            container = new CompositionContainer(catalog);

            engine = container.GetExportedValue<ServiceEngine>();
            engine.OnStart(args);
        }

        protected void OnStop() {
            engine.OnStop();
        }
    }
}
