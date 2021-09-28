CREATE DATABASE IF NOT EXISTS dbloan;

CREATE USER 'admin'@'dbloan' IDENTIFIED BY 'admin123!';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'dbloan';
FLUSH PRIVILEGES ;

USE dbloan;

create table if not exists loan
(
    id         int         not null
        primary key,
    copy       int         not null,
    end_date   datetime(6) not null,
    renewed    bit         not null,
    returned   bit         not null,
    start_date datetime(6) not null,
    user       int         not null
);
