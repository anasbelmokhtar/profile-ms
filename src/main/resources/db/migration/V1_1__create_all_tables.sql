use sql5485812;

create table sql5485812.Users(
     UserId int not null primary key auto_increment,
     Username varchar(55) not null,
     FirstName varchar(45) not null,
     LastName varchar(55) not null,
     email varchar(85) not null,
     phone varchar(12) not null,
     password varchar(45) not null,
     Token varchar(55),
     UserStatus varchar(20) default 'pending'
);
