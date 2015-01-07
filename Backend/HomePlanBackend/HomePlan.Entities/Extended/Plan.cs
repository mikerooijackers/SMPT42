using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HomePlan.Shared.DTO;

// ReSharper disable once CheckNamespace
namespace HomePlan.Entities
{
    public partial class Plan
    {
        public PlanDto ToPlanDto()
        {
            return new PlanDto()
            {
                PlanId = this.PlanID,
                PlannedActivities =
                    this.PlanActivities.Where(p => !p.IsActual).Select(p => p.ToPlanActivityDto()).ToList(),
                ActualActitivies = this.PlanActivities.Where(p => p.IsActual).Select(p => p.ToPlanActivityDto()).ToList(),
                EndDateTime = this.EndDateTime,
                StartDateTime = this.StartDateTime
            };
        }
    }
}
