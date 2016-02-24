using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MyTest.WindowsService.Events;

namespace MyTest.Library.WindowsService.Events
{
    public class StandardServiceEvents
    {
        public static FormattedLogEventMessage StartingService = new FormattedLogEventMessage(1101, "Starting Service", "Starting '{0}' service...");
        public static FormattedLogEventMessage ServiceStarted = new FormattedLogEventMessage(1102, "Service Started", "Service '{0}' has started.");
        public static FormattedLogEventMessage StoppingService = new FormattedLogEventMessage(1201, "Stopping Service", "Stopping '{0}' service...");
        public static FormattedLogEventMessage ServiceStopped = new FormattedLogEventMessage(1202, "Service Stopped", "Service '{0}' has stopped.");
        public static FormattedLogEventMessage ServiceInterruptedWarning = new FormattedLogEventMessage(1401, "Service Interrupted Warning", "WARNING: Service '{0}' interrupted.", System.Diagnostics.TraceEventType.Warning, 2);
        public static FormattedLogEventMessage OnServiceError = new FormattedLogEventMessage(1501, "{1} Error", "ERROR: {0}.{1} '{2}'", System.Diagnostics.TraceEventType.Error, 3);
    }
}
