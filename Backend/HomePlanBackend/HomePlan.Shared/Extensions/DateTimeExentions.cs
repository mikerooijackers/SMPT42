using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HomePlan.Shared.Extensions
{
    public static class DateTimeExentions
    {
        public static double ToMilliseconds(this DateTime dateTime)
        {
            return dateTime.ToUniversalTime().Subtract(
                    new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc)
                    ).TotalMilliseconds;
        }
    }
}
