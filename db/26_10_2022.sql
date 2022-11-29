create
    definer = root@localhost procedure convertTo0(IN _account_id bigint, IN _product_id bigint)
BEGIN
    if (!EXISTS(select * from cart_items join cart on cart.id = cart_items.id_cart where  account_id =_account_id and id_product = _product_id))
    then
        select 0 as quantity;
    else
        select quantity from cart_items join cart on cart.id = cart_items.id_cart where  account_id =_account_id and id_product = _product_id ;
    end if;
END;



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
        select * from cart_items where id_product = _id_product and id_cart = id_cart_main;
    else

        select id into id_cart_main from cart where account_id = _account_id;
        if (!EXISTS(select * from cart_items where id_product = _id_product))
        then
            insert into cart_items (id_cart, id_product, quantity, price, total)
            VALUES (id_cart_main, _id_product, _quantity, null, null);
            select * from cart_items where id_product = _id_product and id_cart = id_cart_main;

        else
            update cart_items
            set quantity = cart_items.quantity + _quantity
            where id_cart = id_cart_main and id_product = _id_product;
            select * from cart_items where id_product = _id_product and id_cart = id_cart_main;
        end if;
    end if;
END;

