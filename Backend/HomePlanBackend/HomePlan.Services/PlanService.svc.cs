using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using HomePlan.Entities;
using HomePlan.Services.Helpers;
using HomePlan.Shared.DTO;
using HomePlan.Shared.Enumerations;

namespace HomePlan.Services
{
    public class PlanService : IPlanService
    {
        public PlanDto GeneratePlan(UserDto loggedInUser,
            long startAtTicks,
            long endAtTicks,
            List<Guid> activities)
        {
            DateTime startedDateTime = new DateTime(startAtTicks);
            DateTime endDateTime = new DateTime(endAtTicks);

            using (HomePlanEntities entities = new HomePlanEntities())
            {
                User authenticatedUser = AuthenticationHelper.Authenticate(loggedInUser, entities);

                IEnumerable<Entities.UserActivity> userActivities =
                    (from ua in entities.UserActivities
                        where activities.Contains(ua.UserActivityID) && ua.UserID == authenticatedUser.UserID
                        select ua).ToArray();

                TimeSpan totalBreakTime = new TimeSpan();


                //Get the total amount of break time;
                totalBreakTime = userActivities.Aggregate(totalBreakTime,
                    (current, activity) => current.Add(activity.PlannedDuration));

                TimeSpan totalTimeSpan = endDateTime.Subtract(startedDateTime);
                TimeSpan totalWorkingTime = totalTimeSpan.Subtract(totalBreakTime);

                long workDurationTicks = totalWorkingTime.Ticks/userActivities.Count();

                Plan plan = new Plan()
                {
                    PlanID = Guid.NewGuid(),
                    UserID =  authenticatedUser.UserID,
                    EndDateTime = endDateTime,
                    StartDateTime = startedDateTime
                };


                TimeSpan currentTime = new TimeSpan(startedDateTime.Ticks);

                int order = 1;

                for (int i = 0; i < userActivities.Count(); i++)
                {
                    //Add the work activity
                    TimeSpan endTime = currentTime.Add(new TimeSpan(workDurationTicks));
                    plan.PlanActivities.Add(new PlanActivity()
                    {
                        PlanActivityID = Guid.NewGuid(),
                        IsActual = false,
                        Order = order++, //Start the order at 1
                        StartTime = currentTime,
                        EndTime = endTime,
                        Type = Shared.Enumerations.PlanActivityType.WorkRelated
                    });

                    currentTime = endTime;
                    //Add the personal activity.
                    UserActivity userActivity = userActivities.ElementAt(i);
                    plan.PlanActivities.Add(new PlanActivity()
                    {
                        PlanActivityID = new Guid(),
                        IsActual = false,
                        Order = order++,
                        StartTime = currentTime,
                        UserActivityID = userActivity.UserActivityID,
                        EndTime = currentTime.Add(userActivity.PlannedDuration),
                        Type = PlanActivityType.Personal
                    });
                }

                //Add the last work activity.
                plan.PlanActivities.Add(new PlanActivity()
                {
                    PlanActivityID = Guid.NewGuid(),
                    IsActual = false,
                    Order = order,
                    StartTime = currentTime,
                    EndTime = currentTime.Add(new TimeSpan(workDurationTicks)),
                    Type = PlanActivityType.WorkRelated
                });

                entities.Plans.Add(plan);
                entities.SaveChanges();

                return plan.ToPlanDto();
            }
        }
    }
}
