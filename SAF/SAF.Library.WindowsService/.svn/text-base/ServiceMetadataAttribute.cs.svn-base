using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel.Composition;

namespace SAF.Library.WindowsService
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
