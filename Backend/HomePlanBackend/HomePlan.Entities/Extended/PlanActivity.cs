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
            return new PlanActivityDto()
            {
                EndTimeMillis = this.EndTime.TotalMilliseconds,
                StartTimeMillis = this.StartTime.TotalMilliseconds,
                PlanActivityId = this.PlanActivityID,
                Type = this.Type
            };
        }
    }
}
