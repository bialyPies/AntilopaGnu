create database antilopa2;
use antilopa2;

drop table detail;
drop table orders;
drop table detailphotos;

create table detailPhotos(
	detailPhotoId integer primary key,
    photoName varchar(50),
    detailPhoto blob not null,
	photoDate Date,
    photoNote varchar(50)
);

create table orders(
	orderId integer auto_increment primary key,
    orderName varchar(50) not null,
    orderCustomerName varchar(50),
    orderStatus enum("ACCEPTED", "COMPLETED", "PARTIALLY_COMPLETED", "CANCELED") default "ACCEPTED", 
    orderTotalCost decimal(5,2),
    orderDate Date not null,
    orderEstimatedDate Date not null,	
    orderCompletionDate Date,
    orderNumberOfDetails integer
);

create table detail(
	detailId integer auto_increment primary key,
    orderId integer not null,
    detailName varchar(50) not null,
    detailState enum ("USED", "NEW"),
    detailPrice decimal(5,2) not null,
    detailReleaseYear year,
    detailColor varchar(50),
    detailNote varchar(50),
    photos integer,
    photosReceived integer,
    detailStatus enum("NOT_IN_STOCK", "ARRIVED", "ISSUED", "REFUSED", "UNDEFINED"),
    constraint fk_orderId foreign key(orderId) references orders (orderId),
    constraint fk_photos foreign key(photos) references detailPhotos (detailPhotoId),
    constraint fk_photosReceived foreign key(photosReceived) references detailPhotos (detailPhotoId)
);

/*insert values*/
insert into orders(orderName, orderDate, orderEstimatedDate) 
values
("e1", "2020-02-28", "2020-03-08"),
("e2", "2020-02-28", "2020-03-09"),
("e5", "2020-02-28", "2020-03-08"),
("e3", "2020-02-28", "2020-03-03");


insert into detail(orderId, detailName, detailPrice, detailState) 
values 
(2, "req", 5.25, "USED"),
(1, "engine1", 5.12, "NEW"),
(2, "engine2", 6.23, "NEW"),
(1, "engine3", 9.54, "NEW");
