create table genres
(
    id             bigserial primary key,
    rus_name       varchar(255),
    eng_name       varchar(255),
    eng_short_name varchar(255)
);

create type cover_type as enum ('HARD','SOFT');

alter table books
    drop column genre,
    add column genre_id bigint references genres (id),
    add column cover cover_type,
    add column book_page int;

alter table authors
    add column short_name varchar(255);
