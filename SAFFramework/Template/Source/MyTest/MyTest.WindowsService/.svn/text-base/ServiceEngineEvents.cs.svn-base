using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MyTest.WindowsService.Events
{
    public class ServiceEngineEvents
    {
        public static LogEventMessage StartingServices = new LogEventMessage(101, "Starting Services", "Starting discoverable services...");
        public static LogEventMessage ServicesStarted = new LogEventMessage(102, "Services Started", "...All discoverable services' threads have been started.");
        public static LogEventMessage StoppingServices = new LogEventMessage(201, "Stopping Services", "Stopping all services...");
        public static LogEventMessage ServicesStopped = new LogEventMessage(202, "Services Stopped", "All services have stopped.");
        public static FormattedLogEventMessage OnServiceEngineError = new FormattedLogEventMessage(501, "{1} Error", "ERROR: {0}.{1} '{2}'", System.Diagnostics.TraceEventType.Error, 3);
    }
}
