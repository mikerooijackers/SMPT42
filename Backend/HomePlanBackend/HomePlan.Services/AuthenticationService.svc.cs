using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.ServiceModel.Web;
using System.Text;
using HomePlan.Entities;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    public class AuthenticationService : IAuthenticationService
    {
        public UserDto Authenticate(string username, string password)
        {
            return new UserDto()
            {
                UserId = Guid.NewGuid(),
                Name = username,
                AvatarUrl = password
            };
        }
        public UserDto Test()
        {
            return new UserDto()
            {
                UserId = Guid.NewGuid()
            };
        }
    }
}
