--Drop tables if they exist to recreate the database
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS Team;

--Create table schema
CREATE TABLE Team (
	TeamName varchar(30) PRIMARY KEY
	);
	
CREATE TABLE Location (
	ID integer PRIMARY KEY,
	LocationName varchar(50)
	);
	
CREATE TABLE Game (
	LocationID integer REFERENCES Location(ID),
	TeamID varchar(30) REFERENCES Team(ID)
	);
	
--Grant SELECT access to the public - Maybe won't use this for the app?
GRANT SELECT ON Team TO PUBLIC;
GRANT SELECT ON Location TO PUBLIC;
GRANT SELECT ON Game TO PUBLIC;

--Sample data
INSERT INTO Team VALUES ('A');
INSERT INTO Team VALUES ('B');

INSERT INTO Location VALUES (1, 'DV atrium window');
INSERT INTO Location VALUES (2, 'VanderLinden office');

INSERT INTO Game VALUES (1, 'A');
INSERT INTO Game VALUES (1, 'B');
INSERT INTO Game VALUES (2, 'A');