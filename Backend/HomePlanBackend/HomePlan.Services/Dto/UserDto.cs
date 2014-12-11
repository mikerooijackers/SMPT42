using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace HomePlan.Services.DTO
{
    /// <summary>
    /// Class for transferring the User object between clients.
    /// </summary>
    public class UserDto
    {
        public Guid UserId { get; set; }
        public String Name { get; set; }
    }
}