
create table driver(
    id real primary key,
    name text,
    age serial,
    license boolean
);

create table car (
    brand text,
    model text,
    price serial,
    owner_id real references driver(id)
);

CREATE SEQUENCE id_sequence
    start 1
    increment 1;

insert into driver (id,name,age,license) values (nextval('id_sequence'),'Ivan',20,true);
insert into driver (id,name,age,license) values (nextval('id_sequence'),'Petr',22,false);
insert into driver (id,name,age,license) values (nextval('id_sequence'),'Zhora',26,true);

insert into car(brand,model,price,owner_id) values ('lada','samara',10000,1);
insert into car(brand,model,price) values ('zhiguli','kopeika',5000);
insert into car(brand,model,price,owner_id) values ('ford','focus',30000,3);

select * from driver inner join car on driver.id = car.owner_id




