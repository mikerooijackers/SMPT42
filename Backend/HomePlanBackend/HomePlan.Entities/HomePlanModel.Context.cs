﻿

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
using System.Data.Entity;
using System.Data.Entity.Infrastructure;


public partial class HomePlanEntities : DbContext
{
    public HomePlanEntities()
        : base("name=HomePlanEntities")
    {

    }

    protected override void OnModelCreating(DbModelBuilder modelBuilder)
    {
        throw new UnintentionalCodeFirstException();
    }


    public virtual DbSet<Plan> Plans { get; set; }

    public virtual DbSet<PlanActivity> PlanActivities { get; set; }

    public virtual DbSet<User> Users { get; set; }

    public virtual DbSet<UserActivity> UserActivities { get; set; }

}

}
