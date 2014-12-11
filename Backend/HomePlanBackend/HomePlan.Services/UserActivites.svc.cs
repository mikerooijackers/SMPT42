using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "UserActivites" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select UserActivites.svc or UserActivites.svc.cs at the Solution Explorer and start debugging.
    public class UserActivites : IUserActivites
    {
        public List<UserActivityDto> GetUserActivities()
        {
            throw new NotImplementedException();
        }

        public UserActivityDto GetUserActivity(string userActivityId)
        {
            throw new NotImplementedException();
        }

        public UserActivityDto AddUserActivity(UserActivityDto userActivity)
        {
            throw new NotImplementedException();
        }

        public UserActivityDto EditUserActivity(UserActivityDto userActivity)
        {
            throw new NotImplementedException();
        }

        public bool RemoveUserActivity(Guid userActivityId)
        {
            throw new NotImplementedException();
        }
    }
}
