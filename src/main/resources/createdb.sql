USE [master]
GO

if not exists ( select * from sys.sql_logins where [name] = 'test_user' )
	CREATE LOGIN test_user WITH PASSWORD=N'test_password', DEFAULT_DATABASE=[master]
GO

IF not exists ( SELECT * FROM master.sys.databases where name = 'test_db' )
	CREATE DATABASE test_db
go

USE [test_db]
GO

if not exists ( SELECT * FROM sys.database_principals where [name] = 'test_user' )
  CREATE USER test_user FOR LOGIN [test_user] WITH DEFAULT_SCHEMA=[dbo]
GO

if not exists ( select * from INFORMATION_SCHEMA.TABLES where TABLE_TYPE = 'BASE TABLE' and TABLE_NAME = 'testentity' )
CREATE TABLE testentity (
	id [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	CONSTRAINT pk_testentitye PRIMARY KEY CLUSTERED ( id ASC )
)
GO

set identity_insert testentity on
go

IF NOT EXISTS (SELECT * FROM testentity WHERE id = 1 )
  insert into testentity ( id, [name] ) values ( 1, 'test entity' )
go

set identity_insert testentity off
go