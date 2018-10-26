--Drop tables if they exist to recreate the database
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS Team;

--Create table schema
CREATE TABLE Team (
	ID integer PRIMARY KEY,
	TeamName varchar(30),
	Score integer
	);
	
CREATE TABLE Location (
	ID integer PRIMARY KEY,
	LocationName varchar(50),
	Score integer
	);
	
CREATE TABLE Game (
	LocationID integer REFERENCES Location(ID),
	TeamID integer REFERENCES Team(ID)
	);
	
--Grant SELECT access to the public - Maybe won't use this for the app?
GRANT SELECT ON Team TO PUBLIC;
GRANT SELECT ON Location TO PUBLIC;
GRANT SELECT ON Game TO PUBLIC;

--Sample data
INSERT INTO Team VALUES (1, 'A', 50);
INSERT INTO Team VALUES (2, 'B', 20);

INSERT INTO Location VALUES (1, 'DV atrium window', 30);
INSERT INTO Location VALUES (2, 'VanderLinden office', 20);

INSERT INTO Game VALUES (1, 1);
INSERT INTO Game VALUES (1, 2);
INSERT INTO Game VALUES (2, 2);