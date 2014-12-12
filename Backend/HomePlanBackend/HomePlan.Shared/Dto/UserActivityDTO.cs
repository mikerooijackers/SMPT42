using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using HomePlan.Shared.Enumerations;
 G
namespace HomePlan.Shared.DTO
{
    /// <summary>
    /// Class for transferring the UserActivity object between clients
    /// </summary>
    public class UserActivityDto
    {
        public Guid UserActivityId { get; set; }
        public Guid UserId { get; set; }
        public string Name { get; set; }
        public UserActivityIconType? IconType { get; set; }
        public TimeSpan PlannedDuration { get; set; }
    }
}