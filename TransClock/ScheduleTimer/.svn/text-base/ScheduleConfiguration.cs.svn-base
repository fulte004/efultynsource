using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace Schedule
{
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ScheduleConfiguration
    {
        public ScheduleConfiguration() {
            ScheduledEvents = new List<ScheduledEvent>();
        }
        [XmlAttribute]
        public bool DoWorkOnStart { get; set; }
        public List<ScheduledEvent> ScheduledEvents { get; set; }
    }

    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ScheduledEvent
    {
        public ScheduledTime ScheduledTime { get; set; }
        public SingleEvent SingleEvent { get; set; }
        public SimpleInterval SimpleInterval { get; set; }
    }
}
