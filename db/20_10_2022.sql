
alter table db_udpm11_v1.product_variant
    modify position bit default  b'0'  null;

create
    definer = root@`localhost` procedure getDetailProduct( in _product_id bigint)
BEGIN
    select sum(quantity) as quantity, product_id ,id, name, image, wholesale_price from product_variant join inventories_product_variant on product_variant.id = inventories_product_variant.product_variant_id where product_id = _product_id;
END;


create
    definer = root@`localhost` procedure getOptionProduct(in _product_id bigint)
BEGIN
    select (SELECT group_concat(DISTINCT option1) FROM product_variant where product_id = _product_id) as OP1,
           (SELECT group_concat(DISTINCT option2) FROM product_variant where product_id = _product_id) as OP2,
           (SELECT group_concat(DISTINCT option3) FROM product_variant where product_id = _product_id) as OP3;
END;


create
    definer = root@localhost procedure getProductByOption(IN _op1 varchar(255), IN _op2 varchar(255),
                                                          IN _op3 varchar(255), IN _product_id bigint)
BEGIN
    select id, name, option1, option2, option3, wholesale_price, sale_price, import_price, code, image
    from product_variant
    where option1 = _op1 and option2 = _op2 and option3 = _op3 and product_id = _product_id;
END;


create
    definer = root@localhost procedure getAllProduct()
BEGIN
    select product.id, pv.image, product.name, wholesale_price
    from product
             join product_variant pv on product.id = pv.product_id
    where pv.position = true;
END;



