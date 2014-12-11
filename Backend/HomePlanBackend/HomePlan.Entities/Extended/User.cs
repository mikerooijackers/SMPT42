using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HomePlan.Shared.DTO;

// ReSharper disable CheckNamespace

namespace HomePlan.Entities
{
    public partial class User
    {
        public UserDto ToUserDto()
        {
            return new UserDto()
            {
                UserId = this.UserID,
                Name = this.UserName,
                Email = this.Email,
                AvatarUrl = this.AvatarImage,
                Password = this.Password
            };
        }
    }
}
