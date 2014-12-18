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
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ActivitiesService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ActivitiesService.svc or ActivitiesService.svc.cs at the Solution Explorer and start debugging.
    public class ActivitiesService : IActivitiesService
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

        public UserActivityDto Mutate(UserDto user, UserActivityDto userActivity)
        {
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUser = AuthenticationHelper.Authenticate(user, entities);

                UserActivity userActivityToUpdate = null;
                if (userActivity.UserActivityId != Guid.Empty)
                {
                    userActivityToUpdate = entities.UserActivities.Find((userActivity.UserActivityId));

                    if (userActivityToUpdate.UserID != authenticatedUser.UserID)
                    {
                        throw new UnauthorizedAccessException("Not your activity, BITCH");
                    }
                }
                else
                {
                    userActivityToUpdate = entities.UserActivities.Add(new UserActivity()
                    {
                        UserActivityID = Guid.NewGuid(),
                        UserID = authenticatedUser.UserID
                    });
                }

                userActivityToUpdate.IconType = userActivity.IconType;
                userActivityToUpdate.Name = userActivity.Name;
                userActivityToUpdate.PlannedDuration = TimeSpan.FromMilliseconds(userActivity.PlannedDurationMilliseconds);


                entities.SaveChanges();

                return userActivityToUpdate.ToUserActivityDto();
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
