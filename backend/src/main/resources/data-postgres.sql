/*
    sifre su oblika "sifra+name"
 */
insert into users (name, username, password, enabled, role) values ('admin1', 'admin1@gmail.com', '$2a$10$1vu6VWjP9ewmLkGzcSJ9T.dUJD1K4GBAYhiE5lHI3vFucbbffKtqS', true, 'ADMIN');
insert into users (name, username, password, enabled, role) values ('user1', 'user1@gmail.com', '$2a$10$A4XnaPtO.2ljs1Vd5iA3EuNBi5HRBWBky2pziALH0/0rQO9A8li7y', true, 'USER');
insert into users (name, username, password, enabled, role) values ('user2', 'user2@gmail.com', '$2a$10$L4Aj951dZhgOUOmAVEf7q.C/ZB498dUOKCnWQvlsnQXtmewdAagaK', true, 'USER');

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

insert into reviews (rating, content, cultural_offer_id, user_id) values (5, 'Otkazano zbog korone', 1, 2);
insert into reviews (rating, content, cultural_offer_id, user_id) values (0, 'Ne valja nista', 1, 2);
