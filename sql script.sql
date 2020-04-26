
-- insert into `locations` (`location_id`, `address1`, `address2`, `approved`, `city`, `country`, `gps_latitude`, `gps_longitude`, `postcode`, `spaces`, `userId`) VALUES (3, 'Sheffield', 'Sheaf St', 1, 'Sheffield', 'United Kingdom', 53.37701, -1.46814, 'S1 2BP', 5, 4);
-- select b.id, b.locationId, b.start_date, b.end_date, b.active from bookings b where b.locationId = 2 AND (b.start_date >= '2019-12-30 01:51:00.865628' AND b.end_date <= '2019-12-31 01:51:00.865628') OR (b.start_date <= '2019-12-30 01:51:00.865628' AND b.end_date <= '2019-12-31 01:51:00.865628') OR (b.start_date >= '2019-12-30 01:51:00.865628' AND b.end_date >= '2019-12-31 01:51:00.865628');
-- insert into `loc_permission` (`userId`, `locationId`) VALUES (4, 1);-- 

-- insert into `locations` (`location_id`, `address1`, `address2`, `approved`, `city`, `country`, `gps_latitude`, `gps_longitude`, `postcode`, `spaces`, `CostPerHour`, `sensors`) VALUES (2, '210', 'Portobello', 0, 'Sheffield', 'United Kingdom', 53.3812899551267, -1.4804996191406228, 'S1 2BP', 5, 3, 1);
-- insert into `locations` (`location_id`, `address1`, `address2`, `approved`, `city`, `country`, `gps_latitude`, `gps_longitude`, `postcode`, `spaces`, `CostPerHour`, `sensors`) VALUES (3, '88', 'Westbourne Road', 1, 'Sheffield', 'United Kingdom', 53.3739985737835, -1.5017856298828103, 'S1 2GJ', 5, 3, 1);
-- insert into `locations` (`location_id`, `address1`, `address2`, `approved`, `city`, `country`, `gps_latitude`, `gps_longitude`, `postcode`, `spaces`, `CostPerHour`, `sensors`) VALUES (4, 'Heeley Retail Park', 'Meersbrook', 1, 'Sheffield', 'United Kingdom', 53.35663547730316, -1.478886054019446, 'S1 2GH', 10, 3, 1);

-- insert into `loc_permission` (`userId`, `locationId`) VALUES (5, 2);
-- insert into `loc_permission` (`userId`, `locationId`) VALUES (5, 3);
-- insert into `loc_permission` (`userId`, `locationId`) VALUES (5, 4);


select * from bookings;
