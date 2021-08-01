CREATE DATABASE IF NOT EXISTS dbbooking;

CREATE USER 'admin'@'dbbooking' IDENTIFIED BY 'admin123!';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'dbbooking';
FLUSH PRIVILEGES ;

USE dbbooking;

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists booking
(
    id         int         not null
    primary key,
    user       int         not null,
    book       int         not null,
    start_date   datetime(6) not null,
    notif_date datetime(6) not null
    );

