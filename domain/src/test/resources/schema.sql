create table users (
                       id       bigserial not null constraint users_pk primary key,
                       email    varchar(255) not null unique,
                       firstname varchar(50)  not null,
                       lastname varchar(50)  not null,
                       password varchar(255) not null,
                       role     varchar(20)  default 'USER',
                       status   varchar(20)  default 'ACTIVE'
);

create table ranges (
                        id       bigserial not null constraint ranges_pk primary key,
                        player_id bigint,
                        name varchar(255)  not null,
                        range text not null


);