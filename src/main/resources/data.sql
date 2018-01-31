/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

---------------------------------

-- Create 2 GASOLINE car

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (1, now(), false, 'AZC675', 4, false, 1, 'GASOLINE', 'RENAULT');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (2, now(), false, 'AGP875', 4, false, 1, 'GASOLINE', 'FORD');

-- Create 2 ELECTRIC car

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (3, now(), false, 'TKB852', 5, false, 1, 'ELECTRIC', 'BMW');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (4, now(), false, 'KPG123', 2, false, 1, 'ELECTRIC', 'MERCEDES');

-- Create 2 DIESEL car

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (5, now(), false, 'GWF572', 3, false, 1, 'DIESEL', 'FIAT');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (6, now(), false, 'WHH237', 2, false, 1, 'DIESEL', 'VW');