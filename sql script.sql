
-- insert into `locations` (`location_id`, `address1`, `address2`, `approved`, `city`, `country`, `gps_latitude`, `gps_longitude`, `postcode`, `spaces`, `userId`) VALUES (3, 'Sheffield', 'Sheaf St', 1, 'Sheffield', 'United Kingdom', 53.37701, -1.46814, 'S1 2BP', 5, 4);
-- select b.id, b.locationId, b.start_date, b.end_date, b.active from bookings b where b.locationId = 2 AND (b.start_date >= '2019-12-30 01:51:00.865628' AND b.end_date <= '2019-12-31 01:51:00.865628') OR (b.start_date <= '2019-12-30 01:51:00.865628' AND b.end_date <= '2019-12-31 01:51:00.865628') OR (b.start_date >= '2019-12-30 01:51:00.865628' AND b.end_date >= '2019-12-31 01:51:00.865628');
-- insert into `loc_permission` (`userId`, `locationId`) VALUES (4, 1);

-- insert into `bookings` (`id`, `active`, `booking_url`, `bookingsPerParkingSlot`, `cancelled`, `completed`, `depositFee`, `end_date`, `fee`, `issue`, `length`, `confirmation`, `parkingId`, `start_date`, `car_id`, `locationId`, `userId`) VALUES (9, 1, 'test', 0, 0, 0, 8, '2020-05-14 12:30:00.000000', 10, 0, 0, 0, 2, '2020-05-14 09:00:00.000000', 7, 1, 1);
-- insert into `bookings` (`id`, `active`, `booking_url`, `bookingsPerParkingSlot`, `cancelled`, `completed`, `depositFee`, `end_date`, `fee`, `issue`, `length`, `confirmation`, `parkingId`, `start_date`, `car_id`, `locationId`, `userId`) VALUES (10, 1, 'test', 0, 0, 0, 8, '2020-05-14 15:00:00.000000', 10, 0, 0, 0, 2, '2020-05-14 13:30:00.000000', 7, 1, 1);
-- insert into `bookings` (`id`, `active`, `booking_url`, `bookingsPerParkingSlot`, `cancelled`, `completed`, `depositFee`, `end_date`, `fee`, `issue`, `length`, `confirmation`, `parkingId`, `start_date`, `car_id`, `locationId`, `userId`) VALUES (11, 1, 'test', 0, 0, 0, 8, '2020-05-14 13:30:00.000000', 10, 0, 0, 0, 3, '2020-05-14 09:30:00.000000', 7, 1, 1);

select * from users;
