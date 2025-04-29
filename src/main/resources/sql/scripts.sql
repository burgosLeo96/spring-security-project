create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

insert ignore into users values ('user','{noop}password',true);
insert ignore into authorities values ('user','read');

insert ignore into users values ('admin','{bcrypt}$2a$12$9j7nuEOK75ygjwtHtc3ntORhhRqCKvZXOhFNZQqfeMevY.gcj0Ewm','1');
insert ignore into authorities values ('admin','admin');

create table customer
(
    id bigint not null auto_increment,
    email varchar(45) not null,
    pwd varchar(200) not null,
    role varchar(45) not null,
    primary key (id)
);

insert into customer (email, pwd, role) values ('test@example.com', '{noop}password', 'read');
insert into customer (email, pwd, role) values ('admin@example.com', '{bcrypt}$2a$12$9j7nuEOK75ygjwtHtc3ntORhhRqCKvZXOhFNZQqfeMevY.gcj0Ewm', 'admin');

select * from customer;

delete from customer where email = 'john.doe@example.com';