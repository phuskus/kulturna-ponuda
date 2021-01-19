insert into authorities (name) values ('ROLE_USER');
insert into authorities (name) values ('ROLE_ADMIN');

-- password == name
insert into users (name, surname, username, password, enabled, role, last_password_reset_date) values ('admin1', 'admin1', 'admin1@gmail.com', '$2y$12$LdA5w1xM5qBwt1l.Srv62etWXUpyfCbU/usi3EprOEdZ3ZbCiDx/K', true, 'ADMIN', '2020-12-07 16:00:00.508-07');
insert into users (name, surname, username, password, enabled, role, last_password_reset_date) values ('admin2', 'admin2', 'admin2@gmail.com', '$2y$12$fcgxX0RiMeL1jbEI/wKt/uD3b1nCrCY22O6Y5ecR28btz4miOnI5q', true, 'ADMIN', '2020-12-07 16:00:00.508-07');
insert into users (name, surname, username, password, enabled, role, last_password_reset_date) values ('user1', 'user1', 'user1@gmail.com', '$2y$12$VgRjaYjlVozA1Kmnrzmc8.tH0GP8fdsPEzoXYC9rz.JBvNzqZnEU6', true, 'USER', '2020-12-07 16:00:00.508-07');
insert into users (name, surname, username, password, enabled, role, last_password_reset_date) values ('user2', 'user2', 'user2@gmail.com', '$2y$12$Su12Y/TWAR4WvcwRaNB/0eslOtipMsJ88Xt.U7nGADdC6vNXubZXS', true, 'USER', '2020-12-07 16:00:00.508-07');
insert into users (name, surname, username, password, enabled, role, last_password_reset_date) values ('user3', 'user3', 'user3@gmail.com', '$2y$12$6qk4EUIYkjj40YZ6DOiZYeHj4va9Vj5z/2s23vXPuhYnZwX65lpwi', false, 'USER', '2020-12-07 16:00:00.508-07');


insert into user_authority (user_id, authority_id) values (1, 2);
insert into user_authority (user_id, authority_id) values (2, 2);
insert into user_authority (user_id, authority_id) values (3, 1);
insert into user_authority (user_id, authority_id) values (4, 1);

insert into categories (name) values ('Institucija');
insert into categories (name) values ('Manifestacija');
insert into categories (name) values ('Kulturno dobro');

insert into subcategories (name, category_id) values ('Muzej', 1);
insert into subcategories (name, category_id) values ('Galerija', 1);
insert into subcategories (name, category_id) values ('Festival', 2);
insert into subcategories (name, category_id) values ('Sajam', 2);
insert into subcategories (name, category_id) values ('Spomenik', 3);
insert into subcategories (name, category_id) values ('Znamenitost', 3);

insert into cultural_offers (address, city, description, latitude, longitude, name, region, admin_id, category_id) 
			values ('Petrovaradinska Tvrdjava', 'Novi Sad', 'Music festival', 45.2526, 19.8623, 'EXIT festival', 'Vojvodina', 1, 3);

insert into posts (content, cultural_offer_id) values ('Jako vrlo veoma kul', 1);
insert into posts (content, cultural_offer_id) values ('Otkazano zbog korone', 1);

insert into pictures (path, placeholder) values ('\pictures\exit.jpg', 'Exit festival picture placeholder');

insert into cultural_offers_pictures (cultural_offer_id, pictures_id) values (1, 1);

insert into reviews (rating, content, cultural_offer_id, user_id) values (5, 'Otkazano zbog korone', 1, 3);
insert into reviews (rating, content, cultural_offer_id, user_id) values (0, 'Ne valja nista', 1, 3);
