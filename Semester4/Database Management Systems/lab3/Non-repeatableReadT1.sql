insert into Invoice(id, total, services_description, emission)
values (4, 100.0, 'serv4', '2002-02-04')


begin tran
waitfor delay '00:00:05'
update Invoice set services_description = 'servChange4455'
where id = 4
commit tran

insert into Invoice(id, total, services_description, emission)
values (5, 100.0, 'serv5', '2002-02-05')


begin tran
waitfor delay '00:00:05'
update Invoice set services_description = 'servChanged5'
where id = 5
commit tran