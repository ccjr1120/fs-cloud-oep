create table sys_role
(
    rid varchar(36) not null comment '角色id'
        primary key,
    name varchar(32) not null comment '角色名',
    create_id varchar(32) null,
    create_date datetime null,
    update_id varchar(32) null,
    update_time datetime null,
    del_flag tinyint(1) default 0 null,
    constraint sys_role_name_uindex
        unique (name)
)
    comment '系统角色表';

create table sys_user
(
    uid varchar(36) not null comment 'uuid'
        primary key,
    account varchar(32) not null comment '登录账号，学生、老师默认为学工号，其它账户可自定义',
    name varchar(24) not null comment '用户姓名',
    password varchar(64) not null comment '密码',
    status tinyint(1) default 1 null comment '账号状态{0：锁定，1：可用}',
    role_id varchar(32) not null comment '角色id',
    create_id varchar(32) null,
    create_time datetime null,
    update_id varchar(32) null,
    update_time datetime null,
    del_flag tinyint(1) default 0 null,
    constraint sys_user_account_uindex
        unique (account)
)
    comment '用户表';

