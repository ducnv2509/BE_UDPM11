create table cart
(
    id             bigint auto_increment primary key,
    account_id     bigint,
    total_quantity int,
    total_price    double
);

create table cart_items
(
    id         bigint auto_increment primary key,
    id_cart    bigint,
    id_product bigint,
    quantity   int,
    price      double,
    total      double
);

-- sổ địa chỉ
create table order_account
(
    id             bigint auto_increment primary key,
    account_id     bigint,
    city_id        int,
    district_id    int,
    commune_id     int,
    address_detail nvarchar(255),
    type           boolean,
    status         boolean default true
);

-- type: pt thanh toán
-- status: trạng thái đơn hàng

create table `order_purchase`
(
    id             bigint auto_increment primary key,
    account_id     bigint,
    account_name   nvarchar(255),
    address_id     bigint,
    note           nvarchar(255),
    total_price    double,
    total_quantity int,
    status         int,
    type           int,
    status_by      nvarchar(255),
    created_time   datetime,
    code           nvarchar(25) unique
);

create table `status`
(
    id   bigint auto_increment primary key,
    code nvarchar(255),
    name nvarchar(255)
);

create table order_by_status_history
(
    order_purchase_id bigint,
    status_id         bigint,
    created_at        datetime,
    primary key (order_purchase_id, status_id)
);

create table order_purchase_items
(
    id          bigint auto_increment primary key,
    id_order    bigint,
    id_product  bigint,
    quantity    int,
    price       double,
    total_price double
);


