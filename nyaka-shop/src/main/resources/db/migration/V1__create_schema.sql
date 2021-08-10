CREATE SEQUENCE public.hibernate_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE roles (
                       id biginteger PRIMARY KEY,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp,
                       role_name varchar(255) NULL,
                       CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE users (
                       id biginteger PRIMARY KEY,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp,
                       email varchar(50) not null unique,
                       father_name varchar(30) NULL,
                       first_name varchar(30) NOT NULL,
                       last_name varchar(30) NOT NULL,
                       "password" varchar(30) not null,
);

CREATE TABLE users_roles (
                             user_id bigint not null references users (id),
                             role_id bigint not null references roles (id),
                             primary key (user_id, role_id)
);

CREATE TABLE categories (
                            id biginteger PRIMARY KEY,
                            created_at timestamp default current_timestamp,
                            updated_at timestamp default current_timestamp,
                            title varchar(255) NULL,
                            CONSTRAINT categories_pkey PRIMARY KEY (id)
);


-- public.roles definition

-- Drop table

-- DROP TABLE roles;




-- public.orders definition

-- Drop table

-- DROP TABLE orders;

CREATE TABLE orders (
                        id biginteger PRIMARY KEY,
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp,
                        total_price numeric(19,2) NULL,
                        user_id int8 NULL,
                        CONSTRAINT orders_pkey PRIMARY KEY (id),
                        CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES users(id)
);


-- public.users_roles definition

-- Drop table

-- DROP TABLE users_roles;




-- public.order_items definition

-- Drop table

-- DROP TABLE order_items;

CREATE TABLE order_items (
                             id biginteger PRIMARY KEY,
                             quantity int4 NOT NULL,
                             order_id int8 NULL,
                             price_id int8 NOT NULL,
                             product_id int8 NOT NULL,
                             CONSTRAINT order_items_pkey PRIMARY KEY (id)
);


-- public.prices definition

-- Drop table

-- DROP TABLE prices;

CREATE TABLE prices (
                        id biginteger PRIMARY KEY,
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp,
                        "cost" numeric(19,2) NULL,
                        product_id int8 NULL,
                        CONSTRAINT prices_pkey PRIMARY KEY (id)
);


-- public.products definition

-- Drop table

-- DROP TABLE products;

CREATE TABLE products (
                          id biginteger PRIMARY KEY,
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp,
                          description varchar(255) NULL,
                          title varchar(255) NOT NULL,
                          category_id int8 NULL,
                          price_id int8 NULL,
                          CONSTRAINT products_pkey PRIMARY KEY (id)
);


-- public.order_items foreign keys

ALTER TABLE public.order_items ADD CONSTRAINT fkbioxgbv59vetrxe0ejfubep1w FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE public.order_items ADD CONSTRAINT fkdh4pn831l8gv61jb5sq6i9x97 FOREIGN KEY (price_id) REFERENCES prices(id);
ALTER TABLE public.order_items ADD CONSTRAINT fkocimc7dtr037rh4ls4l95nlfi FOREIGN KEY (product_id) REFERENCES products(id);


-- public.prices foreign keys

ALTER TABLE public.prices ADD CONSTRAINT fkhpva2t51a39twh6gdkxdcllyf FOREIGN KEY (product_id) REFERENCES products(id);


-- public.products foreign keys

ALTER TABLE public.products ADD CONSTRAINT fkcsbguv9yaiyvrhbtl1npphyd FOREIGN KEY (price_id) REFERENCES prices(id);
ALTER TABLE public.products ADD CONSTRAINT fkog2rp4qthbtt2lfyhfo32lsw9 FOREIGN KEY (category_id) REFERENCES categories(id);