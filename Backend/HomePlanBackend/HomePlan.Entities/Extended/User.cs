using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HomePlan.Shared.DTO;

namespace HomePlan.Entities
{
    public partial class User
    {
        /// <summary>
        /// Create a new DTO object of the entity 
        /// </summary>
        /// <returns>The User DTO object.</returns>
        public UserDto ToUserDto()
        {
            return new UserDto()
            {
                UserId = this.UserID,
                Email = this.Email,
                AvatarUrl = this.AvatarImage,
                Name = this.UserName
            };
        }
    }
}
