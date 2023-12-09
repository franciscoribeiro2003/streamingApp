-- PORT É 3306 --
-- Drop the database if it exists
DROP DATABASE IF EXISTS BOOSBTV;

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS BOOSBTV;

-- Use the BOOSBTV database
USE BOOSBTV;

-- Drop the MOVIE and GENRE tables if they exist
DROP TABLE IF EXISTS MOVIE, USER;

-- Create the MOVIE table
CREATE TABLE IF NOT EXISTS MOVIE (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    linklow VARCHAR(2500),
    linkhigh VARCHAR(2500),
    pathHigh VARCHAR (225) NOT NULL UNIQUE,
    pathLow VARCHAR (225) NOT NULL UNIQUE,
    Descricao VARCHAR(1000),
    duration time,
    year INT,
    genre VARCHAR(50),
    Thumbnail VARCHAR(250) UNIQUE ,
    uploadedBy  VARCHAR(80) NOT NULL
);

Create table USER(
                     id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                     username VARCHAR(80) NOT NULL UNIQUE,
                     password VARCHAR(80) NOT NULL,
                     fullname VARCHAR(120)
);

insert into USER (username, password, fullname) values ('corvo', 'corvo', 'Francisco');
insert into MOVIE (name, linklow, linkhigh, pathHigh, pathLow, Descricao, duration, year, genre, Thumbnail, uploadedBy) values ('The Godfather', 'https://imdb-video.media-imdb.com/vi1348706585/1434659607842-pgv4ql-1616202346191.mp4?Expires=1702205113&Signature=aUfpJLs9dYo6YXhKq3odOdRMffETfHnojrOX4QDL-ZB2IxYjNu2pJi965W4QOshUcHt6CPZmEcv6Nf3CINFOuX1PnvoXUDUk9SbVEy29m8dFKDxr6IHEq9mC~oLbLJ5Mh3lN-SLd5f4LvAmyGeAyUqvp~bmkg63IdC3Mvywfh7kdhPLBBfw4423YFswT7mbt01MdnIq8J--BDauBJvGdfAqzqfeAwgQcaIMwargQbIUZv5nvDwCVYKki8P7If7gxoeyWVRyLSjGIOAVqcOlL0n8qM1DOEgr6HPxF-W7L6Gyo~hlva7n7Z3m67RFD-ken8wPmWpLnUGURQSUCjYtA-g__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA', 'https://imdb-video.media-imdb.com/vi1348706585/1434659607842-pgv4ql-1616202346191.mp4?Expires=1702205113&Signature=aUfpJLs9dYo6YXhKq3odOdRMffETfHnojrOX4QDL-ZB2IxYjNu2pJi965W4QOshUcHt6CPZmEcv6Nf3CINFOuX1PnvoXUDUk9SbVEy29m8dFKDxr6IHEq9mC~oLbLJ5Mh3lN-SLd5f4LvAmyGeAyUqvp~bmkg63IdC3Mvywfh7kdhPLBBfw4423YFswT7mbt01MdnIq8J--BDauBJvGdfAqzqfeAwgQcaIMwargQbIUZv5nvDwCVYKki8P7If7gxoeyWVRyLSjGIOAVqcOlL0n8qM1DOEgr6HPxF-W7L6Gyo~hlva7n7Z3m67RFD-ken8wPmWpLnUGURQSUCjYtA-g__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA', 'C:\Users\franc\Documents\GitHub\BOOSBTV\src\main\resources\static\videos\The Godfather.mp4', 'C:\Users\franc\Documents\GitHub\BOOSBTV\src\main\resources\static\videos\The Godfather.mp4', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', '02:55:00', 1972, 'Crime, Drama', 'https://www.belarte.co.uk/cdn/shop/products/The-God-father1_80ad8512-edc4-45c0-9594-a454fcf074f0.jpg?v=1628173762', 'corvo');
insert into MOVIE (name, linklow, linkhigh, pathHigh, pathLow, Descricao, duration, year, genre, Thumbnail, uploadedBy) values ('The Godfather: Part II', 'https://imdb-video.media-imdb.com/vi1348706585/1434659607842-pgv4ql-1616202346191.mp4?Expires=1702205113&Signature=aUfpJLs9dYo6YXhKq3odOdRMffETfHnojrOX4QDL-ZB2IxYjNu2pJi965W4QOshUcHt6CPZmEcv6Nf3CINFOuX1PnvoXUDUk9SbVEy29m8dFKDxr6IHEq9mC~oLbLJ5Mh3lN-SLd5f4LvAmyGeAyUqvp~bmkg63IdC3Mvywfh7kdhPLBBfw4423YFswT7mbt01MdnIq8J--BDauBJvGdfAqzqfeAwgQcaIMwargQbIUZv5nvDwCVYKki8P7If7gxoeyWVRyLSjGIOAVqcOlL0n8qM1DOEgr6HPxF-W7L6Gyo~hlva7n7Z3m67RFD-ken8wPmWpLnUGURQSUCjYtA-g__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA', 'https://imdb-video.media-imdb.com/vi1348706585/1434659607842-pgv4ql-1616202346191.mp4?Expires=1702205113&Signature=aUfpJLs9dYo6YXhKq3odOdRMffETfHnojrOX4QDL-ZB2IxYjNu2pJi965W4QOshUcHt6CPZmEcv6Nf3CINFOuX1PnvoXUDUk9SbVEy29m8dFKDxr6IHEq9mC~oLbLJ5Mh3lN-SLd5f4LvAmyGeAyUqvp~bmkg63IdC3Mvywfh7kdhPLBBfw4423YFswT7mbt01MdnIq8J--BDauBJvGdfAqzqfeAwgQcaIMwargQbIUZv5nvDwCVYKki8P7If7gxoeyWVRyLSjGIOAVqcOlL0n8qM1DOEgr6HPxF-W7L6Gyo~hlva7n7Z3m67RFD-ken8wPmWpLnUGURQSUCjYtA-g__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA', 'C:\Users\franc\Documents\GitHub\BOOSBTV\src\main\resources\static\videos\The Godfather Part II.mp4', 'C:\Users\franc\Documents\GitHub\BOOSBTV\src\main\resources\static\videos\The Godfather Part II.mp4', 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', '03:22:00', 1974, 'Crime, Drama', 'https://dyn1.heritagestatic.com/lf?set=path%5B5%2F0%2F2%2F2%2F5022203%5D%2Csizedata%5B850x600%5D&call=url%5Bfile%3Aproduct.chain%5D', 'corvo');
