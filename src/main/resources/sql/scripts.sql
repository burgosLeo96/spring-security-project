create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

insert ignore into users values ('user','{noop}password',true);
insert ignore into authorities values ('user','read');

insert ignore into users values ('admin','{bcrypt}$2a$12$9j7nuEOK75ygjwtHtc3ntORhhRqCKvZXOhFNZQqfeMevY.gcj0Ewm','1');
insert ignore into authorities values ('admin','admin');


delete from users where username='admin';

delete from authorities where username='admin';

select * from users;
select * from authorities;