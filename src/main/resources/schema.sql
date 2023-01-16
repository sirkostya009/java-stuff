drop table if exists genre cascade;
drop table if exists book cascade;

create table genre (
    id bigserial primary key,
    name varchar(255) not null
);

create table book (
    id bigserial primary key,
    author varchar(255) not null,
    title varchar(255) not null,
    genre_id bigint,
    constraint FK_genre_id foreign key (genre_id) references genre
);
