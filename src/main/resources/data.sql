INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Restaurant 1'),
       ('Restaurant 2'),
       ('Restaurant 3');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Dish 1', 100, 1),
       ('Dish 2', 200, 2),
       ('Dish 3', 300, 3);
