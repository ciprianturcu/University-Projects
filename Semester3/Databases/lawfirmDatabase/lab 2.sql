use lawfirm;

-- INSERT
insert into Atourney
values
	(1,'Mike','Ross', 17000, 'junior',30,'fraud','Cluj-Napoca'),
	(2,'Harvey','Specter',25000,'senior',46,'criminal','Targu-Mures'),
	(3,'Louis','Litt',25000,'senior',53,'tax','Targu-Mures'),
	(4,'Jessica', 'Pearson', 50000, 'senior',57,'corporate','Cluj-Napoca'),
	(5,'Samantha','Wheeler',15000,'mid',33,'corporate','Cluj-Napoca'),
	(6,'Dana','Scott',14000,'mid',36,'tax','Suceava'),
	(7,'Harold','Gunderson',5000,'junior',25,'criminal','Barlad');

delete from Atourney;

insert into Paralegal
values
	(1,'Rachel','Zane','senior',37,'Targu-Mures'),
	(2,'Donna','Paulsen','senior',46,'Barlad'),
	(3,'Oliver','Smith','mid',35,'Suceava'),
	(4,'Poppy','Jones','mid',30,'Cluj-Napoca'),
	(5,'Lily','Brown','junior',21,'Cluj-Napoca'),
	(6,'Isla','Miller','junior',20,'Targu-Mures'),
	(7,'Sophie','Davis','junior',24,'Targu-Mures');


select * from Paralegal;

select * from Atourney_helper;

insert into Document
values
	(1,'contract','2022-11-06'),
	(2,'affidavit','2022-04-15'),
	(3,'contract','2015-01-10'),
	(4,'affidavit','2016-05-20'),
	(5,'will','2014-04-09'),
	(6,'contract','2022-04-15'),
	(7,'will','2022-04-15'),
	(8,'affidavit','2022-03-08'),
	(9,'affidavit','2014-04-09'),
	(10,'contract','2016-09-20'),
	(11,'affidavit','2017-10-23'),
	(12,'will','2020-10-20'),
	(13,'contract','2016-10-23'),
	(14,'non-disclosure agreement','2022-03-08'),
	(15,'gift deed','2019-08-14'),
	(16,'gift deed','2021-07-19'),
	(17,'gift deed','2021-12-12'),
	(18,'prenuptial agreement','2022-12-09'),
	(19,'prenuptial agreement','2014-04-09'),
	(20,'non-disclosure agreement','2022-04-15'),
	(21,'power of attorney','2022-12-09'),
	(22,'non-disclosure agreement','2018-05-17'),
	(23,'power of attorney','2017-11-09'),
	(24,'non-disclosure agreement','2022-04-09'),
	(25,'gift deed','2022-02-09'),
	(26,'power of attorney','2022-03-08');

insert into Lawsuit
values
	(1,'money laundering'),
	(2,'dui'),
	(3,'fraud'),
	(4,'kidnapping'),
	(5,'fraud'),
	(6,'murder'),
	(7,'fraud'),
	(8,'money laundering'),
	(9,'murder'),
	(10,'kidnapping'),
	(11,'tax fraud'),
	(12,'fraud'),
	(13,'murder'),
	(14,'money laundering'),
	(15,'dui'),
	(16,'kidnapping'),
	(17,'murder'),
	(18,'dui'),
	(19,'dui'),
	(20,'murder'),
	(21,'murder');

insert into Lawsuit
values
	(NULL,NULL,NULL),
	(404,'2023-12-23',NULL),
	(403,NULL,'ongoing');

insert into Invoice
values
	(1,10000.00,'consulting','2019-08-01'),
	(2,15520.00,'win','2022-07-29'),
	(3,10000.00,'win','2021-12-12'),
	(4,14520.00,'win','2021-08-14'),
	(5,10000.00,'consulting','2020-12-17'),
	(6,30000.00,'consulting','2020-11-09'),
	(7,49670.00,'win','2018-01-10'),
	(8,10000.00,'consulting','2022-04-19'),
	(9,15600.00,'consulting','2022-05-21'),
	(10,10000.00,'lost','2021-06-25'),
	(11,190000.00,'win','2020-07-28'),
	(12,45700.00,'win','2021-08-22'),
	(13,10000.00,'lost','2022-09-27'),
	(14,1000000.00,'win','2022-10-18'),
	(15,400000.00,'win','2022-10-23'),
	(16,15500.00,'lost','2021-12-13');

