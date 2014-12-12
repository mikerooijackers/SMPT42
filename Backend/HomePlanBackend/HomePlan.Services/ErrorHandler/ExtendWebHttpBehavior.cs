using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;
using System.Web;

namespace HomePlan.Services.ErrorHandler
{
    /// <summary>
    /// Extended WebHttp behavior for our own error handler.
    /// </summary>
    public class ExtendWebHttpBehavior : WebHttpBehavior
    {
        protected override void AddServerErrorHandlers(ServiceEndpoint endpoint, EndpointDispatcher endpointDispatcher)
        {
            //Clear the default error handler
            endpointDispatcher.ChannelDispatcher.ErrorHandlers.Clear();

            //Add our own handler
            endpointDispatcher.ChannelDispatcher.ErrorHandlers.Add(new JsonErrorHandler());
        }
    }
}