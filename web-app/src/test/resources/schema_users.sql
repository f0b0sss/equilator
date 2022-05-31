create table users (
                       id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 2) PRIMARY KEY,
                       email    varchar(255) not null unique,
                       firstname varchar(50)  not null,
                       lastname varchar(50)  not null,
                       password varchar(255) not null,
                       role     varchar(20)  default 'USER',
                       status   varchar(20)  default 'ACTIVE'
);