insert into Atourney_on_lawsuit
values
	(1,1),
	(2,2),
	(7,3),
	(2,4),
	(1,5),
	(5,6),
	(6,7),
	(2,7),
	(1,3),
	(1,21),
	(2,20),
	(1,14),
	(1,11),
	(6,17),
	(5,18),
	(4,2),
	(3,13),
	(4,12),
	(4,11),
	(1,19),
	(6,21),
	(5,7),
	(3,8),
	(7,9),
	(1,10),
	(3,1),
	(7,2),
	(1,2),
	(3,2);

insert into Client
values 
	(1,'Darryl','Carpenter','1976-06-15',1,'Cluj-Napoca'),
	(2,'Kendal','Fletcher','1977-09-29',4,'Targu-Mures'),
	(3,'Kaitlin','Park','1980-10-07',5,'Targu-Mures'),
	(4,'Declan','Diaz','1982-05-14',2,'Cluj-Napoca'),
	(5,'Raul','Boone','1983-05-01',null,'Suceava'),
	(6,'Lilah','Riley','1983-07-21',6,'Cluj'),
	(7,'Nathalie','Kelly','1988-01-26',9,'Suceava'),
	(8,'Ronin','Rush','1988-06-09',null,'Cluj'),
	(9,'Melina','Mcintosh','1989-10-28',8,'Targu-Mures'),
	(10,'Tyrese','Reyes','1990-09-02',10,'Barlad'),
	(11,'Chace','Sosa','1991-04-26',14,'Targu-Mures'),
	(12,'Cameron','Watts','1992-11-12',null,'Cluj-Napoca'),
	(13,'Kobe','Bird','1993-06-12',4,'Cluj-Napoca'),
	(14,'Jaylen','Melendez','1994-10-21',11,'Cluj'),
	(15,'Gabriela','Salazar','1995-01-16',12,'Targu-Mures'),
	(16,'Kaleigh','Bright','1996-07-11',15,'Iasi'),
	(17,'Beckett','Pineda','1999-07-08',null,'Bucuresti'),
	(18,'Amaya','Wyatt','2001-10-27',12,'Cluj'),
	(19,'Ryann','Cannon','2002-10-16',13,'Timisoara'),
	(20,'Harold','Gunderson', '1997-05-20', null, 'Barlad');


insert into Lawsuit_of_client
values
	(13,14,10,'2023-04-24','ongoing'),
	(5,3,1,'2021-05-29','completed'),
	(20,10,2,'2024-01-14','ongoing'),
	(8,15,7,'2025-10-28','ongoing'),
	(7,3,8,'2019-05-25','completed'),
	(11,3,9,'2026-09-01','ongoing'),
	(16,13,3,'2019-08-24','completed'),
	(18,14,6,'2014-02-24','completed'),
	(14,4,6,'2028-11-24','ongoing'),
	(4,18,6,'2021-12-24','completed'),
	(3,16,2,'2017-12-24','completed'),
	(19,14,3,'2018-03-24','completed'),
	(8,4,null,'2023-07-24','ongoing'),
	(6,2,null,'2023-06-24','ongoing'),
	(19,16,null,'2023-04-24','ongoing'),
	(13,2,null,'2023-11-12','ongoing'),
	(17,14,1,'2023-11-12','ongoing'),
	(11,15,8,'2023-11-16','ongoing'),
	(21,5,9,'2023-11-17','ongoing'),
	(20,10,10,'2023-11-05','ongoing'),
	(2,14,4,'2023-04-06','ongoing');


