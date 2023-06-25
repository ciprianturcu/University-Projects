use practic;

create table ToyStore(
	id int not null,
	storeName varchar(30),
	phoneNumber varchar(10),
	website varchar(100),
	primary key(id)
);

create table Producer(
	id int not null,
	producerName varchar(30),
	phoneNumber varchar(10),
	website varchar(100),
	primary key(id)

);

create table Category(
	id int not null,
	categoryName varchar(30),
	categoryDescription varchar(100),
	primary key(id)
);

create table Toy(
	id int not null,
	toyName varchar(30),
	toyDescription varchar(100),
	producerId int not null,
	categoryId int not null,
	primary key(id),
	foreign key(producerId)
		references Producer(id)
		on delete cascade,
	foreign key(categoryId)
		references Category(id)
		on delete cascade,

);

create table Catalog(
	toyId int not null,
	storeId int not null,
	price int not null,
	foreign key(toyId)
		references Toy(id)
		on delete cascade,
	foreign key(storeId)
		references ToyStore(id)
		on delete cascade,

);

