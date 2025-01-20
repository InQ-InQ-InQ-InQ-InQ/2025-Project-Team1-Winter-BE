CREATE DATABASE photo;

CREATE TABLE user(
    user_id BIGINT AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE user_info(
    user_info_id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    nickname VARCHAR(32) NOT NULL UNIQUE,
    email VARCHAR(255),
    phone VARCHAR(15),
    gender ENUM('FEMALE','MALE'),
    birth DATE,
    created_at DATETIME(6),
    modified_at DATETIME(6),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    PRIMARY KEY(user_info_id)
);

CREATE TABLE follow(
    
);