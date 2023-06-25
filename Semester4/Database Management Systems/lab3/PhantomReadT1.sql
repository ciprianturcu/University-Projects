begin tran
waitfor delay '00:00:05'
insert into Invoice (id, total, services_description, emission)
values(6, 100.0, 'serv6', '2002-02-06')
commit tran

begin tran
waitfor delay '00:00:05'
insert into Invoice (id, total, services_description, emission)
values(7, 100.0, 'serv7', '2002-02-07')
commit tran