insert into users
    (email, father_name, first_name, last_name, password)
values ('user@yandex.ru', 'Victorovich', 'Drannikov', 'Alex',
        '$2y$10$SYnWP1tqq.AMVq4LzTTw.uircwJQMaqashI9CmKFdRcTxOz/9/xNW'),
       ('moderator@yandex.ru', 'Ivanov', 'Saavin', 'Axel',
        '$2y$12$aPojI4JWfgP3fGH9dsLFWe4eZsnkyY4dbDSB7tveqqhDGT7qnz8Jm');

insert into roles
    (role_name)
values ('ROLE_USER'),
       ('ROLE_MODERATOR');

insert into users_roles
    (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2);

insert into categories
    (title, description)
values ('food', 'It should be eaten'),
       ('toy', 'All for funny play');

insert into prices
    (cost)
values (100),
       (99.5),
       (300.77),
       (600.80),
       (123),
       (500),
       (523),
       (12.38),
       (66),
       (999.99);

insert into products
    (description, title, category_id, price_id)
values ('So tasty', 'ice-cream', 1, 1),
       ('Can fly', 'air-bord', 2, 2),
       ('', 'card', 2, 3),
       ('', 'wood-ness', 2, 4),
       ('', 'cheese', 1, 5),
       ('', 'meet', 1, 6),
       ('', 'potato', 1, 7),
       ('', 'opinion', 1, 8),
       ('', 'cherry', 1, 9),
       ('', 'energy drink', 1, 10);

update prices
set product_id = 1
where id = 1;

update prices
set product_id=2
where id = 2;

update prices
set product_id=3
where id = 3;

update prices
set product_id=4
where id = 4;

update prices
set product_id=5
where id = 5;

update prices
set product_id=6
where id = 6;

update prices
set product_id=7
where id = 7;

update prices
set product_id=8
where id = 8;

update prices
set product_id=9
where id = 9;

update prices
set product_id=10
where id = 10;
