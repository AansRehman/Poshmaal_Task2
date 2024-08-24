CREATE TABLE IF NOT EXISTS Artist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    country_of_birth VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS Artwork (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year_of_completion INT NOT NULL,
    price DOUBLE NOT NULL,
    sold BOOLEAN NOT NULL,
    artist_id BIGINT NOT NULL,
    FOREIGN KEY (artist_id) REFERENCES Artist(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Employee (
    email VARCHAR(255) PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    locked BOOLEAN NOT NULL,
    role VARCHAR(50) NOT NULL
    );

INSERT INTO employee (email, firstname, lastname, password, locked, role) VALUES ('manager@super.com', 'super', 'manager', 'manager', false, 'MANAGER');

