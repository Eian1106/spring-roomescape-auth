CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at TIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE theme
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE store
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL DEFAULT 'MANAGER',
    store_id BIGINT       NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (store_id) REFERENCES store (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT
);

CREATE TABLE reservation
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username     VARCHAR(255) NOT NULL,
    user_id BIGINT,
    store_id BIGINT       NOT NULL DEFAULT 1,
    theme_id BIGINT       NOT NULL,
    date     DATE         NOT NULL,
    time_id  BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    FOREIGN KEY (store_id) REFERENCES store (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    FOREIGN KEY (theme_id) REFERENCES theme (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT
);
