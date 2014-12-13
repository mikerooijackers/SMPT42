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

        /// <summary>
        /// Register a new user.   
        /// </summary>
        /// <param name="user">The user to register</param>
        /// <param name="profileImage">The profile picture BASE 65 encoded.</param>
        /// <returns></returns>
        [OperationContract]
        UserDto Register(UserDto user, string profileImage);

        [OperationContract]
        UserDto EditUserProperties(UserDto user, string newProfileImage);

    }

}
