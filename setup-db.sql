-- SQL Server setup script for TechZone project
-- Run in SSMS or sqlcmd with an admin account

-- 1) Create database
IF DB_ID('TechZoneDB') IS NULL
BEGIN
    CREATE DATABASE [TechZoneDB];
END
GO

USE [TechZoneDB];
GO

-- 2) Create user + login (optional) if you want a dedicated user
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'techzone_user')
BEGIN
    CREATE LOGIN [techzone_user] WITH PASSWORD = 'TechZone@1234';
END
GO

USE [TechZoneDB];
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'techzone_user')
BEGIN
    CREATE USER [techzone_user] FOR LOGIN [techzone_user];
    ALTER ROLE db_owner ADD MEMBER [techzone_user];
END
GO

-- 3) Verify
SELECT name, state_desc FROM sys.databases WHERE name = 'TechZoneDB';
SELECT name, type_desc FROM sys.database_principals WHERE name IN ('techzone_user');
