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
            long startAtMillis,
            long endAtMillis,
            List<Guid> activities)
        {
            DateTime baseDate = DateTime.Today;

            DateTime startedDateTime = baseDate.AddMilliseconds(startAtMillis);
            DateTime endDateTime = baseDate.AddMilliseconds(endAtMillis);

            if(endAtMillis < startAtMillis)
            {
                endDateTime.AddDays(1);
            }


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

                double workDurationMillis = totalWorkingTime.TotalMilliseconds;
                if(userActivities.Count() > 0)
                {
                    workDurationMillis = totalWorkingTime.TotalMilliseconds / (userActivities.Count() + 1);
                }

                Plan plan = new Plan()
                {
                    PlanID = Guid.NewGuid(),
                    UserID =  authenticatedUser.UserID,
                    EndDateTime = endDateTime,
                    StartDateTime = startedDateTime
                };


                TimeSpan currentTime = TimeSpan.FromMilliseconds(startedDateTime.TimeOfDay.TotalMilliseconds);

                int order = 1;

                for (int i = 0; i < userActivities.Count(); i++)
                {
                    //Add the work activity
                    TimeSpan endTime = currentTime.Add(TimeSpan.FromMilliseconds(workDurationMillis));

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

                    TimeSpan breakEndTime = currentTime.Add(userActivity.PlannedDuration);
                    plan.PlanActivities.Add(new PlanActivity()
                    {
                        PlanActivityID = Guid.NewGuid(),
                        IsActual = false,
                        Order = order++,
                        StartTime = currentTime,
                        UserActivityID = userActivity.UserActivityID,
                        EndTime = breakEndTime,
                        Type = PlanActivityType.Personal
                    });

                    currentTime = breakEndTime;

                }

                //Add the last work activity.s
                plan.PlanActivities.Add(new PlanActivity()
                {
                    PlanActivityID = Guid.NewGuid(),
                    IsActual = false,
                    Order = order,
                    StartTime = currentTime,
                    EndTime = currentTime.Add(TimeSpan.FromMilliseconds(workDurationMillis)),
                    Type = PlanActivityType.WorkRelated
                });

                entities.Plans.Add(plan);
                entities.SaveChanges();

                return plan.ToPlanDto();
            }
        }
    }
}
