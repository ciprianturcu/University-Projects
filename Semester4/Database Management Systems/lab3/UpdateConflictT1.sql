waitfor delay '00:00:05'
begin tran
update Invoice set services_description = 'servupdate11' where id = 2
waitfor delay '00:00:05'

select * from Invoice
commit tran
