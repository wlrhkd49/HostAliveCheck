create table host (
    id bigint not null auto_increment,
    ip varchar(255),
    last_alive_time datetime(6),
    mod_date datetime(6),
    name varchar(255),
    reg_date datetime(6),
    primary key (id)
)

-- create 수행 후,
alter table host
    add constraint constraint_name unique (name, ip)