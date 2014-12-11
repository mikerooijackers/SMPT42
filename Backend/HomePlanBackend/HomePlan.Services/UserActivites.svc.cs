using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Security.Authentication;
using System.ServiceModel;
using System.Text;
using HomePlan.Entities;
using HomePlan.Services.Helpers;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "UserActivites" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select UserActivites.svc or UserActivites.svc.cs at the Solution Explorer and start debugging.
    public class UserActivites : IUserActivites
    {
        public List<UserActivityDto> GetUserActivities(UserDto user)
        {
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUser = AuthenticationHelper.Authenticate(user, entities);

                return
                    entities.UserActivities.Where(ua => ua.UserID == authenticatedUser.UserID && ua.DeletedValue == null)
                        .Select(ua => ua.ToUserActivityDto()).ToList();
            }
        }

        public UserActivityDto GetUserActivity(UserDto user, Guid userActivityId)
        {
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUser = AuthenticationHelper.Authenticate(user, entities);

                var userActivity =
                    (from ua in entities.UserActivities
                     where ua.UserActivityID == userActivityId && ua.DeletedValue == null
                     select ua).FirstOrDefault();

                if (userActivity == null)
                {
                    throw new ArgumentException(String.Format("UserActivity with id {0} does not exist", userActivityId));
                }

                if (userActivity.UserID != authenticatedUser.UserID)
                {
                    throw new AuthenticationException("You are not allowed to view the user Activity of this user.");
                }

                return userActivity.ToUserActivityDto();
            }
        }

        public UserActivityDto AddUserActivity(UserDto user, UserActivityDto userActivity)
        {
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUSer = AuthenticationHelper.Authenticate(user, entities);

                UserActivity newActivity = new UserActivity()
                {
                    IconType = userActivity.IconType,
                    Name = userActivity.Name,
                    PlannedDuration = userActivity.PlannedDuration,
                    UserID = authenticatedUSer.UserID,
                    UserActivityID = Guid.NewGuid()
                };

                entities.UserActivities.Add(newActivity);

                entities.SaveChanges();

                return newActivity.ToUserActivityDto();
            }
        }

        public UserActivityDto EditUserActivity(UserDto user, UserActivityDto userActivity)
        {
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUser = AuthenticationHelper.Authenticate(user, entities);

                var activityToEdit =
                    entities.UserActivities.FirstOrDefault(
                        ua => ua.UserActivityID == userActivity.UserActivityId && ua.DeletedValue == null);

                if (activityToEdit == null)
                {
                    throw new ArgumentException(String.Format("User activity with id {0} does not exist.",
                        userActivity.UserActivityId));
                }
                else if (activityToEdit.UserID != authenticatedUser.UserID)
                {
                    throw new AuthenticationException("You are not allowed to edit the activity of this user.");
                }

                activityToEdit.Name = userActivity.Name;
                activityToEdit.IconType = userActivity.IconType;
                activityToEdit.PlannedDuration = userActivity.PlannedDuration;

                entities.SaveChanges();

                return activityToEdit.ToUserActivityDto();
            }
        }

        public bool RemoveUserActivity(UserDto user, Guid userActivityId)
        {
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUser = AuthenticationHelper.Authenticate(user, entities);

                var activityToRemove =
                    entities.UserActivities.FirstOrDefault(
                        ua => ua.UserActivityID == userActivityId && !ua.DeletedValue.HasValue);

                if (activityToRemove == null)
                {
                    throw new ArgumentException(String.Format("Activity with id {0} does not exist", userActivityId));
                }

                activityToRemove.DeletedValue = Guid.NewGuid();
                entities.SaveChanges();


                return true;
            }
        }
    }
}
