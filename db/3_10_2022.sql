
-- b1: vs db local drop staff
-- b2: run project
-- b3: chạy các bảng dưới đây
create table staff_seqid
(
    id int auto_increment
        primary key
);



create definer = udpm11@20.189.112.68 trigger db_udpm11_v1.tg_staff_insert
    before insert
    on db_udpm11_v1.staff
    for each row
    IF NEW.code is null or NEW.code = '' THEN
        begin
            INSERT INTO staff_seqid VALUES (NULL);
            SET NEW.code = CONCAT('STAFF', LPAD(LAST_INSERT_ID(), 5, '0'));
        end;
    end if;