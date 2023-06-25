--table A: Attorney, table B: Invoice

set DEADLOCK_PRIORITY high --solution

begin tran
update Invoice set services_description='servdead2' where id = 2
waitfor delay '00:00:10'
update Attorney set specialization = 'Tax' where id = 1 
commit tran

