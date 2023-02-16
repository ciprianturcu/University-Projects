/*create table Ta (
	aid int not null,
	a2 int
);

alter table Ta 
add constraint Ta_PRIMARY_KEY primary key (aid)

alter table Ta
add constraint Ta_UNIQUE_a2 UNIQUE (a2);

alter table Ta
add additional_data varchar(200)


create table Tb (
	bid int not null,
	b2 int
);

alter table Tb
add constraint  Tb_PRIMARY_KEY primary key (bid);

create table Tc(
	cid int not null,
	aid int not null,
	bid int not null
);

alter table Tc
add constraint Tc_PRIMARY_KEY primary key (cid);

alter table Tc
add constraint Tc_FOREIGN_KEY_aid foreign key(aid) references Ta(aid) on delete cascade;

alter table Tc
add constraint Tc_FOREIGN_KEY_bid foreign key(bid) references Tb(bid) on delete cascade;
*/


/*
create or alter procedure populateTableTa(@rows INT) as
	while @rows > 0
	begin
		insert into Ta values (@rows, @rows - 7000 / @rows + @rows / 4, 'test_'+CONVERT(VARCHAR(255),@rows))
		set @rows = @rows - 1
	end
*/

/*
create or alter procedure populateTableTb (@rows INT) as
	while @rows > 0 
	begin
		insert into Tb values (@rows, @rows - 4500 / @rows + @rows / 2)
		set @rows = @rows - 1
	end
*/

/*
create or alter procedure populateTableTc (@rows INT) as
	if @rows > (select count(*) from Ta) * (select count(*) from Tb)
	begin
		RAISERROR ('Too many entities requested', 10, 1)
	end

	declare valueCursor CURSOR LOCAL FOR (
		select a.aid, b.bid
		from Ta a cross join Tb b
	)

	declare @aid INT
	declare @bid INT

	open valueCursor
	fetch valueCursor into @aid, @bid

	while @@FETCH_STATUS =0 and @rows > 0
	begin
		insert into Tc values (@rows, @aid, @bid)
		fetch valueCursor into @aid, @bid
		set @rows = @rows - 1
	end
	close valueCursor
	deallocate valueCursor

*/

delete from Ta
delete from Tb
delete from Tc

/*
exec populateTableTa 10000;
exec populateTableTb 10000;
exec populateTableTc 10000;
*/
---clustered index scan (entire clustered index)
select * from Ta;

---clustered index seek (only a subset from clustered index)
select * from Ta where aid < 700;

---nonclustered index scan (entire nonclustered index)
select a2 from Ta;

---nonclustered index seek (subset from nonclustered index)
select a2 from Ta where a2 = 300;

---key lookup (nonclustered index seek + additional data)
select a.additional_data, a.a2
from Ta a
where a.a2 = 100

--b

select b2 from Tb
where b2 = 90 --here it is a clustered index seek 0.03 cost

/*
drop index if exists Tb_b2_NonClustered on Tb
create nonclustered index Tb_b2_NonClustered on Tb(b2)
*/

select b2 from Tb
where b2 = 81 --here it is a clustered index seek 0.003 cost

--c 
go
create or alter view viewC as
	select a.aid, b.bid, c.cid
	from Tc c
	inner join Ta a on a.aid = c.aid
	inner join Tb b on b.bid = c.bid
	where b.b2 > 400 and a.additional_data = 'test_2057'
go

---0.350 automatically created ones
---0.342 automatically created ones + nonclustered on b2
---0.299 automatically created ones + nonclustered on b2 + nonclustered additional_data
---0.267 automatically created ones + nonclustered on b2 + nonclustered additional_data + nonclustered on (aid,bid) from Tc

select * from viewC
/*
drop index idx_Tc_aid_bid on Tc
CREATE NONCLUSTERED INDEX idx_Tc_aid_bid ON Tc(aid, bid);

DROP INDEX Ta_additional_data_index ON Ta
CREATE NONCLUSTERED INDEX Ta_additional_data_index ON Ta(additional_data)
*/