insert into Document_on_lawsuit
values
	(4, 17, 5, 'rejected'),
	(3, 21, 4,'denied'),
	(16, 3, 3,'lost'),
	(10, 19, 3,'accepted'),
	(9, 4, 6,'processed'),
	(2, 21, 4,'under consideration'),
	(1, 4, 3,'denied'),
	(12, 20, 7,'denied'),
	(17, 6, 1,'rejected'),
	(20, 11, 2,'under consideration'),
	(7, 12, 1,'denied'),
	(16, 8, 2,'rejected'),
	(21, 8, 1,'lost'),
	(16, 3, 6,'rejected'),
	(23, 8, 6,'under consideration'),
	(17, 15, 4,'rejected'),
	(18, 7, 5,'under consideration'),
	(6, 11, 6,'lost'),
	(19, 4, 1,'rejected'),
	(1, 3, 4,'under consideration'),
	(20, 9, 6,'under consideration'),
	(20, 20, 1,'under consideration'),
	(5, 13, 6,'under consideration'),
	(23, 21, 4,'rejected'),
	(13, 17, 6,'under consideration');

insert into Atourney_helper
values
	(1,4,'draft documents','2022-11-29'),
	(1,5,'schedule meeting with client' , '2022-12-01' ),
	(7,2,'order documents from plaintiff','2022-11-07'),
	(4,4,'order document requests','2022-11-01'),
	(4,5,'draft documents','2022-07-29'),
	(5,4,'draft contract','2022-08-19'),
	(5,5,'draft documents','2022-11-18'),
	(6,3,'schedule meeting with client','2022-12-07'),
	(7,2,'schedule meeting with client','2022-11-04'),
	(4,5,'draft contract','2023-01-17'),
	(5,4,'schedule meeting with client','2020-11-29'),
	(4,5,'draft documents','2021-11-29'),
	(5,4,'schedule meeting with client','2022-11-29'),
	(7,2,'draft documents','2022-11-29'),
	(1,4,'schedule meeting with client','2022-11-29'),
	(1,4,'draft documents','2022-11-29'),
	(6,3,'schedule meeting with client','2022-11-29'),
	(7,2,'draft contract','2022-11-29');


--update, all junior atourneys  will get equal salaries if they are in cluj-napoca
update Atourney
set fee=10000
where experience = 'junior' and city = 'Cluj-Napoca';

update Paralegal
set age =50 
where id = 6;

--update , all invoices lower than 15000 will be catalogued as losses setting basic fee of loss to 15000
update Invoice
set services_description='loss'
where total <= 15000;

--update, all tasks deadlines that are null should be set to a week after current date if the task is not null
update Atourney_helper
set task_deadline=(Select GETDATE() 'Today',
						Dateadd(week,2,GETDATE()))
where task is not null;

--update , atourney who lives in cluj and suceava gets a 
update Atourney 
set experience = 'senior'
where city in ('Cluj-Napoca','Suceava') and experience='mid';

update Atourney
set experience = 'mid'
where age between 28 and 40;

update Client
set city = 'Cluj-Napoca'
where city like 'Cluj%';

-- delete

delete from Document_on_lawsuit
where admission_status in ('rejected','denied','lost');

delete from Atourney_helper
where task is null;

--- a and b
--get all employee names
select first_name,last_name
from Atourney
union
select first_name,last_name
from Paralegal
-- checking if there are any clients that are a employee at the firm so we avoid conflict of interests
select first_name, last_name
from Client
intersect
(
select first_name,last_name
from Atourney
union
select first_name,last_name
from Paralegal
);
---get all atourneys that are a senior or have a fee over 20000$
select first_name, last_name, experience , specialization
from Atourney
where experience = 'senior' OR fee>20000;
-- checking if there are any atourneys on specific specializations or have a rank of senior, senior atourneys can handle any specialization in a case
select first_name, last_name, experience , specialization
from Atourney
where experience = 'senior' OR specialization in ('corporate','tax','fraud');

--c 
select * from Lawsuit_of_client;
select * from Lawsuit;
--all lawsuits that are not with a charge of any kind of fraud we handle and are ongoing.
select lawsuit_id , charge, court_date , progress 
from Lawsuit_of_client , Lawsuit
where lawsuit_id = id and progress = 'ongoing'
except
select lawsuit_id , charge, court_date , progress 
from Lawsuit_of_client , Lawsuit
where charge in ('tax fraud','fraud');

--all documents that do not have a class of will or contract
select id,class,emission_date
from Document d , Document_on_lawsuit
where id = document_id
except
select id,class,emission_date
from Document
where class not in ('contract' , 'will');

