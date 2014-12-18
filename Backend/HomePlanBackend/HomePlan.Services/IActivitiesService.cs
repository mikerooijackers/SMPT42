using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IActivitiesService" in both code and config file together.
    [ServiceContract]
    public interface IActivitiesService
    {
        /// <summary>
        /// Get all the user activities of the specified user
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        [OperationContract]
        List<UserActivityDto> GetUserActivities(UserDto user);

        /// <summary>
        /// Get a single user activity
        /// </summary>
        /// <param name="user"></param>
        /// <param name="userActivityId"></param>
        /// <returns></returns>
        [OperationContract]
        UserActivityDto GetUserActivity(UserDto user, Guid userActivityId);

        /// <summary>
        /// Add a new user activity.
        /// </summary>
        /// <param name="user"></param>
        /// <param name="userActivity"></param>
        /// <returns></returns>
        [OperationContract]
        UserActivityDto Mutate(UserDto user, UserActivityDto userActivity);


        /// <summary>
        /// Remove an existing UserActivity
        /// </summary>
        /// <param name="user"></param>
        /// <param name="userActivityId"></param>
        /// <returns></returns>
        [OperationContract]
        bool RemoveUserActivity(UserDto user, Guid userActivityId);
    }
}
