---Attorney, Attorney_for_invoice, Invoice

create or alter procedure addRowAttorney @first_name VARCHAR(30), @last_name VARCHAR(30), @fee INT, @experience VARCHAR(30), @age INT, @specialization VARCHAR(30), @city VARCHAR(30) as
begin
	---don't display nr of afected rows
	set nocount on
	---getting the max id already in the table
	declare @maxId int
	set @maxId = 1
	select top 1 @maxId = id + 1  from Attorney order by id desc

	---validate input
	declare @error VARCHAR(max)
	set @error = ''
	if(@first_name is NULL)
	begin
		set @error = 'First name must not be null'
		raiserror('First name must not be null', 16, 1);
	end

	if(@last_name is NULL)
	begin
		set @error = 'Last name must not be null'
		raiserror('Last name must not be null', 16, 1);
	end

	if(@fee < 0)
	begin
		set @error = 'Fee must be positive'
		raiserror('Fee must be positive', 16, 1);
	end

	if(@experience is NULL)
	begin
		set @error = 'Experience must not be null'
		raiserror('Experience must not be null', 16, 1);
	end

	if(@age < 0)
	begin
		set @error = 'Age must be positive'
		raiserror('Age must be positive', 16, 1);
	end

	if(@specialization is NULL)
	begin
		set @error = 'Specialization must not be null'
		raiserror('Specialization must not be null', 16, 1);
	end

	if(@city is NULL)
	begin
		set @error = 'City must not be null'
		raiserror('City must not be null', 16, 1);
	end

	---insert data
	insert into Attorney(id, first_name, last_name, fee, experience, age, specialization, city)
	values (@maxId, @first_name, @last_name, @fee, @experience, @age, @specialization, @city)
	declare @fullName VARCHAR(100)
	set @fullName = @first_name + ' ' + @last_name
	exec sp_log_changes '', @fullName, 'Add row to attorney', @error
end

go
create or alter procedure addRowInvoice @total FLOAT, @services_description VARCHAR(100), @emission DATE as
begin
	---don't display nr of afected rows
	set nocount on
	---getting the max id already in the table
	declare @maxId int
	set @maxId = 1
	select top 1 @maxId = id + 1  from Invoice order by id desc

	---validate input
	declare @error VARCHAR(max)
	set @error = ''
	if(@services_description is NULL)
	begin
		set @error = 'Service description must not be null'
		raiserror('Service description must not be null', 16, 1);
	end

	if(@emission is NULL)
	begin
		set @error = 'Emission date must not be null'
		raiserror('Emission date must not be null', 16, 1);
	end

	if(@total < 0)
	begin
		set @error = 'Total must be positive'
		raiserror('Total must be positive', 16, 1);
	end

	insert into Invoice(id, total, services_description, emission)
	values (@maxId, @total, @services_description, @emission)
	exec sp_log_changes '', @services_description, 'Add row to invoice', @error
end

go 
create or alter procedure addRowAttorneyForInvoice @attorneyFirstName VARCHAR(30), @attorneyLastName VARCHAR(30), @servicesDescription VARCHAR(30) as
begin
	---don't display nr of afected rows
	set nocount on

	---validate input
	declare @error VARCHAR(max)
	set @error = ''

	if(@attorneyFirstName is NULL)
	begin
		set @error = 'First name must not be null'
		raiserror('First name must not be null', 16, 1);
	end

	if(@attorneyLastName is NULL)
	begin
		set @error = 'Last name must not be null'
		raiserror('Last name must not be null', 16, 1);
	end

	if(@servicesDescription is NULL)
	begin
		set @error = 'Service description must not be null'
		raiserror('Service description must not be null', 16, 1);
	end

	---get ids of entities 
	declare @attorneyId int
	set @attorneyId = (select id from Attorney where first_name = @attorneyFirstName and last_name = @attorneyLastName)
	if(@attorneyId is NULL)
	begin
		set @error = 'No attorney with the given name'
		raiserror(@error, 16, 1);
	end

	declare @invoiceId int 
	set @invoiceId = (select id from Invoice where services_description = @servicesDescription)
	if(@invoiceId is NULL)
	begin
		set @error = 'No invoice with the given description'
		raiserror(@error, 16, 1);
	end

	insert into Attorney_for_invoice values(@attorneyId, @invoiceId)
	declare @fullName VARCHAR(100)
	set @fullName = @attorneyFirstName + ' ' + @attorneyLastName
	declare @newData VARCHAR(350)
	set @newData = @fullName+ ' ,' + @servicesDescription
	exec sp_log_changes '', @newData, 'Connect attorney to invoice', @error
end

go 
create or alter procedure successfulAddRollback as
begin
	
	begin tran
	begin try
		exec addRowAttorney 'Mircea', 'Bravo', 1000, 'Senior', 43, 'Criminal', 'Cluj-Napoca'
		exec addRowInvoice 4000.0, 'Services', '2022-11-06'
		exec addRowAttorneyForInvoice 'Mircea', 'Bravo', 'Services'
	end try
	begin catch
		rollback tran
		exec sp_log_changes '','','rolledback all op', ''
		return
	end catch
	commit tran
end

go
create or alter procedure failAddRollback as 
begin
	begin tran
	begin try
		exec addRowAttorney 'Mircea', 'Bravo', 1000, 'Senior', 43, 'Criminal', 'Cluj-Napoca'
		exec addRowInvoice 4000.0, 'Services', '2022-11-06'
		exec addRowAttorneyForInvoice 'Mir', 'Bravo', 'Services'
	end try
	begin catch
		rollback tran
		exec sp_log_changes '','','rolledback all op', ''
		return
	end catch
	commit tran
end

exec successfulAddRollback
exec failAddRollback

select * from Attorney
select * from Invoice
select * from Attorney_for_invoice
select * from LogChanges
delete from Attorney_for_invoice
delete from Invoice
delete from Attorney
delete from LogChanges