using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace HomePlan.Shared.DTO
{
    /// <summary>
    /// Class for transferring the User object between clients.
    /// </summary>
    [DataContract]
    public class UserDto
    {
        [DataMember]
        public Guid UserId { get; set; }
        [DataMember]
        public String Name { get; set; }
        [DataMember]
        public String Email { get; set; }
        [DataMember]
        public String AvatarUrl { get; set; }
    }
}