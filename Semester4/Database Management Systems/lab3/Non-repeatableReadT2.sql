set transaction isolation level read committed
--set transaction isolation level repeatable read -- solution
begin tran
select * from Invoice
waitfor delay '00:00:05'
select * from Invoice
commit tran