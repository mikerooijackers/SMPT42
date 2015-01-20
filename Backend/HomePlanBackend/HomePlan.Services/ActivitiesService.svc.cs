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
using HomePlan.Entities;

namespace HomePlan.Services
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ActivitiesService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ActivitiesService.svc or ActivitiesService.svc.cs at the Solution Explorer and start debugging.
    public class ActivitiesService : IActivitiesService
    {
        public List<UserActivityDto> GetUserActivities(UserDto user)
        {
            List<UserActivityDto> returnedAcitivies = new List<UserActivityDto>();
            using (var entities = new HomePlanEntities())
            {
                var authenticatedUser = AuthenticationHelper.Authenticate(user, entities);

                List<UserActivity> entityActivities = 
                    entities.UserActivities.Where(ua => ua.UserID == authenticatedUser.UserID
                        && ua.DeletedValue == null).ToList();

                foreach(UserActivity ea in entityActivities)
                {
                    returnedAcitivies.Add(ea.ToUserActivityDto());
                }

                return returnedAcitivies;
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

        public UserActivityDto CreateActivity(UserActivityDto activity)
        {
            using (var entities = new HomePlanEntities())
            {
                if (entities.UserActivities.Any(ua => ua.Name == activity.Name))
                {
                    throw new Exception("A task with this name already exists.");
                }

                Entities.UserActivity newActivity = new Entities.UserActivity()
                {
                    IconType = activity.IconType,
                    PlannedDuration = TimeSpan.FromMilliseconds(activity.PlannedDurationMilliseconds),
                    Name = activity.Name,
                    UserID = activity.UserId,
                    UserActivityID = Guid.NewGuid()
                };

                entities.UserActivities.Add(newActivity);

                try
                {
                    entities.SaveChanges();
                }
                catch (Exception ex)
                {
                    throw new Exception("An unknown error occurred.");
                }

                return newActivity.ToUserActivityDto();
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

        public bool SavePhoto(User user, string photo)
        {
            bool success = false;

            using (var entities = new HomePlanEntities())
            {

                Entities.Photo newPhoto = new Entities.Photo()
                {
                  UserID = Guid.NewGuid();
                };

                if (!String.IsNullOrWhiteSpace(photo))
                {
                    string imageLocation = ImageHelper.SavePhoto(user, photo);
                    newPhoto.Photo = imageLocation;
                }

                entities.Photo.Add(newPhoto);

                try
                {
                    entities.SaveChanges();
                    success = true;
                }
                catch (Exception ex)
                {
                    throw new Exception("An unknown error occurred.");
                    return false;
                }

                return success;
            }
        }

    }
}
