use UserLogin;
show tables;
select * from UserLogin;
drop table UserLogin;
create table UserLogin(U_Id int(10) unsigned NOT NULL AUTO_INCREMENT,UserId varchar(255) DEFAULT NULL,UserPassword varchar(255) DEFAULT NULL,primary key(U_Id),UNIQUE KEY `UserId` (`UserId`));
select * from UserLogin;
INSERT INTO UserLogin(UserId, UserPassword) VALUES ('muniraja', MD5('muni123'));
INSERT INTO UserLogin(UserId, UserPassword) VALUES ('prashant', MD5('prashant123'));