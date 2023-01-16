drop table if exists genre_books cascade;
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

create table genre_books (
    genre_id bigint not null,
    books_id bigint not null,
    primary key (genre_id, books_id),
    constraint UK_books_id unique (books_id),
    constraint FK_books_id foreign key (books_id) references book,
    constraint FK_genre_id foreign key (genre_id) references genre
);
