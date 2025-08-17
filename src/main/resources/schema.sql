CREATE TABLE movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    release_year INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    studios VARCHAR(255),
    producers VARCHAR(255),
    winner BOOLEAN
);