-- insert into `loc_permission` (`userId`, `locationId`) VALUES (4, 1);

insert into `locations` (`location_id`, `address1`, `address2`, `approved`, `city`, `costPerHour`, `country`, `gps_latitude`, `gps_longitude`, `postcode`, `sensors`, `spaces`) VALUES (4, 'Sheffield', 'Test Street', 1, 'Sheffield', 2, 'United Kingdom', 53.37701, -1.46814, 'S1 2BP', 1, 5);

insert into `loc_permission` (`userId`, `locationId`) VALUES (4, 4);

 
select * from loc_permission