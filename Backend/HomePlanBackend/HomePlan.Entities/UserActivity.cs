
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------


namespace HomePlan.Entities
{

using System;
    using System.Collections.Generic;
    
public partial class UserActivity
{

    public UserActivity()
    {

        this.PlanActivities = new HashSet<PlanActivity>();

    }


    public System.Guid UserActivityID { get; set; }

    public System.Guid UserID { get; set; }

    public Nullable<HomePlan.Shared.Enumerations.UserActivityIconType> IconType { get; set; }

    public string Name { get; set; }

    public Nullable<System.Guid> DeletedValue { get; set; }

    public System.TimeSpan PlannedDuration { get; set; }



    public virtual ICollection<PlanActivity> PlanActivities { get; set; }

    public virtual User User { get; set; }

}

}
