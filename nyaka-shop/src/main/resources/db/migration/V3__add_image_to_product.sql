alter table products
    add column image_name varchar(255) null;
update products
set image_name = '1.jpg'
where id = 1;
update products
set image_name = 'cat.jpg'
where id = 2;