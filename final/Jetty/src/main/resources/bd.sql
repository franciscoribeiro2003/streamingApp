--CREATE DATABASE IF NOT EXISTS netflixpp;
Use netflixpp;

Create table user(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    fullname VARCHAR(120)
);
-- prmpt to insert a user
--insert into user (username, password, fullname) values ('corvo', 'corvo', 'Francisco');

Create table movies (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (80) NOT NULL,
    pathHigh VARCHAR (225) NOT NULL UNIQUE,
    pathLow VARCHAR (225) NOT NULL UNIQUE,
    poster VARCHAR (225) NOT NULL UNIQUE,
    year INT,
    totaltime time,
    linkLow VARCHAR (225) UNIQUE,
    linkHigh VARCHAR (225) UNIQUE,
    linkPoster VARCHAR (225) UNIQUE,
    uploadedBy  VARCHAR(80) NOT NULL,
    Foreign Key (uploadedBy) references user(username)
);
-- create a prompto insert the movie
-- insert into movies (name, pathHigh, pathLow, poster, year, totaltime, linkLow, linkHigh, linkPoster, uploadedBy) values ('The Matrix', 'C:\Users\joaop\Documents\GitHub\NetflixPP\src\main\resources\static\videos\The Matrix.mp4', 'C:\Users\joaop\Documents\GitHub\NetflixPP\src\main\resources\static\videos\The Matrix.mp4', 'C:\Users\joaop\Documents\GitHub\NetflixPP\src\main\resources\static\videos\The Matrix.mp4', 1999, '02:16:00', 'http://localhost:8080/videos/The Matrix.mp4', 'http://localhost:8080/videos/The Matrix.mp4', 'http://localhost:8080/videos/The Matrix.mp4', 'corvo');


--Create table uploads (
  --  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    --movieID INT NOT NULL,
     --mudei chave para nome do utilizador
    --Foreign Key (movieID) references movies(id),
--);

 --insert in to movies