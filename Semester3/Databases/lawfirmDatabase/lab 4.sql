/*
create or alter procedure addToTables( @tableName VARCHAR(255)) AS
	if @tableName not in (select TABLE_NAME from INFORMATION_SCHEMA.TABLES) begin
		print 'Table doesn''t exist'
		return
	end
	if @tableName in (select Name from Tables) begin
		print 'Table already in Tables'
		return
	end
	insert into Tables(Name) values (@tableName);
*/

/*
create or alter procedure addToViews(@viewName VARCHAR(255)) as
	if @viewName not in (select TABLE_NAME from INFORMATION_SCHEMA.VIEWS) begin
		print 'View doesn''t exist'
		return
	end
	if @viewName in (select Name from Views) begin
		print 'View already in views'
		return
	end
	insert into Views(Name) values (@viewName);
*/

/*
create or alter procedure addToTests(@testName VARCHAR(255)) as
	if @testName in (select Name from Tests) begin
		print 'Test already in tests'
		return 
	end
	insert into Tests(Name) values (@testName)
	*/

/*
create or alter procedure connectTableToTest(@tableName VARCHAR(255), @testName VARCHAR(255), @rows INT, @pos INT) AS
	if @tableName not in (select Name from Tables) 
	begin
		print 'Table not in Tables'
		return
	end

	if @testName not in (select Name from Tests)
	begin
		print 'Test not in Tests'
		return
	end

	declare @tableId int
	declare @testId int
	set @tableId = (select TableId From Tables where Name = @tableName)
	set @testId = (select TestId from Tests where Name=@testName)

	if exists (select * from TestTables where TestId = @testId and TableId = @tableId)
	begin
		print 'TestTable connection already exists'
	end

	insert into TestTables values(@testId,@tableId,@rows,@pos)
*/

/*
create or alter procedure connectViewToTest(@viewName VARCHAR(255),@testName VARCHAR(255)) as
	if @viewName not in (select Name from Views)
	begin
		print 'table not in views'
		return
	end

	if @testName not in (select Name from Tests)
	begin
		print 'Test not in Tests'
		return
	end

	declare @viewId int
	declare @testId int

	set @viewId = (select ViewId from Views where Name= @viewName)
	set @testId = (select TestId from Tests where Name= @testName)

	if exists(select * from TestViews where TestId = @testId and ViewId = @viewId)
	begin
		print 'TestView connection already exists'
	end

	insert into TestViews values(@testId,@viewId)
*/

/*
create procedure runTest(@testName VARCHAR(255) , @description VARCHAR(255)) as
	if @testName not in (select Name from Tests)
	begin
		print 'Test not in tests'
		return 
	end

	declare @testStartTime DATETIME2
	declare @testRunId INT
	declare @tableId INT
	declare @table VARCHAR(255)
	declare @rows INT
	declare @pos INT
	declare @command VARCHAR(255)
	declare @tableInsertStartTime DATETIME2
	declare @tableInsertEndTime DATETIME2
	declare @testId INT
	declare @view VARCHAR(255)
	declare @viewId INT
	declare @viewStartTime DATETIME2
	declare @viewEndTime DATETIME2

	set @testId = (select TestId from Tests T where T.Name = @testName)

	declare tableCursor cursor scroll for
		select T1.Name, T1.TableId, T2.NoOfRows, T2.Position
		FROM Tables T1 inner join TestTables T2 on T1.TableID = T2.TableID
		where T2.TestID = @testId
		order by T2.Position desc

	declare viewCursor cursor scroll for
		select V.Name, V.ViewId
		from Views V inner join TestViews TV on V.ViewID = TV.ViewID
		where TV.TestID = @testId

	set @testStartTime = sysdatetime()

	insert into TestRuns(Description,StartAt,EndAt) values (@description, @testStartTime, @testStartTime)
	set @testRunId= SCOPE_IDENTITY()

	open tableCursor
	fetch first from tableCursor into @table, @tableId,@rows,@pos

	while @@FETCH_STATUS = 0
	begin
		exec ('DELETE FROM ' + @table)
		fetch tableCursor into @table, @tableId, @rows, @pos
	end

	fetch first from tableCursor into @table, @tableId,@rows,@pos

	while @@FETCH_STATUS = 0
	begin
		set @command = 'populateTable' + @table
		if @rows > 0 and @command not in (select ROUTINE_NAME from INFORMATION_SCHEMA.ROUTINES)
		begin
			print @command + ' does not exist'
			return
		end
		set @tableInsertStartTime = sysdatetime()
		if @rows > 0
		begin
			exec @command @rows
		end

		set @tableInsertEndTime = sysdatetime()

		insert into TestRunTables values(@testRunId,@tableId,@tableInsertStartTime,@tableInsertEndTime)
		fetch from tableCursor into @table, @tableId, @rows, @pos 
	end
	close tableCursor
	deallocate tableCursor

	open viewCursor
	fetch viewCursor into @view, @viewId

	while @@FETCH_STATUS = 0
	begin
		set @viewStartTime = sysdatetime()
		exec ('SELECT * FROM ' + @view)
		set @viewEndTime = sysdatetime()
		insert into TestRunViews values(@testRunID, @viewId, @viewStartTime, @viewEndTime)
		fetch viewCursor into @view ,@viewId
	end
	close viewCursor
	deallocate viewCursor
	update TestRuns
	set EndAt = sysdatetime()
	where TestRunID = @testRunId;
	*/


---test1 


/*
create view LAWSUIT_VIEW AS
	select * from Lawsuit;
*/

