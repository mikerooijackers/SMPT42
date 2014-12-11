using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using HomePlan.Shared.Enumerations;

namespace HomePlan.Shared.DTO
{
    /// <summary>
    /// Class for transferring the PlanActivity object between clients
    /// </summary>
    public class PlanActivityDto
    {
        public Guid PlanActivityId { get; set; }
        public PlanActivityType Type { get; set; }
        public long StartTimeTicks { get; set; }
        public long EndTimeTicks { get; set; }
    }
}