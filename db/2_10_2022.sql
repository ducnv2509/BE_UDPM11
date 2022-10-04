# new
alter table db_udpm11_v1.product_variant
    add wholesale_price decimal(20, 2) null default (0);

alter table db_udpm11_v1.product_variant
    add sale_price decimal(20, 2) null default (0);

alter table db_udpm11_v1.product_variant
    add import_price decimal(20, 2) null default (0);

create table supplier
(
    id         int primary key auto_increment,
    code       varchar(100)  unique,
    name       text         not null,
    email      varchar(100) not null unique,
    phone      varchar(20)  not null unique,
    address    text         not null,
    create_at  datetime default (now()),
    update_at  datetime,
    is_delete  bit      default (0)
);
create table supplier_seqid
(
    id int auto_increment
        primary key
);



create definer = udpm11@20.189.112.68 trigger db_udpm11_v1.tg_supplier_insert
    before insert
    on db_udpm11_v1.supplier
    for each row
    IF NEW.code is null or NEW.code = '' THEN
begin
INSERT INTO supplier_seqid VALUES (NULL);
SET NEW.code = CONCAT('SUPP', LPAD(LAST_INSERT_ID(), 5, '0'));
end;
end if;

-- 9h 2/10

