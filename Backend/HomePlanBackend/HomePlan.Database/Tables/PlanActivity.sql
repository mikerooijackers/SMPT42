CREATE TABLE [dbo].[PlanActivity]
(
	[PlanActivityID] UNIQUEIDENTIFIER NOT NULL
		CONSTRAINT [DF_PlanActivity] DEFAULT NEWID()
		CONSTRAINT [PK_PlanActivity] PRIMARY KEY,
	[PlanID] UNIQUEIDENTIFIER NOT NULL,
	[StartTime] TIME NOT NULL,
	[EndTime] TIME NOT NULL,
	[Order] INT NOT NULL,
	[Type] TINYINT NOT NULL, --The type of the planned activity, possibly an enum
	[UserActivityID] UNIQUEIDENTIFIER NULL, --ID of the user defined activity (not always required)
	[PlannedActivityID] UNIQUEIDENTIFIER NULL,
	[IsActual] BIT NOT NULL, --bit indicating whether it was a planned activity or an actual (time spent) activity, maybe not neaded because we have plannedActivityID

	CONSTRAINT [FK_PlanActivity_Plan] FOREIGN KEY(PlanID) REFERENCES [Plan](PlanID),
	CONSTRAINT [FK_PlannedActivity_PlanActivity] FOREIGN KEY(PlannedActivityID) REFERENCES [PlanActivity](PlanActivityID),
	CONSTRAINT [FK_PlanActivity_UserActivity] FOREIGN KEY(UserActivityID) REFERENCES UserActivity(UserActivityID),
	CONSTRAINT [UN_PlanActivity_PlanID_Order] UNIQUE(PlanID, [Order])

)
