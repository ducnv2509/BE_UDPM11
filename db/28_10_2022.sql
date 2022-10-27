create
definer = root@localhost procedure addOrderPurchase(IN _account_id bigint, IN _address varchar(255),
                                                        IN _note varchar(255))
BEGIN
    declare acc_name nvarchar(255);
    declare total_main double;
    declare id_main double;
    declare total_quantity_main integer;

    select username into acc_name from accounts where id = _account_id;

    select sum(quantity) ,
           sum(quantity * wholesale_price)
    into total_quantity_main, total_main
    from cart_items join cart on cart_items.id_cart = cart.id
        join product_variant on product_variant.id = cart_items.id_product
    where account_id = _account_id;

    insert into order_purchase (account_id, account_name, address_id, note, total_price, total_quantity, status, type, status_by, created_time, code)
    values (_account_id, acc_name, _address, _note, total_main, total_quantity_main, null, null, null, Now(), null);
    select last_insert_id() into  id_main;
    insert INTO  order_purchase_items(id_order, id_product, quantity, price, total_price)
    select  b.id id_order, id_product id_product, quantity quantity, wholesale_price price, (quantity * wholesale_price)  total_price
    from cart_items join cart c on cart_items.id_cart = c.id
                    join product_variant on product_variant.id = cart_items.id_product
                    join (SELECT * from order_purchase order by id desc limit 1) b on (c.account_id = b.account_id)
    where c.account_id = _account_id ;

    update inventories_product_variant i inner join cart_items c on c.id_product = i.product_variant_id
    set i.quantity = i.quantity - c.quantity
        where i.product_variant_id=c.id_product;

    delete from cart_items where id_cart = (select id from cart where account_id = _account_id);
    select * from order_purchase_items p where p.id_order = id_main;
END;

