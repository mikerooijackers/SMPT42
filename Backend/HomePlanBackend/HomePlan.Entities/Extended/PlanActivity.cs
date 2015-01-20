using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HomePlan.Shared.DTO;
using HomePlan.Shared.Extensions;

// ReSharper disable once CheckNamespace
namespace HomePlan.Entities
{
    public partial class PlanActivity
    {
        public PlanActivityDto ToPlanActivityDto()
        {
            UserActivityDto selectedUserActivity = null;
            if(this.UserActivity != null)
            {
                selectedUserActivity = this.UserActivity.ToUserActivityDto(); 
            }
            return new PlanActivityDto()
            {
                EndTimeMillis = (long) this.EndTime.TotalMilliseconds,
                StartTimeMillis = (long) this.StartTime.TotalMilliseconds,
                PlanActivityId =  this.PlanActivityID,
                UserActivity = selectedUserActivity,
                Type = this.Type
            };
        }
    }
}
