use practic;

--insert into Category
--values
--		(1, 'c1', 'ceva1'),
--		(2, 'p2', 'ceva2'),
--		(3, 'p3', 'ceva3');

--insert into Producer
--values
--		(1, 'p1', '074562342', 'https:://ceva'),
--		(2, 'p2', '0743333342', 'https:://ceva'),
--		(3, 'p3', '079999342', 'https:://ceva');

--insert into Toy
--values
--		(1,'t1','d1',1,1),
--		(2,'t2','d2',2,2),
--		(3,'t3','d3',3,3);




---T1
begin tran
update Producer set phoneNumber ='0769245233' where id = 1
waitfor delay '00:00:10'
update Toy set toyDescription='deadlockT1' where id = 2
commit tran

select * from Toy;