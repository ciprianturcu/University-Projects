set transaction isolation level read uncommitted
--set transaction isolation level read committed --solution
begin transaction
select * from Invoice
waitfor delay '00:00:15'
select * from Invoice
commit transaction