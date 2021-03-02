DROP TABLE IF EXISTS customer;
create table customer (
	ID INT PRIMARY KEY,
	NAME VARCHAR(30) NOT NULL,
	AGE INT NOT NULL,
	ACTIVE BOOLEAN
);
insert into customer (ID,ACTIVE,AGE,NAME) values(10001,FALSE,23,'Jack');
insert into customer (ID,ACTIVE,AGE,NAME) values(10002,FALSE,23,'Kathryn');
insert into customer (ID,ACTIVE,AGE,NAME) values(10003,FALSE,27,'Adam');
