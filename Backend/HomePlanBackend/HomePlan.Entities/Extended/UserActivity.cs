using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HomePlan.Shared.DTO;

// ReSharper disable once CheckNamespace
namespace HomePlan.Entities
{
    public partial class UserActivity
    {
        public Shared.DTO.UserActivityDto ToUserActivityDto()
        {
            return new UserActivityDto()
            {
                IconType = this.IconType,
                PlannedDurationMilliseconds = this.PlannedDuration.TotalMilliseconds,
                Name = this.Name,
                UserActivityId = this.UserActivityID,
                UserId = this.UserID
            };
        }
    }
}
