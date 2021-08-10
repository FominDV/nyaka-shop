create sequence public.hibernate_sequence
    increment by 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1
    no cycle;

create table roles
(
    id         bigint primary key,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    role_name  varchar(30) not null
);

create table users
(
    id          bigint primary key,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    email       varchar(50) not null unique,
    father_name varchar(30) null,
    first_name  varchar(30) not null,
    last_name   varchar(30) not null,
    "password"  varchar(80) not null
);

create table users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

create table categories
(
    id          bigint primary key,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    name        varchar(50)  not null,
    description varchar(255) null
);

create table orders
(
    id          bigint primary key,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    total_price numeric(8, 2) not null,
    user_id     bigint        not null references users (id)
);

create table products
(
    id          bigint primary key,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    description varchar(255) null,
    title       varchar(50)  not null,
    category_id bigint       not null references categories (id),
    price_id    bigint       not null
);

create table prices
(
    id         bigint primary key,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    "cost"     numeric(19, 2) NULL,
    product_id bigint         not null references products (id)
);

create table order_items
(
    id         bigint primary key,
    quantity   int    not null,
    order_id   bigint not null references orders (id),
    price_id   bigint not null references prices (id),
    product_id bigint not null references products (id)
);

alter table products
    add constraint product_price_ref foreign key (price_id) references prices (id);
