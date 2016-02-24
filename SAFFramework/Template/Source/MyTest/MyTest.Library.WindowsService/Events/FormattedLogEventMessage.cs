using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MyTest.WindowsService.Events
{
    public class FormattedLogEventMessage : LogEventMessage
    {
        private string nameFormat;
        private string descriptionFormat;
        public FormattedLogEventMessage(int eventID, string name, string description) {
            EventID = eventID;
            nameFormat = name;
            descriptionFormat = description;
        }
        public FormattedLogEventMessage(int eventID, string name, string description, System.Diagnostics.TraceEventType severity, int priority) {
            EventID = eventID;
            nameFormat = name;
            descriptionFormat = description;
            Priority = priority;
            Severity = severity;
        }
        public object[] FormatArgs { get; set; }
        public override string Title {
            get {
                return string.Format(nameFormat, FormatArgs);
            }
            set {
                base.Title = value;
            }
        }

        public override string Description {
            get {
                return string.Format(descriptionFormat, FormatArgs);
            }
            set {
                base.Description = value;
            }
        }
    }
}
