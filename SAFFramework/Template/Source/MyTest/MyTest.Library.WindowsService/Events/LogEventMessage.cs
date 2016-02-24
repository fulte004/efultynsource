using FIS.Notifications.Logging;
using GalaSoft.MvvmLight.Messaging;

namespace MyTest.WindowsService.Events
{
    public class LogEventMessage : MessageBase, ILogEventMessage
    {
        public LogEventMessage() {

        }
        public LogEventMessage(int eventID, string name, string description, int priority, System.Diagnostics.TraceEventType severity) {
            EventID = eventID;
            Title = name;
            Description = description;
            Priority = priority;
            Severity = severity;
        }
        public LogEventMessage(int eventID, string name, string description) {
            EventID = eventID;
            Title = name;
            Description = description;
            Priority = 1;
            Severity = System.Diagnostics.TraceEventType.Information;
        }
        public string Source { get; set; }
        public int EventID { get; set; }
        public virtual string Title { get; set; }
        public virtual string Description { get; set; }
        public int Priority { get; set; }
        public System.Diagnostics.TraceEventType Severity { get; set; }
    }
}
