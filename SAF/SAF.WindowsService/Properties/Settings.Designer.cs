﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.237
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace SAF.WindowsService.Properties {
    
    
    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.VisualStudio.Editors.SettingsDesigner.SettingsSingleFileGenerator", "10.0.0.0")]
    internal sealed partial class Settings : global::System.Configuration.ApplicationSettingsBase {
        
        private static Settings defaultInstance = ((Settings)(global::System.Configuration.ApplicationSettingsBase.Synchronized(new Settings())));
        
        public static Settings Default {
            get {
                return defaultInstance;
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("bin")]
        public string AssemblyPath {
            get {
                return ((string)(this["AssemblyPath"]));
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("SAF.WindowsService")]
        public string ServiceName {
            get {
                return ((string)(this["ServiceName"]));
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute(@"<?xml version=""1.0"" encoding=""utf-16""?>
<Service xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" type=""SAF.WindowsService.EmptyService,SAF.WindowsService"" name=""empty"">
  <File>C:\temp\EmptyService.txt</File>
  <RunAs InheritIdentity=""false"">
    <Domain>FNFIS</Domain>
    <User>svc-filewatcher-test</User>
    <Password>WkwqSaHJk9zS35BfHTS3YZZvTFJBMmfrXVdAJ83o8EKGyh4xNh6aQ5qPGHxo7Yb+</Password>
  </RunAs>
</Service>")]
        public global::SAF.Library.WindowsService.Service EmptyService {
            get {
                return ((global::SAF.Library.WindowsService.Service)(this["EmptyService"]));
            }
        }
    }
}
