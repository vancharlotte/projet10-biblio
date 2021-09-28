CREATE DATABASE IF NOT EXISTS dbauth;

CREATE USER 'admin'@'dbauth' IDENTIFIED BY 'admin123!';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'dbauth';
FLUSH PRIVILEGES ;

USE dbauth;

create table if not exists oauth_client_details (
  client_id varchar(255) not null,
  client_secret varchar(255) not null,
  web_server_redirect_uri varchar(2048) default null,
  scope varchar(255) default null,
  access_token_validity int(11) default null,
  refresh_token_validity int(11) default null,
  resource_ids varchar(1024) default null,
  authorized_grant_types varchar(1024) default null,
  authorities varchar(1024) default null,
  additional_information varchar(4096) default null,
  autoapprove varchar(255) default null,
  primary key (client_id)
) engine=innodb ;

create table if not exists account (
  id int(11) not null auto_increment,
  username varchar(100) not null,
  password varchar(1024) not null,
  email varchar(1024) not null,
  firstName varchar(100) not null,
  lastName varchar(100) not null,
  phoneNumber varchar(10) not null,
  enabled tinyint(4) not null,
  account_Non_Expired tinyint(4) not null,
  credentials_Non_Expired tinyint(4) not null,
  account_Non_Locked tinyint(4) not null,
  roles varchar(1024) not null,
  primary key (id),
  unique key username (username)
) engine=innodb ;


-- token store
create table if not exists oauth_client_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table if not exists oauth_access_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(256)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

create table if not exists oauth_code (
  code VARCHAR(256), authentication LONG VARBINARY
);

create table if not exists oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);

