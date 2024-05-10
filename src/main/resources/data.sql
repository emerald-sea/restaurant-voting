INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('I love rest 3', 'test@gmail.com', '{noop}test');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (name)
VALUES ('Rest 1'),
       ('Rest 2'),
       ('Rest 3');

INSERT INTO DISH (name, price, restaurant_id)
    VALUES ('Dish 1', 100,  1),
           ('Dish 2', 200,  2),
           ('Dish 3', 300,  3);

INSERT INTO VOTE (restaurant_id, user_id)
VALUES ( 1, 1),
       ( 2, 2),
       ( 3, 4);