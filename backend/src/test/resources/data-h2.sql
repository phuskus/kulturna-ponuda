insert into authorities (name) values ('ROLE_USER');
insert into authorities (name) values ('ROLE_ADMIN');

/* password is name (test1, test2, admin1, admin2) */
insert into users (name, username, password, enabled, role, last_password_reset_date) values ('test1', 'test1@gmail.com', '$2a$10$5JOhdCnEYE5pMO0mXMjRGeYP35rE5nF3wA2q5qnGSEhnowJ7AWUFe', true, 'USER', '2020-12-07 16:00:00.508-07');
insert into users (name, username, password, enabled, role, key, last_password_reset_date) values ('test2', 'test2@gmail.com', '$2a$10$3/P/qXmm16QDY0mDFXjRKem0Weq1GESCi8sVWHKhC6J2h.qLiWcWW', false, 'USER', 'test-key', '2020-12-19 13:05:59.222');
insert into users (name, username, password, enabled, role, last_password_reset_date) values ('admin1', 'admin1@gmail.com', '$2a$10$RbxZeg4k0qtP6CY9OKeIPujXP2woY34EikC36tbg.DVhbgwbCREii', true, 'ADMIN', '2020-12-19 13:05:59.222');
insert into users (name, username, password, enabled, role) values ('admin2', 'admin2@gmail.com', '$2a$10$aWgpmYlLE/ml9MECgeggtuKgjUUe6iyYH0qa.jspVPQAJbjp.Vcji', true, 'ADMIN');

insert into user_authority (user_id, authority_id) values (1, 1);
insert into user_authority (user_id, authority_id) values (2, 1);
insert into user_authority (user_id, authority_id) values (3, 2);
insert into user_authority (user_id, authority_id) values (4, 2);

insert into categories (name) values ('Manifestacija');

insert into subcategories (name, category_id) values ('Festival', 1);

insert into subcategories (name, category_id) values ('Okupljanje', 1);

insert into cultural_offers (address, city, description, latitude, longitude, name, region, admin_id, category_id) 
			values ('Petrovaradinska Tvrdjava', 'Novi Sad', 'Music festival', 45.2526, 19.8623, 'cultural_offer1', 'Vojvodina', 3, 1);
insert into cultural_offers (address, city, description, latitude, longitude, name, region, admin_id, category_id) 
			values ('Kalemegdanska tvrdjava', 'Beograd', 'Music festival', 45.2526, 19.8623, 'cultural_offer2', 'Vojvodina', 3, 1);

insert into pictures (path, placeholder) values ('\pictures\exit.jpg', 'picture placeholder');
insert into pictures (path, placeholder) values ('\pictures\exit1.jpg', 'picture1 placeholder');

/* insert into cultural_offers_pictures (cultural_offer_id, pictures_id) values (1, 1); */

insert into posts (content, cultural_offer_id) values ('test1_offer1', 1);
insert into posts (content, cultural_offer_id) values ('test2_offer1', 1);
insert into posts (content, cultural_offer_id) values ('test3_offer2', 2);

insert into post_pictures (post_id, pictures_id) values (1, 1);
insert into post_pictures (post_id, pictures_id) values (2, 1);
insert into post_pictures (post_id, pictures_id) values (3, 1);


