using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using HomePlan.Entities;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    public class Service1 : IAuthService
    {

        public UserDto Authenticate(string username, string password)
        {
            throw new NotImplementedException();
        }

        public UserDto Register(string username, string email, string password)
        {
            using(HomePlanEntities entities = new HomePlanEntities())
            {
                var user = new Entities.User()
                {
                    UserID = Guid.NewGuid(),
                    UserName = username,
                    Email = email,
                    Password = password
                };
                entities.Users.Add(user);
                entities.SaveChanges();

                return user.toUserDto();
            }
        }
    }
}
