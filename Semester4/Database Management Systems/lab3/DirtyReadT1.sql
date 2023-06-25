insert into Invoice(id, total, services_description, emission)
values 
	(1, 100.0, 'serv1', '2002-02-01'),
	(2, 100.0, 'serv2', '2002-02-02'),
	(3, 100.0, 'serv3', '2002-02-03')
select * from Invoice

begin transaction
update Invoice set services_description='servChanged'
where id = 2
waitfor delay '00:00:10'
rollback transaction