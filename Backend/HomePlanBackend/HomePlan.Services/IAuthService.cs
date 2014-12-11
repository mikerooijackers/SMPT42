using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    /// <summary>
    /// Interface for the authenticataion webservice.
    /// </summary>
    [ServiceContract]
    public interface IAuthService
    {

        [OperationContract]
        UserDto Authenticate(string username, string password);

        [OperationContract]
        UserDto Register(string username, string email, string password);

    }

}
