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

    update inventories_product_variant i inner join cart_items ci on ci.id_product = i.product_variant_id join cart c on ci.id_cart = c.id
    set i.quantity = i.quantity - ci.quantity
    where i.product_variant_id=ci.id_product and c.account_id = _account_id ;
    
    delete from cart_items where id_cart = (select id from cart where account_id = _account_id);
    select * from order_purchase_items p where p.id_order = id_main;
END;

-- FIX add cart
create
    definer = root@localhost procedure createCart(IN _account_id int, IN _id_product int, IN _quantity int)
BEGIN
    declare id_cart_main long;
    if (!EXISTS(select * from cart where account_id = _account_id))
    then
        insert into cart(account_id, total_quantity, total_price)
        values (_account_id, null, null);
        select id into id_cart_main from cart where account_id = _account_id;
        insert into cart_items (id_cart, id_product, quantity, price, total)
        VALUES (id_cart_main, _id_product, _quantity, null, null);
    else
        select id into id_cart_main from cart where account_id = _account_id;
        if (!EXISTS(select * from cart_items  where id_product = _id_product and id_cart = id_cart_main))
        then
            insert into cart_items (id_cart, id_product, quantity, price, total)
            VALUES (id_cart_main, _id_product, _quantity, null, null);

        else
            update cart_items
            set quantity = cart_items.quantity + _quantity
            where id_cart = id_cart_main and id_product = _id_product;
        end if;
    end if;
    select * from cart_items where id_product = _id_product and id_cart = id_cart_main;

END;



