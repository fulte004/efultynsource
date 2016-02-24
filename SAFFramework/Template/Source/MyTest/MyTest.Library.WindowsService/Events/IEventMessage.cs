namespace FIS.Notifications.Logging
{
    public interface ILogEventMessage
    {
        int EventID { get; set; }
        string Title { get; set; }
        string Description { get; set; }
        int Priority { get; set; }
        System.Diagnostics.TraceEventType Severity { get; set; }
    }
}
