using System;
using System.ComponentModel.Composition;

namespace MyTest.Library.WindowsService
{
    [MetadataAttribute]
    [AttributeUsage(AttributeTargets.Class, AllowMultiple=false)]
    public class ServiceMetadataAttribute : ExportAttribute
    {
        public ServiceMetadataAttribute() 
            : base(typeof(IService)) 
        {
        }

        public string Name { get; set; }
    }
}
