﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using HomePlan.Shared.DTO;

namespace HomePlan.Services
{
    [ServiceContract]
    public interface IPlanService
    {
        [OperationContract]
        PlanDto GeneratePlan(UserDto loggedInUser, long startAtMillis, long endAtMillis, List<Guid> activities);
    }
}