--d
--toti clientii care au cel putin un lawsuit activ
select distinct first_name, last_name
from Client c 
inner join Lawsuit_of_client loc
on c.id = loc.client_id;

-- showing relevant data of lawsuits where documents have been rejected or the charge was a dui and the task deadline for the atourney helper is the last 4 months of the year ordered by the task deadline 
select a.id , a.first_name, a.last_name ,p.id , p.first_name, p.last_name ,ah.task,ah.task_deadline, dol.document_id, dol.admission_status , l.id, l.charge
from Atourney a
full outer join Atourney_helper ah
On a.id = ah.atourney_id and ah.atourney_id is not null and a.id is not null
full outer join Paralegal p
on p.id = ah.paralegal_id and ah.paralegal_id is not null and p.id is not null
full outer join Document_on_lawsuit dol
on dol.paralegal_id = p.id and dol.paralegal_id is not null
full outer join Lawsuit l
on l.id = dol.lawsuit_id and l.id is not null and dol.lawsuit_id is not null
where (admission_status in ('rejected','denied') or charge ='dui') and task_deadline >= '2022-09-01'
order by task_deadline

-- which atourney is on which case
select distinct a.first_name, a.last_name,  l.id , l.charge
from Atourney_on_lawsuit aol
left join Atourney a on a.id = aol.atourney_id
left join Lawsuit l on l.id = aol.lawsuit_id

--document details with admission status and who is responsible of it
select d.class, d.emission_date , dol.admission_status , p.first_name, p.last_name
from Document d
right join Document_on_lawsuit dol on d.id = dol.document_id
right join Paralegal p on p.id = dol.paralegal_id

--e
--see the clients that have an invoice 
select invoice_id,c.first_name, c.last_name
from Client c
where invoice_id in (select id from Invoice)
order by invoice_id;
-- see the ongoing cases worth more than 15k
select l.id , l.charge, loc.court_date, loc.client_id
from Lawsuit_of_client loc, Lawsuit l
where loc.client_id in(select c.id
					from Client c
					where invoice_id in (select id from Invoice where total > 15000)) and loc.progress = 'ongoing' and l.id = loc.lawsuit_id
-- paralegals who work on documents that are contracts
select p.id,p.first_name, p.last_name
from Paralegal p 
where p.id in (select distinct dol.paralegal_id
			from Document_on_lawsuit dol
			where dol.document_id in(select id from Document where class = 'contract')
			);

--f
-- paralegals who work on documents that are contracts
select p.id,p.first_name, p.last_name
from Paralegal p 
where exists (select dol.paralegal_id
			from Document_on_lawsuit dol
			where dol.document_id in(select id from Document where class = 'contract') and dol.paralegal_id = p.id
			);
-- paralegal who has a task in drafting a document or contract
select p.id, p.first_name,p.last_name
from Paralegal p 
where exists(
			select ah.paralegal_id
			from Atourney_helper ah
			where task in ('draft contract','draft documents') and ah.paralegal_id = p.id
			);


--g
--print atourneys which were on cases with a client rating over 7 order alphabetically in descending order by first_name
select (t.first_name)+(' ')+(t.last_name) as name
from(
	select a.first_name, a.last_name	
	from Atourney a
	where exists(
				select aol.atourney_id
				From Atourney_on_lawsuit aol, Lawsuit_of_client loc
				where aol.lawsuit_id = loc.lawsuit_id and a.id = aol.atourney_id and loc.client_rating >7
				)

)t
order by first_name desc;

--print invoice that are more than 50k and have been in a lawsuit with the charge of dui
select top 5 i.id,i.total,i.services_description, t.first_name, t.last_name, t.lawsuit_id
from(
	select tt.invoice_id , tt.first_name, tt.last_name, tt.lawsuit_id
	from (
			select c.invoice_id , c.first_name, c.last_name, l.id as lawsuit_id
			from Lawsuit_of_client loc , Lawsuit l, Client c
			where loc.lawsuit_id = l.id and l.charge = 'dui' and loc.client_id = c.id 
	)tt
)t , Invoice i
where i.id = t.invoice_id and i.total >= 50000
order by i.total desc


