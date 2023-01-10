create table book (
    id bigint not null,
    author varchar(255) not null,
    title varchar(255) not null,
    genre_id bigint,
    primary key (id),
    constraint FK_genre_id foreign key (genre_id) references genre
);

create table genre (
    id bigint not null,
    name varchar(255) not null,
    primary key (id)
);

create table genre_books (
    genre_id bigint not null,
    books_id bigint not null,
    primary key (genre_id, books_id),
    constraint UK_books_id unique (books_id),
    constraint FK_books_id foreign key (books_id) references book,
    constraint FK_genre_id foreign key (genre_id) references genre
);
