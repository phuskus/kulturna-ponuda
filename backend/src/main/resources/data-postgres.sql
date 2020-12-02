insert into users (name, username, password, enabled, role) values ('Mario Kujundzic', 'mario@gmail.com', 'temppassword', true, 'ADMIN');

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