/*
create or alter procedure populateTableLawsuit (@rows INT) as
	while @rows > 0 
	begin
		insert into Lawsuit VALUES (@rows, 'test_'+CONVERT(VARCHAR(255),@rows))
		set @rows = @rows-1
	end
*/

/*
exec addToTables 'Lawsuit';
exec addToViews 'LAWSUIT_VIEW';
exec addToTests 'LAWSUIT_TEST';
exec connectTableToTest 'Lawsuit', 'LAWSUIT_TEST', 10000,1;
exec connectViewToTest 'LAWSUIT_VIEW', 'LAWSUIT_TEST';
*/

--exec runTest 'LAWSUIT_TEST', 'TEST1';

/*
create view CLIENT_INVOICE_VIEW as
	select C.first_name + ' ' + C.last_name AS NAME, I.total AS total
	from Client C inner join Invoice I on C.invoice_id = I.id
*/

/*
create or alter procedure populateTableClient(@rows INT) as
	while @rows > 0 
	begin
		insert into Client values(@rows, 'testfn_'+CONVERT(VARCHAR(255),@rows), 'testln_'+CONVERT(VARCHAR(255),@rows), '1987-06-24', FLOOR(RAND()*@rows)+1,'testc_'+CONVERT(VARCHAR(255),@rows) )
		set @rows = @rows - 1
	end
*/

/*
create or alter procedure populateTableInvoice(@rows INT) as
	while @rows > 0 
	begin
		insert into Invoice values (@rows, FLOOR(RAND()*1000000)+1, 'test_'+CONVERT(VARCHAR(255),@rows), '1987-06-24')
		set @rows = @rows - 1
	end
*/

/*
exec addToTables 'Client';
exec addToTables 'Invoice';
exec addToViews 'CLIENT_INVOICE_VIEW';
exec addToTests 'CLIENT_INVOICE_TEST';

exec connectTableToTest 'Invoice' , 'CLIENT_INVOICE_TEST', 10000, 2;
exec connectTableToTest 'Client', 'CLIENT_INVOICE_TEST', 8000, 1;

exec connectViewToTest 'CLIENT_INVOICE_VIEW', 'CLIENT_INVOICE_TEST';
*/

--exec runTest 'CLIENT_INVOICE_TEST', 'TEST2';

/*
alter table Atourney_helper
drop constraint PK__Atourney__3213E83FEC64A014;

alter table Atourney_helper
alter column task_deadline VARCHAR(255) not null;

alter table Atourney_helper
add constraint PK_Atourney_helper primary key (id, task_deadline);
*/

/*
create view PARALEGAL_COUNT as
	select P.first_name+ ' '+ P.last_name as NAME, count(*) as NROFTSK
	from Paralegal P inner join Atourney_helper AH on P.id = AH.paralegal_id
	group by P.first_name + ' ' + p.last_name
*/

/*
create or alter procedure populateTableParalegal(@rows INT) as
	while @rows > 0
	begin
		insert into Paralegal values (@rows, 'testfn_' + CONVERT(VARCHAR(255),@rows), 'testln_'+CONVERT(VARCHAR(255),@rows), 'senior', 30, 'city')
		set @rows = @rows -1
	end
*/
	
/*
create or alter procedure populateTableAtourney(@rows INT) as
	while @rows > 0
	begin
		insert into Atourney values (@rows, 'testfn_'+CONVERT(VARCHAR(255),@rows), 'testln_'+CONVERT(VARCHAR(255),@rows), 100000,'senior',30,'spec','city','country')
		set @rows = @rows - 1
	end
*/

/*
alter table Atourney_helper
drop constraint PK_Atourney_helper

alter table Atourney_helper
drop column id

delete from Atourney_helper

alter table Atourney_helper
add id int not null;

alter table Atourney_helper
add constraint PK_Atourney_helper primary key (id, task_deadline);
*/

/*
create or alter procedure populateTableAtourney_helper(@rows INT) as
	
	declare @atourneyMaxId INT
	set @atourneyMaxId = (select max(id) from Atourney)
	
	declare @paralegalMaxId INT
	set @paralegalMaxId = (select max(id) from Paralegal)

	while @rows > 0
	begin
		insert into Atourney_helper values (FLOOR(RAND()*@atourneyMaxId)+1, FLOOR(RAND()*@paralegalMaxId)+1, 'testtask_'+CONVERT(VARCHAR(255),@rows), CONVERT(VARCHAR(10), DATEADD(DAY, FLOOR(RAND() * (DATEDIFF(DAY, '20000101', '20210101'))), '20000101'), 120), @rows)
		set @rows = @rows - 1
	end

*/

/*
exec addToTables 'Atourney';
exec addToTables 'Paralegal';
exec addToTables 'Atourney_helper';
exec addToViews 'PARALEGAL_COUNT';
exec addToTests 'PARALEGAL_COUNT_TEST';
exec connectTableToTest 'Atourney_helper' , 'PARALEGAL_COUNT_TEST', 2000, 1;
exec connectTableToTest 'Atourney' , 'PARALEGAL_COUNT_TEST', 5000, 2;
exec connectTableToTest 'Paralegal', 'PARALEGAL_COUNT_TEST', 5000, 3;

exec connectViewToTest 'PARALEGAL_COUNT', 'PARALEGAL_COUNT_TEST';
*/

---exec runTest 'PARALEGAL_COUNT_TEST', 'TEST3';

/*
select * from TestRuns;

delete from TestRuns;
delete from TestViews;
delete from TestRunTables;
delete from TestRunViews;
delete from TestTables;
delete from Views;
delete from Tests;
*/

select DATEDIFF(MILLISECOND, StartAt, EndAt) as time
from TestRuns

select * from TestRuns
select * from TestRunTables
select * from TestRunViews