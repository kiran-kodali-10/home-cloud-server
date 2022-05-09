DROP TABLE IF EXISTS USERS CASCADE ;
CREATE TABLE users
(
    username varchar(50) not null primary key ,
    password varchar(100) not null ,
    active boolean,
    role varchar(50)
);
