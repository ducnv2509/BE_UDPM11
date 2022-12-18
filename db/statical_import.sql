create
definer = ducnv@'%' procedure get_statistic_import(IN inventory_id int, IN supplier_id int,
                                                            IN start_date datetime, IN end_date datetime,
                                                            IN sort_by text, IN sort_dir bit, IN page int, IN size int,
                                                            IN key_search text)
BEGIN


DROP TABLE IF EXISTS `db_udpm11_v1`.`p_return` ;
DROP TABLE IF EXISTS `db_udpm11_v1`.`p_receive` ;
DROP TABLE IF EXISTS `db_udpm11_v1`.`final` ;

CREATE TEMPORARY TABLE p_return (
                                        import_id   int ,
                                        details_import_id  int ,
                                        return_import_id  int ,
                                        product_variant_id  int ,
                                        return_number  int
    );
    CREATE TEMPORARY TABLE p_receive (
                                         inventory_id int,
                                         supplier_id int,
                                         account_id int,
                                         import_id int,
                                         import_code text,
                                         details_import_id int,
                                         product_variant_id int,
                                         import_price decimal(20,2),
                                         import_number int ,
                                         total_price decimal(20,2) ,
                                         delivery_date text,
                                         create_at datetime
    );

    CREATE TEMPORARY TABLE final (
                                     inventory_id int,
                                     supplier_id int,
                                     account_id int,
                                     import_id int,
                                     import_code text,
                                     details_import_id int,
                                     product_variant_id int,
                                     import_price decimal(20,2),
                                     import_number int ,
                                     total_price decimal(20,2) ,
                                     delivery_date text,
                                     create_at datetime,
                                     code text,
                                     name text,
                                     return_number int,
                                     receive_number int ,
                                     product_id int,
                                     product_code text
    );
insert into p_receive
select imports.inventory_id as inventory_id,
       imports.supplier_id as supplier_id,
       imports.account_id as account_id,
       imports.id as import_id,
       imports.code as import_code,
       details_imports.id as details_import_id,
       product_variant_id,
       details_imports.import_price as import_price,
       details_imports.quantity as import_number,
       imports.total_price,
       delivery_date,
       create_at
from  details_imports  left join imports on details_imports.import_id=imports.id left join imports_status on imports.id=imports_status.import_id

where imports.is_done=true and imports.is_import=true and imports_status.id and status_id=4 ;

insert into p_return
select details_imports.import_id as import_id,
       details_imports.id as details_import_id,
       return_import_id,
       product_variant_id,
       sum(details_return_import.quantity )as return_number
from return_import inner join details_return_import on return_import.id=details_return_import.return_import_id
                   inner join details_imports on details_imports.id=details_return_import.details_import_id
group by details_import_id , product_variant_id;

insert into final select tbl1.* ,product.code as product_code from
    (select p_receive.*,product_variant.code as code,product_variant.name as name,if(p_return.return_number>0,p_return.return_number,0) as return_number,(p_receive.import_number-if(p_return.return_number>0,p_return.return_number,0)) as receive_number  ,product_variant.product_id as product_id
     from p_receive left join p_return on p_receive.details_import_id =p_return.details_import_id left join product_variant on product_variant.id=p_receive.product_variant_id
     where create_at>=start_date and create_at<=end_date ) as tbl1 left join product on product.id=tbl1.product_id
;



set @from_string =' from final ' ;

    set @search_value=concat('"%',lower(key_search),'%"');
    set @clause =concat(' where create_at>="',start_date,'" and create_at<="',end_date,'"' ,' and (lower(code) like ',@search_value,' or lower(name) like  ',@search_value ,') ' );
    set @col =' inventory_id, supplier_id, account_id, import_id, import_code, details_import_id, product_variant_id, sum(import_price*(import_number-return_number))/sum(import_number-return_number) as avg_price, sum(import_number) as import_number, sum(return_number) as return_number, (sum(import_number)- sum(return_number)) as receive_number, total_price, delivery_date, create_at, code, name,product_id,product_code' ;
    set @group_by =' group by product_variant_id ';
    if(sort_dir) then
begin
            set @order_by =concat(' order by ',sort_by,' asc' );

end;
else
begin
            set @order_by =concat(' order by ',sort_by,' desc' );

end;
end if;
    if( inventory_id>0) then
begin
            set @clause=concat(@clause,' and inventory_id= ',inventory_id);
end;
end if;
    if(supplier_id>0) then
begin
            set @clause=concat(@clause,' and supplier_id= ',supplier_id);
end;
end if;


    set @query_string=concat('select ',@col,@from_string,@clause,@group_by,@order_by);
    if(page>0 && size>0) then
