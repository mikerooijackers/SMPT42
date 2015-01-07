using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace HomePlan.Shared.DTO
{
    /// <summary>
    /// Class for transferring the Plan object between clients.
    /// </summary>
    public class PlanDto
    {
        public Guid PlanId { get; set; }
        public DateTime StartDateTime { get; set; }
        public DateTime EndDateTime { get; set; }
        public List<PlanActivityDto> PlannedActivities { get; set; }
        public List<PlanActivityDto> ActualActitivies { get; set; }
    }
}