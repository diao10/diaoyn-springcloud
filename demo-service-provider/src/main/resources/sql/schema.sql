create table if not exists my_user
(
    user_id     int          not null primary key auto_increment comment '用户Id',
    user_name   varchar(200) null comment '用户名称',
    real_name   varchar(200) null comment '用户真实名称',
    password    varchar(200) null comment '用户密码',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '修改时间'
);
