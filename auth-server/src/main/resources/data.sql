INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, autoapprove, additional_information)
VALUES
('client', '{noop}secret', 'http://localhost:8000/login', 'READ,WRITE', '3600', '10000', 'library-book,library-loan', 'authorization_code,password,refresh_token,implicit', 'true', '{}'),
('batch', '{noop}secret', 'http://localhost:9005/login', 'READ', '3600', '10000', 'library-loan', 'client_credentials', 'true', '{}');


INSERT INTO Account
(id, username, firstName, lastName, password, email, phoneNumber, roles, enabled, account_Non_Expired, credentials_Non_Expired, account_Non_Locked)
VALUES
(1, 'admin','Jean', 'Lastname', '{noop}123', 'admin@aaa.com', '0111111111', 'ADMIN,USER', '1', '1', '1', '1' ),
(2, 'user', 'Paul', 'Lastname', '{noop}123', 'user@aaa.com', '0222222222', 'USER', '1', '1', '1', '1' ),
(3, 'user2', 'Pierre', 'Lastname', '{noop}123', 'user2@aaa.com', '0333333333', 'USER', '1', '1', '1', '1' ),
(4, 'user3', 'Jacques', 'Lastname', '{noop}123', 'user3@aaa.com', '0333333333', 'USER', '1', '1', '1', '1' ),
(5, 'user4', 'Alphonse', 'Lastname', '{noop}123', 'user4@aaa.com', '0333333333', 'USER', '1', '1', '1', '1' );




