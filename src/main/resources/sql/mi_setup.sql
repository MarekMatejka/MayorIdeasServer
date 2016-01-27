create table Category (
	ID int not null auto_increment,
	Name varchar(50) not null,
	primary key (ID)
);

create table User (
	ID int not null auto_increment,
	Name varchar(50) not null,
	Is_Citizen boolean not null,
	primary key (ID)
);

create table Idea (
	ID int not null auto_increment,
	Title varchar(200) not null,
	CategoryID int not null,
	Description varchar(4000) not null,
	UserID int not null,
	DateCreated timestamp not null,
	primary key (ID),
	foreign key (CategoryID) references Category(ID),
	foreign key (UserID) references User(ID)
);

create table Picture (
	ID int not null auto_increment,
	Picture mediumblob not null,
	IdeaID int not null,
	UserID int not null,
	primary key (ID),
	foreign key (IdeaID) references Idea(ID),
	foreign key (UserID) references User(ID)
);

create table Comment (
	ID int not null auto_increment,
	UserID int not null,
	IdeaID int not null,
	Text varchar(4000) not null,
	DateCreated timestamp not null,
	primary key (ID),
	foreign key (UserID) references User(ID),
	foreign key (IdeaID) references Idea(ID)
);

create table Follows (
	UserID int not null,
	IdeaID int not null,
	primary key (UserID, IdeaID),
	foreign key (UserID) references User(ID),
	foreign key (IdeaID) references Idea(ID)
);

create table Vote (
	UserID int not null,
	IdeaID int not null,
	Voted int not null,
	DateVoted timestamp not null,
	primary key (UserID, IdeaID),
	foreign key (UserID) references User(ID),
	foreign key (IdeaID) references Idea(ID)
);

insert into Category values
	(default, 'Environment'),
	(default, 'Culture'),
	(default, 'Transport'),
	(default, 'Infrastructure');