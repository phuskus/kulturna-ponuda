insert into users (name, username, password, enabled, role) values ('Mario Kujundzic', 'mario@gmail.com', 'temppassword', true, 'ADMIN');
insert into users (name, username, password, enabled, role) values ('Pera Peric', 'pera@gmail.com', 'perapera', true, 'USER');


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