using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IUserActivites" in both code and config file together.
    [ServiceContract]
    public interface IUserActivites
    {
        [OperationContract]
        [WebGet(UriTemplate = "")]
        List<UserActivityDto> GetUserActivities();

        [OperationContract]
        [WebGet(UriTemplate = "/{userActivityId}")]
        UserActivityDto GetUserActivity(string userActivityId);

        [OperationContract]
        UserActivityDto AddUserActivity(UserActivityDto userActivity);

        [OperationContract]
        UserActivityDto EditUserActivity(UserActivityDto userActivity);

        [OperationContract]
        bool RemoveUserActivity(Guid userActivityId);


    }
}
