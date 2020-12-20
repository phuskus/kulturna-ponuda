insert into authorities (name) values ('ROLE_USER');
insert into authorities (name) values ('ROLE_ADMIN');

insert into users (name, username, password, enabled, role, last_password_reset_date) values ('test1', 'test1@gmail.com', '$2a$10$A4XnaPtO.2ljs1Vd5iA3EuNBi5HRBWBky2pziALH0/0rQO9A8li7y', true, 'USER', '2020-12-07 16:00:00.508-07');
insert into users (name, username, password, enabled, role, key, last_password_reset_date) values ('test2', 'test2@gmail.com', '$2a$10$A4XnaPtO.2ljs1Vd5iA3EuNBi5HRBWBky2pziALH0/0rQO9A8li7y', false, 'USER', 'test-key', '2020-12-19 13:05:59.222');
insert into users (name, username, password, enabled, role, last_password_reset_date) values ('admin', 'admin@gmail.com', '$2a$10$A4XnaPtO.2ljs1Vd5iA3EuNBi5HRBWBky2pziALH0/0rQO9A8li7y', true, 'ADMIN', '2020-12-19 13:05:59.222');

insert into categories (name) values ('Manifestacija');

insert into subcategories (name, category_id) values ('Festival', 1);

insert into cultural_offers (address, city, description, latitude, longitude, name, region, admin_id, category_id) 
			values ('Petrovaradinska Tvrdjava', 'Novi Sad', 'Music festival', 45.2526, 19.8623, 'cultural_offer1', 'Vojvodina', 3, 1);
insert into cultural_offers (address, city, description, latitude, longitude, name, region, admin_id, category_id) 
			values ('Kalemegdanska tvrdjava', 'Beograd', 'Music festival', 45.2526, 19.8623, 'cultural_offer2', 'Vojvodina', 3, 1);

insert into pictures (path, placeholder) values ('\pictures\exit.jpg', 'Exit festival picture placeholder');