begin
            set @query_string=concat(@query_string,' limit ',size,' offset ',(page-1)*size );
end;
end if;

-- select @query_string;
PREPARE stmt1 FROM @query_string;
EXECUTE stmt1 ;

END;


create
definer = ducnv@'%'  procedure get_statistic_import_extend(IN inventory_id int, IN supplier_id int,
                                                                   IN start_date datetime, IN end_date datetime,
                                                                   IN sort_by text, IN sort_dir bit, IN page int,
                                                                   IN size int, IN key_search text)
BEGIN


DROP TABLE IF EXISTS `db_udpm11_v1`.`p_return` ;
DROP TABLE IF EXISTS `db_udpm11_v1`.`p_receive` ;
DROP TABLE IF EXISTS `db_udpm11_v1`.`final` ;

CREATE TEMPORARY TABLE p_return (
                                        import_id   int ,
                                        details_import_id  int ,
                                        return_import_id  int ,
                                        product_variant_id  int ,
                                        return_number  int
    );
    CREATE TEMPORARY TABLE p_receive (
                                         inventory_id int,
                                         supplier_id int,
                                         account_id int,
                                         import_id int,
                                         import_code text,
                                         details_import_id int,
                                         product_variant_id int,
                                         import_price decimal(20,2),
                                         import_number int ,
                                         total_price decimal(20,2) ,
                                         delivery_date text,
                                         create_at datetime
    );

    CREATE TEMPORARY TABLE final (
                                     inventory_id int,
                                     supplier_id int,
                                     account_id int,
                                     import_id int,
                                     import_code text,
                                     details_import_id int,
                                     product_variant_id int,
                                     import_price decimal(20,2),
                                     import_number int ,
                                     total_price decimal(20,2) ,
                                     delivery_date text,
                                     create_at datetime,
                                     code text,
                                     name text,
                                     return_number int,
                                     receive_number int
    );
insert into p_receive
select imports.inventory_id as inventory_id,
       imports.supplier_id as supplier_id,
       imports.account_id as account_id,
       imports.id as import_id,
       imports.code as import_code,
       details_imports.id as details_import_id,
       product_variant_id,
       details_imports.import_price as import_price,
       details_imports.quantity as import_number,
       imports.total_price,
       delivery_date,
       create_at
from  details_imports  left join imports on details_imports.import_id=imports.id left join imports_status on imports.id=imports_status.import_id

where imports.is_done=true and imports.is_import=true and imports_status.id and status_id=4 ;

insert into p_return
select details_imports.import_id as import_id,
       details_imports.id as details_import_id,
       return_import_id,
       product_variant_id,
       sum(details_return_import.quantity )as return_number
from return_import inner join details_return_import on return_import.id=details_return_import.return_import_id
                   inner join details_imports on details_imports.id=details_return_import.details_import_id
group by details_import_id , product_variant_id;

insert into final
select p_receive.*,product_variant.code as code,product_variant.name as name,if(p_return.return_number>0,p_return.return_number,0) as return_number,(p_receive.import_number-if(p_return.return_number>0,p_return.return_number,0)) as receive_number
from p_receive left join p_return on p_receive.details_import_id =p_return.details_import_id left join product_variant on product_variant.id=p_receive.product_variant_id
where create_at>=start_date and create_at<=end_date
;


set @search_value=concat('"%',lower(key_search),'%"');
    set @clause =concat(' where create_at>="',start_date,'" and create_at<="',end_date,'"' ,' and (lower(code) like ',@search_value,' or lower(name) like  ',@search_value ,') ' );
    set @from_string =' from final ' ;
    set @col =' inventory_id, supplier_id, account_id, import_id, import_code, details_import_id, product_variant_id, import_price, import_number , return_number, (import_number- return_number) as receive_number,(import_number- return_number)*import_price total_price, delivery_date, create_at, code ,name' ;
    set @group_by ='  ';
    if(sort_dir) then
begin
            set @order_by =concat(' order by ',sort_by,' asc' );

end;
else
begin
            set @order_by =concat(' order by ',sort_by,' desc' );

end;
end if;
    if( inventory_id>0) then
begin
            set @clause=concat(@clause,' and inventory_id= ',inventory_id);
end;
end if;
    if(supplier_id>0) then
begin
            set @clause=concat(@clause,' and supplier_id= ',supplier_id);
end;
end if;


    set @query_string=concat('select ',@col,@from_string,@clause,@group_by,@order_by);
    if(page>0 && size>0) then
begin
            set @query_string=concat(@query_string,' limit ',size,' offset ',(page-1)*size );
end;
end if;

-- select @query_string;
PREPARE stmt1 FROM @query_string;
EXECUTE stmt1 ;

END;

