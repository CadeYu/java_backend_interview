create table wallet_transfer
(
    transfer_id   int auto_increment comment '每笔交易的id位此表的唯一标识，且自增'
        primary key,
    user_id       int                                not null comment '用户id，与上表连接',
    amount        decimal(10, 2)                     not null comment '每一笔交易的金额',
    type          char                               not null comment '交易的类型，D表示充值，C表示消费',
    transfer_date datetime default CURRENT_TIMESTAMP not null comment '交易发生的时间，默认值为当前的时间',
    constraint wallet_transfer_ibfk_1
        foreign key (user_id) references wallet (user_id),
    check (`type` in (_utf8mb4\'D\',_utf8mb4\'C\'))
);

create index user_id
    on wallet_transfer (user_id);

INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (1, 1, 100.00, 'C', '2022-12-27 10:57:24');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (2, 1, 100.00, 'C', '2022-12-27 11:08:18');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (3, 1, 100.00, 'C', '2022-12-27 11:09:04');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (4, 1, 20.00, 'D', '2022-12-27 11:09:04');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (5, 1, 100.00, 'C', '2022-12-27 11:37:53');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (6, 1, 20.00, 'D', '2022-12-27 11:37:53');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (7, 1, 100.00, 'C', '2022-12-27 11:38:23');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (8, 1, 20.00, 'D', '2022-12-27 11:38:23');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (9, 1, 100.00, 'C', '2022-12-27 11:39:01');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (10, 1, 20.00, 'D', '2022-12-27 11:39:01');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (11, 1, 100.00, 'C', '2022-12-27 11:39:34');
INSERT INTO java_demo.wallet_transfer (transfer_id, user_id, amount, type, transfer_date) VALUES (12, 1, 20.00, 'D', '2022-12-27 11:39:34');
