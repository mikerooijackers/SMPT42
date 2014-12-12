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
        public UserDto Authenticate(UserDto user)
        {
            var authenticatedUser = Helpers.AuthenticationHelper.Authenticate(user);
            return authenticatedUser != null ? authenticatedUser.ToUserDto() : null;
        }

        public UserDto Register(UserDto user)
        {
            using (HomePlanEntities entities = new HomePlanEntities())
            {
                //TODO: Encrypt / hash password.

                Entities.User newUser = new Entities.User()
                {
                    Email = user.Email,
                    UserName = user.Name,
                    Password = user.Password,
                    UserID = Guid.NewGuid()
                };

                entities.Users.Add(newUser);

                entities.SaveChanges();

                return newUser.ToUserDto();
            }
        }
    }
}
