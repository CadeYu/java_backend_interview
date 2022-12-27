create table wallet
(
    user_id int            not null comment '用户id，作为该表唯一标识'
        primary key,
    balance decimal(10, 3) null comment '钱包的余额，balance字段总共10位数，7位整数，3位小数'
);

INSERT INTO java_demo.wallet (user_id, balance) VALUES (1, 14020.000);
INSERT INTO java_demo.wallet (user_id, balance) VALUES (2, 30000.000);
