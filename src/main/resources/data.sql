DELETE FROM vote;
DELETE FROM dish;
DELETE FROM RESTAURANT;
DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (name)
VALUES ('FirstRest'),
       ('SecondRest'),
       ('ThirdRest');

INSERT INTO DISH (name, date_menu, restaurant_id, price)
VALUES ('soup FirstRest', CURRENT_DATE, 1, 500),
       ('pasta FirstRest', CURRENT_DATE, 1, 300),
       ('coffe FirstRest', CURRENT_DATE, 1, 200),
       ('soup SecondRest', CURRENT_DATE, 2, 500),
       ('pasta SecondRest', '2021-08-25', 2, 300),
       ('coffe SecondRest', '2021-08-25', 2, 200),
       ('soup ThirdRest', '2021-08-25', 3, 200),
       ('pasta ThirdRest', '2021-08-25', 3, 300),
       ('coffe ThirdRest', '2021-08-25', 3, 400);

INSERT INTO VOTE (voted_date, restaurant_id, user_id)
VALUES ('2021-08-25', 1, 1),
       ('2021-08-25', 1, 2),
       ('2021-08-25', 2, 3),
       (CURRENT_DATE, 2, 1),
       (CURRENT_DATE, 3, 2);
