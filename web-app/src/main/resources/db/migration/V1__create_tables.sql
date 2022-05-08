create table users (
    id       bigserial not null constraint users_pk primary key,
    email    varchar(255) not null unique,
    firstname varchar(50)  not null,
    lastname varchar(50)  not null,
    password varchar(255) not null,
    role     varchar(20)  not null,
    status   varchar(20)  not null
);

insert  into users values (
    1, 'admin@mail.com', 'Ivan', 'Ivanov', '$2a$12$BMCe7.ytHFwyLxkOIYAmXOmit3GqLTZ90kDH5VAgId30Y/a6KMVNq', 'ADMIN','ACTIVE'
);

insert  into users values (
    2, 'user@mail.com', 'Petr', 'Petrov', '$2a$12$rXb.1cTvjQOSMDQE3GNU3eaWPvr1X3RBNHmJc4kw5UWb3gvj0BD6G', 'USER','ACTIVE'
);







