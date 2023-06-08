CREATE TABLE brands (
                        id bigserial NOT NULL,
                        title varchar(255) NOT NULL,
                        CONSTRAINT brands_pkey PRIMARY KEY (id)
);

CREATE TABLE categories (
                            id bigserial NOT NULL,
                            title varchar(255) NOT NULL,
                            CONSTRAINT categories_pkey PRIMARY KEY (id)
);

CREATE TABLE countries (
                           id bigserial NOT NULL,
                           title varchar(255) NOT NULL,
                           CONSTRAINT countries_pkey PRIMARY KEY (id)
);

CREATE TABLE roles (
                       id bigserial NOT NULL,
                       role_name varchar(255) NOT NULL,
                       CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE users (
                       id bigserial NOT NULL,
                       created_at timestamp NULL,
                       email varchar(255) NOT NULL,
                       father_name varchar(255) NOT NULL,
                       first_name varchar(255) NOT NULL,
                       last_name varchar(255) NOT NULL,
                       "password" varchar(255) NOT NULL,
                       "login" varchar(50) NOT NULL,
                       CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
                       CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE users_roles (
                             user_id int8 NOT NULL,
                             role_id int8 NOT NULL
);


-- public.users_roles foreign keys

ALTER TABLE public.users_roles ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE public.users_roles ADD CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES roles(id);

CREATE TABLE products (
                          id bigserial NOT NULL,
                          created_at timestamp NULL,
                          description varchar(255) NULL,
                          image_id uuid NULL,
                          title varchar(255) NOT NULL,
                          updated_at timestamp NULL,
                          brand_id int8 NULL,
                          country_id int8 NULL,
                          CONSTRAINT products_pkey PRIMARY KEY (id)
);


-- public.products foreign keys

ALTER TABLE public.products ADD CONSTRAINT fka3a4mpsfdf4d2y6r8ra3sc8mv FOREIGN KEY (brand_id) REFERENCES brands(id);
ALTER TABLE public.products ADD CONSTRAINT fknlfsbb5ww3qpf1nts56i232lx FOREIGN KEY (country_id) REFERENCES countries(id);

CREATE TABLE categories_products (
                                     product_id int8 NOT NULL,
                                     category_id int8 NOT NULL
);


-- public.categories_products foreign keys

ALTER TABLE public.categories_products ADD CONSTRAINT fk2a3u5mbtmtq3d4s5abajhhksf FOREIGN KEY (category_id) REFERENCES categories(id);
ALTER TABLE public.categories_products ADD CONSTRAINT fk2tnk948b1lgpg3uggwyi2kwfq FOREIGN KEY (product_id) REFERENCES products(id);

CREATE TABLE prices (
                        id bigserial NOT NULL,
                        "cost" numeric(19,2) NULL,
                        created_at timestamp NULL,
                        product_id int8 NULL,
                        CONSTRAINT prices_pkey PRIMARY KEY (id)
);


-- public.prices foreign keys

ALTER TABLE public.prices ADD CONSTRAINT fkhpva2t51a39twh6gdkxdcllyf FOREIGN KEY (product_id) REFERENCES products(id);

CREATE TABLE shipments (
                           id bigserial NOT NULL,
                           created_at timestamp NULL,
                           quantity int4 NULL,
                           product_id int8 NULL,
                           CONSTRAINT shipments_pkey PRIMARY KEY (id)
);


-- public.shipments foreign keys

ALTER TABLE public.shipments ADD CONSTRAINT fkigjanuwjnryskujohncq7a394 FOREIGN KEY (product_id) REFERENCES products(id);

CREATE TABLE orders (
                        id bigserial NOT NULL,
                        address varchar(255) NOT NULL,
                        created_at timestamp NULL,
                        phone varchar(255) NOT NULL,
                        status varchar(255) NULL,
                        total_price numeric(19,2) NOT NULL,
                        updated_at timestamp NULL,
                        user_id int8 NOT NULL,
                        CONSTRAINT orders_pkey PRIMARY KEY (id)
);


-- public.orders foreign keys

ALTER TABLE public.orders ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES users(id);

CREATE TABLE order_items (
                             id bigserial NOT NULL,
                             quantity int4 NOT NULL,
                             order_id int8 NOT NULL,
                             product_id int8 NOT NULL,
                             CONSTRAINT order_items_pkey PRIMARY KEY (id)
);


-- public.order_items foreign keys

ALTER TABLE public.order_items ADD CONSTRAINT fkbioxgbv59vetrxe0ejfubep1w FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE public.order_items ADD CONSTRAINT fkocimc7dtr037rh4ls4l95nlfi FOREIGN KEY (product_id) REFERENCES products(id);

CREATE TABLE feedbacks (
                           id bigserial NOT NULL,
                           created_at timestamp NULL,
                           "text" varchar(255) NULL,
                           order_item_id int8 NULL,
                           CONSTRAINT feedbacks_pkey PRIMARY KEY (id)
);


-- public.feedbacks foreign keys

ALTER TABLE public.feedbacks ADD CONSTRAINT fkp4mcqc539v311h1woe3bidtit FOREIGN KEY (order_item_id) REFERENCES order_items(id);

insert into users
    (created_at, email, father_name, first_name, last_name, password, login)
values (CURRENT_TIMESTAMP, 'necronaft@yandex.ru', 'Victorovich', 'Drannikov', 'Alex',
        '$2y$10$SYnWP1tqq.AMVq4LzTTw.uircwJQMaqashI9CmKFdRcTxOz/9/xNW', 'necronaft'),
       (CURRENT_TIMESTAMP,'moderator@yandex.ru', 'Ivanov', 'Saavin', 'Axel',
        '$2a$12$iCIlskDLpjpG3yazBmYSruDjD5pjTu454.EqtZHmzRd1m8fu0iFGK', 'moderator777');

insert into roles
    (role_name)
values ('ROLE_USER'),
       ('ROLE_MODERATOR');

insert into users_roles
    (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2);
