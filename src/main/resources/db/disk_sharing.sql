-- DTD
create schema if not exists `disk_sharing` default character set utf8 collate utf8_unicode_ci;

use disk_sharing;

create table reference(
	id bigint auto_increment primary key,
	name varchar(255) not null,
	constraint UK_q8l59p9sov4rf76smc2wl228m unique (name)
);

create table reference_values(
	id bigint auto_increment primary key,
	ref_id bigint not null,
  value varchar(255) not null,
	constraint FKj0f38wxsme6pt95jqwtjtoyvj foreign key (ref_id) references reference (id),
  constraint UK_1fxrmv40f5uqy2x9xfqij58xd unique (value)
);
create index FKj0f38wxsme6pt95jqwtjtoyvj on reference_values (ref_id);

create table users(
	id bigint auto_increment primary key,
	username varchar(255) not null,
  password varchar(255) not null,
  first_name varchar(255) not null,
	last_name varchar(255) not null,
	constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
);

create table disks(
	id bigint auto_increment primary key,
	owner_id bigint not null,
  holder_id bigint null,
  description varchar(255) null,
  constraint FKe8x6ji07a0tfugvbr4wc7extw foreign key (owner_id) references users (id),
	constraint FKexwp27b7xxx65duv6eocoo8ku foreign key (holder_id) references users (id)
);
create index FKe8x6ji07a0tfugvbr4wc7extw on disks (owner_id);
create index FKexwp27b7xxx65duv6eocoo8ku on disks (holder_id);

create table taken_items(
	id bigint auto_increment primary key,
	disk_id bigint not null,
  from_id bigint not null,
  to_id bigint not null,
  op_type_id bigint not null,
  op_date datetime(6) not null,
	constraint FKi28la9crm472iobm6u61lrqx9 foreign key (disk_id) references disks (id),
	constraint FK10q234b2vt81e21vqyxt7s0tu foreign key (from_id) references users (id),
	constraint FK95ld229ykc66ofiud14g8hu3e foreign key (to_id) references users (id),
	constraint FKm3k19qbofyvgfe0te5wvvs0em foreign key (op_type_id) references reference_values (id)
);
create index FKi28la9crm472iobm6u61lrqx9 on taken_items (disk_id);
create index FK10q234b2vt81e21vqyxt7s0tu on taken_items (from_id);
create index FK95ld229ykc66ofiud14g8hu3e on taken_items (to_id);
create index FKm3k19qbofyvgfe0te5wvvs0em on taken_items (op_type_id);

-- DDL
INSERT INTO disk_sharing.reference (id, name) VALUES (1, 'Операции обмена');

INSERT INTO disk_sharing.reference_values (id, ref_id, value) VALUES (1, 1, 'Взять');
INSERT INTO disk_sharing.reference_values (id, ref_id, value) VALUES (2, 1, 'Вернуть');
INSERT INTO disk_sharing.reference_values (id, ref_id, value) VALUES (3, 1, 'Вернуть по требованию владельца');

INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (1, 'mike.t@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Mike', 'Teodor');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (2, 'barry.r@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Barry', 'Ray');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (3, 'steven.f@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Steve', 'Fox');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (4, 'max.d@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Max', 'Don');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (5, 'alex.c@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Alex', 'Celvin');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (6, 'nick.p@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Nick', 'Peter');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (7, 'tom.w@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Tom', 'Wendey');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (8, 'luise.a@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Luise', 'Andy');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (9, 'kate.y@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Kate', 'Yohan');
INSERT INTO disk_sharing.users (id, username, password, first_name, last_name) VALUES (10, 'peter.r@gmail.com', '$2b$10$O3J5YNtFbJbzUXhdb00FkeDa2pGUKRV8Q7KZDEgWc4H8CtEhw2VdG', 'Piter', 'Rich');

INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (1, 3, 6, 'blah-blah1');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (2, 8, null, 'blah-blah2');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (3, 10, 9, 'blah-blah3');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (4, 1, 5, 'blah-blah4');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (5, 4, 6, 'blah-blah5');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (6, 2, null, 'blah-blah6');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (7, 5, null, 'blah-blah7');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (8, 7, null, 'blah-blah8');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (9, 3, 5, 'blah-blah9');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (10, 6, null, 'blah-blah10');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (11, 9, 5, 'blah-blah11');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (12, 8, null, 'blah-blah12');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (13, 10, 5, 'blah-blah13');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (14, 3, null, 'blah-blah14');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (15, 1, 10, 'blah-blah15');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (16, 4, null, 'blah-blah16');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (17, 6, null, 'blah-blah17');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (18, 5, null, 'blah-blah18');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (19, 8, 9, 'blah-blah19');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (20, 5, 10, 'blah-blah20');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (21, 3, null, 'blah-blah21');
INSERT INTO disk_sharing.disks (id, owner_id, holder_id, description) VALUES (22, 9, null, 'blah-blah22');

INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (1, 1, 3, 6, 1, '2020-07-19 15:18:10.097000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (2, 1, 6, 3, 2, '2020-07-19 15:18:17.051000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (3, 1, 3, 6, 1, '2020-07-19 15:18:23.162000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (4, 5, 4, 6, 1, '2020-07-19 15:19:47.038000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (5, 9, 3, 6, 1, '2020-07-19 15:19:49.924000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (6, 10, 6, 2, 1, '2020-07-19 15:21:20.498000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (7, 18, 5, 2, 1, '2020-07-19 15:21:56.423000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (8, 19, 8, 2, 1, '2020-07-19 15:22:03.126000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (9, 20, 5, 2, 1, '2020-07-19 15:22:15.939000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (10, 19, 2, 8, 2, '2020-07-19 15:22:30.191000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (11, 10, 2, 6, 3, '2020-07-19 15:24:09.331000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (12, 9, 6, 3, 2, '2020-07-19 15:26:50.242000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (13, 7, 5, 8, 1, '2020-07-19 15:28:52.078000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (14, 7, 8, 5, 2, '2020-07-19 15:29:17.952000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (15, 7, 5, 8, 1, '2020-07-19 15:29:21.699000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (16, 7, 8, 5, 2, '2020-07-19 15:29:27.760000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (17, 20, 2, 5, 3, '2020-07-19 15:32:10.861000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (18, 2, 8, 5, 1, '2020-07-19 15:34:00.234000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (19, 11, 9, 5, 1, '2020-07-19 15:34:04.176000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (20, 2, 5, 8, 2, '2020-07-19 15:34:13.108000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (21, 3, 10, 9, 1, '2020-07-19 15:38:47.416000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (22, 7, 5, 9, 1, '2020-07-19 15:38:52.497000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (23, 19, 8, 9, 1, '2020-07-19 15:38:55.343000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (24, 7, 9, 5, 2, '2020-07-19 15:44:29.530000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (25, 18, 2, 5, 3, '2020-07-19 15:45:21.958000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (26, 17, 6, 5, 1, '2020-07-19 15:46:01.588000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (27, 9, 3, 5, 1, '2020-07-19 15:46:05.185000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (28, 4, 1, 5, 1, '2020-07-19 15:46:07.893000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (29, 17, 5, 6, 2, '2020-07-19 15:46:23.338000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (30, 13, 10, 5, 1, '2020-07-19 15:46:35.402000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (31, 20, 5, 10, 1, '2020-07-19 15:50:08.654000');
INSERT INTO disk_sharing.taken_items (id, disk_id, from_id, to_id, op_type_id, op_date) VALUES (32, 15, 1, 10, 1, '2020-07-19 15:50:19.070000');
