create table Category (
	ID int not null auto_increment,
	Name varchar(50) not null,
	primary key (ID)
);

create table Author (
	ID int not null auto_increment,
	Name varchar(50) not null,
	primary key (ID)
);

create table Idea (
	ID int not null auto_increment,
	Title varchar(200) not null,
	CategoryID int not null,
	Description varchar(4000) not null,
	Location varchar(50) not null,
	AuthorID int not null,
	DateCreated timestamp not null,
	primary key (ID),
	foreign key (CategoryID) references Category(ID),
	foreign key (AuthorID) references Author(ID)
);

create table Picture (
	ID int not null auto_increment,
	Picture blob not null,
	IdeaID int not null,
	AuthorID int not null,
	primary key (ID),
	foreign key (IdeaID) references Idea(ID),
	foreign key (AuthorID) references Author(ID)
);

create table Comment (
	ID int not null auto_increment,
	AuthorID int not null,
	IdeaID int not null,
	Text varchar(4000) not null,
	DateCreated timestamp not null,
	primary key (ID),
	foreign key (AuthorID) references Author(ID),
	foreign key (IdeaID) references Idea(ID)
);