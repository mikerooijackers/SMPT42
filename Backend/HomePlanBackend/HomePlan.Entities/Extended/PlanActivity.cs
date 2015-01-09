using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HomePlan.Shared.DTO;

// ReSharper disable once CheckNamespace
namespace HomePlan.Entities
{
    public partial class PlanActivity
    {
        public PlanActivityDto ToPlanActivityDto()
        {
            return new PlanActivityDto()
            {
                EndTimeTicks = this.EndTime.Ticks,
                StartTimeTicks = this.StartTime.Ticks,
                PlanActivityId = this.PlanActivityID,
                Type = this.Type
            };
        }
    }
}
