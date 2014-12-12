using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
using System.ServiceModel.Channels;
using System.ServiceModel.Dispatcher;
using System.Web;

namespace HomePlan.Services.ErrorHandler
{
    public class JsonErrorHandler : IErrorHandler
    {
        // ReSharper disable once RedundantAssignment
        public void ProvideFault(Exception error, MessageVersion version, ref Message fault)
        {
            var jsonError = new JsonErrorDetails(error.Message, error.StackTrace, error.GetType().FullName);

            fault = Message.CreateMessage(version, "", jsonError,
                new DataContractJsonSerializer(typeof (JsonErrorDetails)));


            //Use JSON instead of xml
            var wbf = new WebBodyFormatMessageProperty(WebContentFormat.Json);
            fault.Properties.Add(WebBodyFormatMessageProperty.Name, wbf);

            //Modify the response
            var rmp = new HttpResponseMessageProperty()
            {
                StatusCode = HttpStatusCode.BadRequest,
                StatusDescription = "Bad Request"
            };

            rmp.Headers[HttpResponseHeader.ContentType] = "application/json";
            fault.Properties.Add(HttpResponseMessageProperty.Name, rmp);
        }

        public bool HandleError(Exception error)
        {
            //Error has already been handled.
            return true;
        }
    }
}