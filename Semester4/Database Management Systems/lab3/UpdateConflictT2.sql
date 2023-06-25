set transaction isolation level snapshot
begin tran
select * from Invoice
waitfor delay '00:00:05'
update Invoice set services_description = 'servupdate22' where id =2
select * from invoice
commit tran

--ALTER DATABASE lawfirm SET ALLOW_SNAPSHOT_ISOLATION ON
