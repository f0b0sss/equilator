create table ranges (
                        id BIGINT GENERATED BY DEFAULT AS IDENTITY(START
                            WITH 6) PRIMARY KEY,
                        player_id bigint,
                        name varchar(255)  not null,
                        range text not null
);