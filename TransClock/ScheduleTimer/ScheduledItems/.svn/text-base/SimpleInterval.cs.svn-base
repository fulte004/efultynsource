/***************************************************************************
 * Copyright Andy Brummer 2004-2005
 * 
 * This code is provided "as is", with absolutely no warranty expressed
 * or implied. Any use is at your own risk.
 *
 * This code may be used in compiled form in any way you desire. This
 * file may be redistributed unmodified by any means provided it is
 * not sold for profit without the authors written consent, and
 * providing that this notice and the authors name is included. If
 * the source code in  this file is used in any commercial application
 * then a simple email would be nice.
 * 
 **************************************************************************/
using System;
using System.Collections;
using System.Diagnostics;
using System.Xml.Serialization;

namespace Schedule
{
    /// <summary>
    /// The simple interval represents the simple scheduling that .net supports natively.  It consists of a start
    /// absolute time and an interval that is counted off from the start time.
    /// </summary>
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class SimpleInterval : IScheduledItem
    {
        public SimpleInterval() {
        }

        public SimpleInterval(DateTime startTime, TimeSpan Interval) {
            TimeInterval = Interval;
            StartTime = startTime;
            EndTime = DateTime.MaxValue;
        }
        public SimpleInterval(DateTime startTime, TimeSpan Interval, int count) {
            TimeInterval = Interval;
            StartTime = startTime;
            EndTime = StartTime + TimeSpan.FromTicks(Interval.Ticks * count);
        }
        public SimpleInterval(DateTime startTime, TimeSpan Interval, DateTime endTime) {
            TimeInterval = Interval;
            StartTime = startTime;
            EndTime = endTime;
        }
        public void AddEventsInInterval(DateTime Begin, DateTime End, ArrayList List) {
            if (End <= StartTime)
                return;
            DateTime Next = NextRunTime(Begin, true);
            while (Next < End) {
                List.Add(Next);
                Next = NextRunTime(Next, false);
            }
        }

        public DateTime NextRunTime(DateTime time, bool AllowExact) {
            DateTime returnTime = NextRunTimeInt(time, AllowExact);
            Debug.WriteLine(time);
            Debug.WriteLine(returnTime);
            Debug.WriteLine(EndTime);
            return (returnTime >= EndTime) ? DateTime.MaxValue : returnTime;
        }

        private DateTime NextRunTimeInt(DateTime time, bool AllowExact) {
            TimeSpan Span = time - StartTime;
            if (Span < TimeSpan.Zero)
                return StartTime;
            if (ExactMatch(time))
                return AllowExact ? time : time + TimeInterval;
            uint msRemaining = (uint)(TimeInterval.TotalMilliseconds - ((uint)Span.TotalMilliseconds % (uint)TimeInterval.TotalMilliseconds));
            return time.AddMilliseconds(msRemaining);
        }

        private bool ExactMatch(DateTime time) {
            TimeSpan Span = time - StartTime;
            if (Span < TimeSpan.Zero)
                return false;
            return (Span.TotalMilliseconds % TimeInterval.TotalMilliseconds) == 0;
        }

        [XmlIgnore]
        public TimeSpan TimeInterval { get; set; }
        
        /// <summary>
        /// Serializable TimeInterval property.
        /// </summary>
        [XmlAttribute("TimeInterval")]
        public string SerializableTimeInterval {
            get { return TimeInterval.ToString(); }
            set { TimeInterval = TimeSpan.Parse(value); }
        }
        
        [XmlAttribute]
        public DateTime StartTime { get; set; }
        [XmlAttribute]
        public DateTime EndTime { get; set; }
    }
}