CREATE DATABASE IF NOT EXISTS dbbook;

CREATE USER 'admin'@'dbbook' IDENTIFIED BY 'admin123!';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'dbbook';
FLUSH PRIVILEGES ;

USE dbbook;

create table if not exists book
(
    id int          not null
        primary key,
    author       varchar(255) null,
    editor       varchar(255) null,
    genre        varchar(255) null,
    language     varchar(255) null,
    release_date varchar(255) null,
    summary      varchar(255) null,
    title        varchar(255) null
);

create table if not exists copy
(
    id   int not null
        primary key,
    book int not null
);

create table if not exists hibernate_sequence
(
    next_val bigint null
);
