USE [DemoAPI]
GO

/****** Object:  Table [dbo].[person]    Script Date: 19/10/2023 16:48:48 ******/
/* Create DemoAPI database and run this to create person table**/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[person](
	[id] [uniqueidentifier] DEFAULT (newid()),
	[name] [varchar](100) NOT NULL
) ON [PRIMARY]
GO




