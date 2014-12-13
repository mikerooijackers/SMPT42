using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.ServiceModel.Web;
using System.Text;
using HomePlan.Entities;
using HomePlan.Services.Helpers;
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

        public UserDto Register(UserDto user, string profileImage)
        {
            using (HomePlanEntities entities = new HomePlanEntities())
            {
                //TODO: Encrypt / hash password.
                if (entities.Users.Any(u => u.Email == user.Email))
                {
                    throw new Exception("A user with this email already exists.");
                }

                Entities.User newUser = new Entities.User()
                {
                    Email = user.Email,
                    UserName = user.Name,
                    Password = user.Password,
                    UserID = Guid.NewGuid()
                };

                if (!String.IsNullOrWhiteSpace(profileImage))
                {
                    string imageLocation = ImageHelper.SaveImage(newUser, profileImage);
                    newUser.AvatarImage = imageLocation;
                }

                entities.Users.Add(newUser);

                try
                {
                    entities.SaveChanges();
                }
                catch (Exception ex)
                {
                    throw new Exception("An unkown error occured.");
                }

                return newUser.ToUserDto();
            }
        }

        public UserDto EditUserProperties(UserDto user, string newProfileImage)
        {
            using (var entities = new HomePlanEntities())
            {
                User userToEdit = entities.Users.Find(user.UserId);

                if (userToEdit == null)
                {
                    throw new ArgumentException("User with ID specified does not exist.");
                }

                userToEdit.UserName = user.Name;

                if (!String.IsNullOrWhiteSpace(newProfileImage))
                {
                    string newAvatarUrl = ImageHelper.SaveImage(userToEdit, newProfileImage);
                    userToEdit.AvatarImage = newAvatarUrl;
                }

                entities.SaveChanges();

                return userToEdit.ToUserDto();
            }
        }
    }
}
