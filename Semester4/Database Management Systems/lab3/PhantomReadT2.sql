set transaction isolation level repeatable read
--set transaction isolation level serializable -- solution

begin tran
select * from Invoice
waitfor delay '00:00:05'
select * from Invoice
commit tran
