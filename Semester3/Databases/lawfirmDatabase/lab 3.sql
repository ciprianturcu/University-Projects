---a. modify the type of a column;
-- modify Atourney age from int to tinyint
create or alter procedure setAtourneyAgeToTinyint as 
	alter table Atourney 
		alter column age tinyint not null
go

create or alter procedure setAtourneyAgeToInt as 
	alter table Atourney 
		alter column age int not null
go

---b. add / remove a column;
create or alter procedure addCountryToAtourney as
	alter table Atourney
		add country varchar(50)
go

create or alter procedure removeCountryFromAtourney as
	alter table Atourney
		drop column country
go
---c. add / remove a DEFAULT constraint;
create or alter procedure addDefaultToLawsuitOfClientProgress as
	alter table Lawsuit_of_client
		add constraint df_progress
			default 'ongoing' for progress
go

create or alter procedure removeDefaultFromLawsuitOfClientProgress as
	alter table Lawsuit_of_client
		drop constraint df_progress
go

---g. create / drop a table.
create or alter procedure addJudge as
	create table Judge (
		judge_id int not null,
		full_name varchar(100) not null,
		salary int,
		domain varchar(30) not null,
		constraint JUDGE_ID primary key(judge_id),
		lawsuit_id int
	)

go 

create or alter procedure dropJudge as
	drop table if exists Judge
go

---d. add / remove a primary key;
create or alter procedure addNameDomainPrimaryKey as
	alter table Judge
		drop constraint JUDGE_ID
	alter table Judge
		add constraint JUDGE_ID primary key (judge_id,full_name,domain)
go

create or alter procedure removeNameDomainPrimaryKey as
	alter table Judge
		drop constraint JUDGE_ID
	alter table Judge
		add constraint JUDGE_ID primary key (judge_id)
go

---e. add / remove a candidate key;

create or alter procedure newCandidateKeyParalegal as
	alter table Paralegal
		add constraint PARALEGAL_CANDIDATE_KEY unique (id, last_name,city)
go

create or alter procedure dropCandidateKeyParalegal as
	alter table Paralegal
		drop constraint PARALEGAL_CANDIDATE_KEY 
go

---f. add / remove a foreign key;
create or alter procedure newForeignKeyJudge as
	alter table Judge
		add constraint JUDGE_FOREIGN_KEY foreign key (lawsuit_id) references Lawsuit(id)
go

create or alter procedure removeForeignKeyJudge as
	alter table Judge
	drop constraint JUDGE_FOREIGN_KEY
go

/*
create table versionsTable (
    version int
)

insert into versionsTable values (1) -- initial version

create table proceduresTable (
    fromVersion int,
    toVersion int,
    primary key (fromVersion, toVersion),
    procedureName varchar(max)
)




insert into proceduresTable values (1, 2, 'setAtourneyAgeToTinyint')
insert into proceduresTable values (2, 1, 'setAtourneyAgeToInt')
insert into proceduresTable values (2, 3, 'addCountryToAtourney')
insert into proceduresTable values (3, 2, 'removeCountryFromAtourney')
insert into proceduresTable values (3, 4, 'addDefaultToLawsuitOfClientProgress')
insert into proceduresTable values (4, 3, 'removeDefaultFromLawsuitOfClientProgress')
insert into proceduresTable values (4, 5, 'addJudge')
insert into proceduresTable values (5, 4, 'dropJudge')
insert into proceduresTable values (5, 6, 'addNameDomainPrimaryKey')
insert into proceduresTable values (6, 5, 'removeNameDomainPrimaryKey')
insert into proceduresTable values (6, 7, 'newCandidateKeyParalegal')
insert into proceduresTable values (7, 6, 'dropCandidateKeyParalegal')
insert into proceduresTable values (7, 8, 'newForeignKeyJudge')
insert into proceduresTable values (8, 7, 'removeForeignKeyJudge')
*/

go 
CREATE OR ALTER PROCEDURE goToVersion(@newVersion INT) 
AS
    DECLARE @curr INT
    DECLARE @procedureName VARCHAR(100)
    SELECT @curr = version FROM versionsTable

    IF  @newVersion > (SELECT MAX(toVersion) FROM proceduresTable)
        RAISERROR ('Bad version', 10, 1)
    ELSE
    BEGIN
        IF @newVersion = @curr
            PRINT('Already on this version!');
        ELSE
        BEGIN
            IF @curr > @newVersion
            BEGIN
                WHILE @curr > @newVersion
                BEGIN
                    SELECT @procedureName = procedureName FROM proceduresTable 
                    WHERE fromVersion = @curr AND toVersion = @curr - 1
                    PRINT('executing: ' + @procedureName);
                    EXEC(@procedureName)
                    SET @curr = @curr - 1
                END
            END

            IF @curr < @newVersion
            BEGIN
                WHILE @curr < @newVersion
                    BEGIN
                        SELECT @procedureName = procedureName FROM proceduresTable
                        WHERE fromVersion = @curr AND toVersion = @curr + 1
                        PRINT('executing: ' + @procedureName);
                        EXEC (@procedureName)
                        SET @curr = @curr + 1
                    END
            END

            UPDATE versionsTable SET version = @newVersion
        END
    END
GO
EXEC goToVersion 2