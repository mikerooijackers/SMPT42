using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace HomePlan.Services.ErrorHandler
{
    [DataContract]
    public class JsonErrorDetails
    {
        [DataMember]
        public string StackTrace { get; set; }
        [DataMember]
        public string Message { get; set; }
        [DataMember]
        public string ExceptionType { get; set; }

        public JsonErrorDetails(string message, string stacktrace, string exceptiontype)
        {
            this.StackTrace = stacktrace;
            this.Message = message;
            this.ExceptionType = exceptiontype;
        }

    }
}