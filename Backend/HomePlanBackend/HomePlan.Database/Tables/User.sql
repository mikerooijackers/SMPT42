﻿CREATE TABLE [dbo].[User]
(
	[UserID] UNIQUEIDENTIFIER NOT NULL
		CONSTRAINT [DF_User_UserID] DEFAULT NEWID()
		CONSTRAINT [PK_User] PRIMARY KEY,
	[Email] VARCHAR(255) NOT NULL,
	[UserName] VARCHAR(255) NOT NULL,
	[AvatarImage] VARCHAR(255) NULL,
	[Password] VARCHAR(MAX) NOT NULL,
	[ActiviationString] VARCHAR(MAX) NULL,

	CONSTRAINT [UN_User_Email] UNIQUE(Email)
)