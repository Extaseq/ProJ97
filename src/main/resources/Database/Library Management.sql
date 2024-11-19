create table authors
(
    author_id int         not null
        primary key,
    name      varchar(50) null,
    biography text        null
);

create table publishers
(
    publisher_id int auto_increment
        primary key,
    name         varchar(255) null,
    address      varchar(255) null
);

create table books
(
    book_id          int auto_increment
        primary key,
    title            varchar(100) null,
    author_id        int          null,
    publisher_id     int          null,
    genre            varchar(100) null,
    published_year   year         null,
    isbn             varchar(13)  null,
    copies_available int          null,
    constraint books_ibfk_1
        foreign key (author_id) references authors (author_id),
    constraint books_ibfk_2
        foreign key (publisher_id) references publishers (publisher_id)
);

create index author_id
    on books (author_id);

create index publisher_id
    on books (publisher_id);

create table useraccounts
(
    account_id    int auto_increment
        primary key,
    member_id     int                           null,
    username      varchar(100)                  null,
    password_hash varchar(255)                  null,
    salt          varchar(255)                  null,
    created_time  timestamp                     null,
    updated_time  timestamp                     null,
    status        varchar(255) default 'active' null,
    constraint member_id
        unique (member_id),
    constraint username
        unique (username)
);

create table members
(
    member_id int auto_increment
        primary key,
    fullname  varchar(255) null,
    address   varchar(255) null,
    email     varchar(100) null,
    phone     varchar(20)  null,
    join_date date         null,
    constraint members_ibfk_1
        foreign key (member_id) references useraccounts (member_id)
);

create table borrow
(
    borrow_id   int auto_increment
        primary key,
    member_id   int  null,
    book_id     int  null,
    borrow_date date null,
    due_date    date null,
    return_date date null,
    rating      int  null,
    review_text text null,
    constraint borrow_ibfk_1
        foreign key (book_id) references books (book_id),
    constraint borrow_ibfk_2
        foreign key (member_id) references members (member_id)
);

create index book_id
    on borrow (book_id);

create index member_id
    on borrow (member_id);

create table fines
(
    fine_id   int auto_increment
        primary key,
    borrow_id int            null,
    amount    decimal(10, 2) null,
    paid      tinyint(1)     null,
    constraint fines_ibfk_1
        foreign key (borrow_id) references borrow (borrow_id)
);

create index borrow_id
    on fines (borrow_id);

create table readinghistory
(
    history_id   int auto_increment
        primary key,
    member_id    int                                           null,
    book_id      int                                           null,
    reading_date date                                          null,
    status       enum ('Borrowed', 'Completed', 'In Progress') null,
    constraint readinghistory_ibfk_1
        foreign key (book_id) references books (book_id),
    constraint readinghistory_ibfk_2
        foreign key (member_id) references members (member_id)
);

create index book_id
    on readinghistory (book_id);

create index member_id
    on readinghistory (member_id);


