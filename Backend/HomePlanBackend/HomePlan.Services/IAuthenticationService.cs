using System.ServiceModel;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    /// <summary>
    /// Interface for the authenticataion webservice.
    /// </summary>
    [ServiceContract]
    public interface IAuthenticationService
    {

        [OperationContract]
        UserDto Authenticate(string username, string password);

        [OperationContract]
        UserDto Test();

    }

}
