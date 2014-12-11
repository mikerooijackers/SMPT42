using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using HomePlan.Entities;
using HomePlan.Shared.DTO;

namespace HomePlan.Services.Helpers
{
    public static class AuthenticationHelper
    {
        public static Entities.User Authenticate(string username, string password, HomePlanEntities entities)
        {
            //TODO: salt password
                User authenticatedUser =
                    (from u in entities.Users
                        where u.UserName == username && u.Password == password
                        select u).FirstOrDefault();

                if (authenticatedUser == null)
                {
                    throw new UnauthorizedAccessException("Username and/or password is incorrect.");
                }

                return authenticatedUser;
        }

        public static Entities.User Authenticate(string username, string password)
        {
            using (var entities = new HomePlanEntities())
            {
                return Authenticate(username, password, entities);
            }
        }

        public static Entities.User Authenticate(UserDto user)
        {
            return Authenticate(user.Name, user.Password);
        }

        public static Entities.User Authenticate(UserDto user, HomePlanEntities entities)
        {
            return Authenticate(user.Name, user.Password, entities);
        }
    }
}