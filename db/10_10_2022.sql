create table imports
(
    id            int auto_increment primary key,
    supplier_id   int                          null,
    account_id    int                          not null,
    note          varchar(250) charset utf8mb3 null,
    code          varchar(50) charset utf8mb3  null,
    total_price   decimal(20, 2)               not null,
    inventory_id  int                          not null,
    is_paid       bit default b'0'             null,
    is_import     bit default b'0'             null,
    is_done       bit default b'0'             null,
    delivery_date varchar(50)                  null,
    is_return     bit                          null,
    constraint code
        unique (code),
    constraint imports_ibfk_2
        foreign key (account_id) references accounts (id),
    constraint imports_ibkf_3
        foreign key (supplier_id) references supplier (id),
    constraint imports_ibkf_5
        foreign key (inventory_id) references inventories (id)
);
create table import_seqid
(
    id int auto_increment
        primary key
);


create index account_id
    on imports (account_id);

create definer = root@localhost trigger tg_import_insert_code
    before insert
    on imports
    for each row
    IF NEW.code is null or NEW.code = '' THEN
        begin
            INSERT INTO import_seqId VALUES (NULL);
            SET NEW.code = CONCAT('PON', LPAD(LAST_INSERT_ID(), 5, '0'));
        end;
    end if;

# ------------------------

create
    definer = root@localhost procedure filter_import_by_supplier(IN supplierId int)
BEGIN


    select im.code,
           i.name        as 'inventoryName',
           im.total_price   as 'totalPrice',
           im.is_done       as 'isDone',
           im.is_paid       as 'isPaid',
           im.is_import     as 'isImport',
           im.is_return     as 'isReturn',
           (SELECT i2.create_at FROM imports_status i2 WHERE i2.import_id = im.id ORDER BY id LIMIT 1) as 'first',
           (SELECT i2.create_at FROM imports_status i2 WHERE  i2.import_id = im.id ORDER BY id DESC LIMIT 1) as 'last'
    from imports im
             inner join imports_status is2 on im.id = is2.import_id
             inner join inventories i on im.inventory_id = i.id
             inner join supplier s on im.supplier_id = s.id
    where im.supplier_id = supplierId
    group by im.code,im.id
    order by im.id desc;
END;

# ------------------------------

create
    definer = root@localhost procedure filter_import_invoice(IN key_word text)
BEGIN

    declare search_value text;
    set search_value = lower(concat('%', key_word, '%'));


    CREATE TEMPORARY TABLE IF NOT EXISTS products_count1
    (
        code        text,
        supplierCode        text,
        inventoryName    text,
        totalPrice bigint,
        isDone boolean,
        isPaid boolean,
        isImport boolean,
        isReturn boolean,
        userName text,
        deliveryDate text
    );

    insert into products_count1 (select imports.code,
                                        s.code        as 'supplierCode',
                                        i.name        as 'inventoryName',
                                        total_price   as 'totalPrice',
                                        is_done       as 'isDone',
                                        is_paid       as 'isPaid',
                                        is_import     as 'isImport',
                                        is_return     as 'isReturn',
                                        a.username    as 'userName',
                                        delivery_date as 'deliveryDate'
                                 from imports
                                          inner join accounts a on imports.account_id = a.id
                                          inner join inventories i on imports.inventory_id = i.id
                                          inner join supplier s on imports.supplier_id = s.id
                                 where (lower(imports.code) like concat('%', search_value, '%') or
                                        lower(s.code) like concat('%', search_value, '%') or lower(i.name) like concat('%', search_value, '%') )
                                 order by imports.id desc);


    select * from products_count1 order by code desc;


    drop table products_count1;

END;


# ---------------


create
    definer = root@localhost procedure filter_product_variant(IN page int, IN size int, IN key_word text)
BEGIN
    declare offset_number int;
    declare search_value text;
    set search_value = lower(concat('%', key_word, '%'));
    set offset_number = (page - 1) * size;


    CREATE TEMPORARY TABLE IF NOT EXISTS products_count2
    (
        id int,
        code text,
        name text,
        quantity int,
        image longtext,
        importPrice bigint
    );

    insert into products_count2 (select pv.id,
                                        pv.code,
                                        pv.name,
                                        pv.image,
                                        IF(quantity >= 0, sum(quantity), 0)    as quantity,
                                        IF(import_price > 0, import_price, 0) as importPrice
                                 from product_variant pv
                                          left join inventories_product_variant ipv on pv.id = ipv.product_variant_id
                                          inner join product p on pv.product_id = p.id
                                 where (p.is_delete = false and pv.is_delete = false)
                                   and (lower(pv.name) like concat('%', search_value, '%') or
                                        pv.code like concat('%', search_value, '%'))
                                 group by pv.id, pv.code, pv.name, IF(import_price > 0, import_price, 0)
                                 order by pv.id desc
                                 limit size offset offset_number);


    select * from products_count2 order by id desc;


    drop table products_count2;

END;



# ---------------------


create
    definer = root@localhost procedure count_product_variant(IN key_word text)
BEGIN

    declare search_value text;
    set search_value = lower(concat('%', key_word, '%'));
    select count(table1.total)
    from (select 0 as total  from product left join product_variant on product.id=product_variant.product_id
          where (lower(product.name) like search_value or lower(product.code) like search_value) and product.is_delete=false
          group by(product.id)) as table1
    group by table1.total;

END;

# -------------

alter table db_udpm11_v1.details_imports
    add constraint key_name
        primary key (id);


# -------------
alter table db_udpm11_v1.imports_status
    modify create_at  datetime default (now()) null;


# -----------

create
    definer = root@localhost procedure select_create_at(IN producVariantId int)
BEGIN
    select create_at from product inner join product_variant on product.id = product_variant.product_id where product_variant.id = producVariantId;
END;


# --------------

create
    definer = root@localhost procedure select_create_at(IN producVariantId int)
BEGIN
    select p.create_at from product p inner join
                            product_variant on p.id = product_variant.product_id where product_variant.id = producVariantId;
END;

# ----------------

create
    definer = root@localhost procedure get_categoriesByIdorName(IN valueText text)
BEGIN
    select * from categories where (categories.id  like concat('%', valueText, '%') or categories.name like concat('%', valueText, '%')) order by categories.id desc;
END;

alter table db_udpm11_v1.product
    drop column thumbnail;

alter table db_udpm11_v1.product_variant
    modify position bit default  b'0'  null;

