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
        public string Name { get; set; }
        [DataMember]
        public string Email { get; set; }
        [DataMember]
        public string Password { get; set; }
        [DataMember]
        public string AvatarUrl { get; set; }
    }
}