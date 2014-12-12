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
        UserDto Authenticate(UserDto user);

        [OperationContract]
        UserDto Register(UserDto user);

    }

}
