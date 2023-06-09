alter table products add column is_deleted bool not null default false;
alter table products add column barcode varchar(13) not null default '1247802157865';
alter table order_items add column "cost" numeric(19,2) null default false;