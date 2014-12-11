CREATE TABLE [dbo].[UserActivity]
(
	[UserActivityID] UNIQUEIDENTIFIER NOT NULL
		CONSTRAINT [DF_UserActivity_UserActivityID] DEFAULT NEWID()
		CONSTRAINT [PK_UserActivity] PRIMARY KEY,
	[UserID] UNIQUEIDENTIFIER NOT NULL,
	[Type] TINYINT NOT NULL, --Probably an enum
	[Icon] varchar(255) NULL, --Optional icon, might also be something predefined
	[Name] varchar(255) NOT NULL,
	[DeletedValue] uniqueidentifier null,
	[PlannedDuration] TIME NOT NULL,

	CONSTRAINT [FK_UserActivity_Person] FOREIGN KEY(UserID) REFERENCES [User](UserID)

)
