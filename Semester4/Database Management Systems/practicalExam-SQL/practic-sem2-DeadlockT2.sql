--set DEADLOCK_PRIORITY high --solution


--T2
begin tran
update Toy set toyDescription='deadlockT2' where id = 2
waitfor delay '00:00:10'
update Producer set  phoneNumber ='076999993' where id = 1
commit tran