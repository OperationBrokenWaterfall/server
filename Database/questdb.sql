--Drop tables if they exist to recreate the database
DROP TABLE IF EXISTS Game;

--Create table schema

CREATE TABLE Game (
	ID serial PRIMARY KEY,
	LocationID varchar(10),
	TeamName varchar(30)
	);
	
--Grant SELECT access to the public - Maybe won't use this for the app?
GRANT SELECT ON Game TO PUBLIC;

--Sample values
----------------------------------------------------------------
--INSERT INTO Game (LocationID, TeamName) VALUES ('a','team a');
--INSERT INTO Game (LocationID, TeamName) VALUES ('ab','team a');
--INSERT INTO Game (LocationID, TeamName) VALUES ('abc','team b');
--INSERT INTO Game (LocationID, TeamName) VALUES ('abcd','team b');
--INSERT INTO Game (LocationID, TeamName) VALUES ('a','team c');
--INSERT INTO Game (LocationID, TeamName) VALUES ('ab','team c');