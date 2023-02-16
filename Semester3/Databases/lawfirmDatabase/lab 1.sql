use lawfirm;

create table Atourney(
	id int not null,
	first_name varchar(30) not null,
	last_name varchar(30) not  null,
	fee int not null,
	experience varchar(30) not null,
	age int not null,
	specialization varchar(30) not null,
	city varchar(30) not null,
	primary key(id)
);

create table Paralegal(
	id int not null,
	first_name varchar(30) not null,
	last_name varchar(30) not  null,
	experience varchar(30) not null,
	age int not null,
	city varchar(30) not null,
	primary key(id)
);

create table Atourney_helper(
	id int Identity(1,1),
	atourney_id int not null,
	paralegal_id int not null,
	task varchar(30),
	task_deadline date,
	foreign key(atourney_id)
		references Atourney(id)
		on delete cascade,
	foreign key(paralegal_id)
		references Paralegal(id)
		on delete cascade,
	primary key(id)
);

create table Lawsuit(
	id int not null,
	charge varchar(30) not null,
	primary key(id)
);

create table Atourney_on_lawsuit(
	atourney_id int not null,
	lawsuit_id int not null,
	foreign key(atourney_id)
		references Atourney(id)
		on delete cascade,
	foreign key(lawsuit_id)
		references Lawsuit(id)
		on delete cascade
);

create table Document(
	id int not null,
	class varchar(30) not null,
	emission_date date not null,
	primary key(id)
);

create table Document_on_lawsuit(
	document_id int not null,
	lawsuit_id int not null,
	paralegal_id int not null,
	admission_status varchar(30) not null,
	foreign key(document_id)
		references Document(id)
		on delete cascade,
	foreign key(lawsuit_id)
		references Lawsuit(id)
		on delete cascade,
	foreign key(paralegal_id)
		references Paralegal(id)
		on delete cascade
);

create table Invoice(
	id int not null,
	total float not null,
	services_description varchar(100) not null,
	emission date not null,
	primary key(id)
);

create table Client(
	id int not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	date_of_birth date not null,
	invoice_id int ,
	city varchar(30) not null,
	primary key(id),
	foreign key(invoice_id)
		references Invoice(id)
		on delete cascade

);

alter table Atourney_helper
add task_deadline date;

create table Lawsuit_of_client(
	lawsuit_id int not null,
	client_id int not null,
	client_rating int check(client_rating <= 10 and client_rating >0),
	court_date date,
	progress varchar(30),
	foreign key(lawsuit_id)
		references Lawsuit(id)
		on delete cascade,
	foreign key(client_id)
		references Client(id)
		on delete cascade
);


create table Atourney_for_invoice(
	atourney_id int not null,
	invoice_id int not null,
	foreign key(atourney_id)
		references Atourney(id)
		on delete cascade,
	foreign key(invoice_id)
		references Invoice(id)
		on delete cascade
);

