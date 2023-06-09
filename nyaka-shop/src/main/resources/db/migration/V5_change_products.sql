alter table products add column is_deleted bool not null default false;
alter table order_items add column "cost" numeric(19,2) null default false;