--h
--select * from Atourney a
--where a.id in (
--		select distinct aol.atourney_id
--		from Atourney_on_lawsuit aol
--		where aol.lawsuit_id in (select l.id from Lawsuit l where l.charge = 'murder')
--		)

--showing all cities with 2 or more atourneys and their average age
select a.city, avg(a.age) as average_age
from Atourney a
group by a.city
having 2<= (
			select count(*)
			from Atourney a2
			where a.city = a2.city
			);

--get the services total amount of money made
select i.services_description , sum(i.total) as total
from Invoice i
group by i.services_description
having 2<=(select count(*)
			from Invoice i2
			where i.services_description = i2.services_description
			)

-- get top 3 charges that bring the most money
select top 3 l.charge, sum(tt.total) as total
from(
	select loc.lawsuit_id, t.total
	from(
			select c.id as client_id, i.total
			from Client c , Invoice i
			where i.id = c.invoice_id
		)t, Lawsuit_of_client loc
	where t.client_id = loc.client_id
	)tt , Lawsuit l
where tt.lawsuit_id = l.id
group by l.charge
having 1 < (select count(*)
			from Lawsuit l2
			where l.charge = l2.charge
			)
order by total desc

--get minimum and maximum age for each experience level
select a.experience, min(a.age) as minimum_age, max(a.age) as maximum_age
from Atourney a
group by a.experience

--i
--get clients with a rating on a lawsuit greater than 6
select c.first_name, c.last_name
from Client c
where c.id = any (
				select loc.client_id
				from Lawsuit_of_client loc
				where loc.client_rating > 6
				)

select c.first_name, c.last_name
from Client c
where c.id in (
				select loc.client_id
				from Lawsuit_of_client loc
				where loc.client_rating > 6
				)
-- check older paralegals than any of the paralegals who worked with mid or junior atourneys 
select p.first_name, p.last_name
from Paralegal p
where p.age > any (
					select distinct p2.age
					from Paralegal p2 join Atourney_helper ah2 on p2.id = ah2.paralegal_id join Atourney a2 on a2.id = ah2.atourney_id
					where a2.experience in ('mid','junior')
					)

select p.first_name, p.last_name
from Paralegal p
where p.age >		(
					select distinct min(p2.age)
					from Paralegal p2 join Atourney_helper ah2 on p2.id = ah2.paralegal_id join Atourney a2 on a2.id = ah2.atourney_id
					where a2.experience in ('mid','junior')
					)
--get all clients and their raitng that have a lower rating than the clients who worked with an atourney from the same city
select c2.first_name, c2.last_name, loc.client_rating
from Lawsuit_of_client loc , Client c2
where loc.client_rating < all (
	select loc2.client_rating
	from Atourney a join Atourney_on_lawsuit aol on a.id = aol.atourney_id join Lawsuit l on l.id = aol.lawsuit_id join Lawsuit_of_client loc2 on loc2.lawsuit_id = l.id join Client c on c.id = loc2.client_id
	where a.city = c.city and loc2.client_rating is not null
) and loc.client_id = c2.id and loc.client_rating is not null

select c2.first_name, c2.last_name, loc.client_rating
from Lawsuit_of_client loc , Client c2
where loc.client_rating <(
	select min(loc2.client_rating)
	from Atourney a join Atourney_on_lawsuit aol on a.id = aol.atourney_id join Lawsuit l on l.id = aol.lawsuit_id join Lawsuit_of_client loc2 on loc2.lawsuit_id = l.id join Client c on c.id = loc2.client_id
	where a.city = c.city and loc2.client_rating is not null
) and loc.client_id = c2.id and loc.client_rating is not null
--get all atourneys and their fee who have a diffrent fee than any other mid experienced atourneys
select a.first_name, a.last_name, a.fee*12 as anual_fee, a.experience
from Atourney a
where a.fee <> all(
			select a2.fee
			from Atourney a2
			where a2.experience = 'mid'
				)
select a.first_name, a.last_name, a.fee/173 as hourly_fee, a.experience
from Atourney a
where a.fee not in(
			select a2.fee
			from Atourney a2
			where a2.experience = 'mid'
				)