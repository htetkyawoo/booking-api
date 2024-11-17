insert into auth_user (id, email, password, gender, name, is_verified)
values
    (1, 'hello@gmail.com', '$2a$10$Ae5osYY3hEB86GwXz9YcReptLowSSwg1pE839usLqrCus4uk2PXQS', 1, 'hello', true), -- password hello123
    (2, 'he@gmail.com', '$2a$10$Ae5osYY3hEB86GwXz9YcReptLowSSwg1pE839usLqrCus4uk2PXQS', 1, 'hlo', true), -- password hello123
    (3, 'helo@gmail.com', '$2a$10$Ae5osYY3hEB86GwXz9YcReptLowSSwg1pE839usLqrCus4uk2PXQS', 1, 'helo', true); -- password hello123

insert into country (id, name, code)
values
    (1, 'Singapore', 'SGP'),
    (2, 'Myanmar', 'MMR');

insert into package (id, name, price, credits, valid_day, country_id)
values
    (1, 'Basic Package', 30, 200, 30, 1),
    (2, 'Advance Package', 50, 500, 30, 1),
    (3, 'Basic Package', 300, 200, 30, 2);

insert into class (id, name, start_time, end_time, cost, total_slot, country_id)
values
    (1, 'yoga', '2024-11-17 12:00', '2024-11-17 13:00', 10, 10, 1),
    (2, 'yoga', '2024-11-18 12:00', '2024-11-18 13:00', 10, 5, 1),
    (3, 'yoga', '2024-11-19 12:00', '2024-11-19 13:00', 10, 2, 2),
    (4, 'yoga', current_timestamp + interval 1 minute, current_timestamp + interval 2 minute, 10, 2, 2);
