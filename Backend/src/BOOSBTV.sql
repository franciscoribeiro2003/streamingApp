--PORT É 3306--
--USER É root--
--PASS É BOOSBTV--
-- Drop the database if it exists
DROP DATABASE IF EXISTS BOOSBTV;

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS BOOSBTV;

-- Use the BOOSBTV database
USE BOOSBTV;

SELECT user, host FROM mysql.user WHERE user = 'root';

CREATE USER 'root'@'localhost' IDENTIFIED BY 'BOOSBTV';

GRANT ALL PRIVILEGES ON BOOSBTV TO 'root'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;


-- Drop the MOVIE and GENRE tables if they exist
DROP TABLE IF EXISTS MOVIE, GENRE;

-- Create the MOVIE table
CREATE TABLE IF NOT EXISTS MOVIE (
    MovieID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Titulo VARCHAR(50) NOT NULL,
    Link VARCHAR(250) NOT NULL,
    Descricao VARCHAR(1000) NOT NULL,
    Duration INTEGER NOT NULL,
    GenreID VARCHAR(50) NOT NULL,
    Thumbnail VARCHAR(250) NOT NULL
);

-- Create the GENRE table
CREATE TABLE IF NOT EXISTS GENRE (
    GenreID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Genre VARCHAR(50) NOT NULL
);

-- Create the MOVIE_GENRE table
CREATE TABLE IF NOT EXISTS MOVIE_GENRE (
    MovieID INTEGER NOT NULL,
    GenreID INTEGER NOT NULL,
    PRIMARY KEY (MovieID, GenreID),
    FOREIGN KEY (MovieID) REFERENCES MOVIE (MovieID),
    FOREIGN KEY (GenreID) REFERENCES GENRE (GenreID)
);

-- Insert data into MOVIE table
INSERT INTO MOVIE (Titulo, Link, Descricao, Duration, GenreID, Thumbnail) VALUES ('Porn', 'https:naoseibro', 'É um filme de "adultos"',120, '2', 'ganda foto'),
                                                                                 ('NotPorn', 'https:naoseibro', 'É um filme de "adultos"',120, '2', 'ganda foto');

-- Insert data into GENRE table
INSERT INTO GENRE (Genre) VALUES
('Action'), ('Adventure'), ('Animation'), ('Biography'), ('Comedy'),
('Crime'), ('Documentary'), ('Drama'), ('Family'), ('Fantasy'),
('Film Noir'), ('History'), ('Horror'), ('Music');

-- Insert data into MOVIE_GENRE table
INSERT INTO MOVIE_GENRE (MovieID, GenreID) VALUES
(1, 2),
(2, 3);
