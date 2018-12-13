package edu.calvin.cs262.lab09;

import com.google.api.server.spi.config.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.PUT;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.DELETE;

/**
 * This Java annotation specifies the general configuration of the Google Cloud endpoint API.
 * The name and version are used in the URL: https://PROJECT_ID.appspot.com/monopoly/v1/ENDPOINT.
 * The namespace specifies the Java package in which to find the API implementation.
 * The issuers specifies boilerplate security features that we won't address in this course.
 *
 * You should configure the name and namespace appropriately.
 */
@Api(
    name = "questapp",
    version = "v1",
    namespace =
    @ApiNamespace(
        ownerDomain = "lab09.cs262.calvin.edu",
        ownerName = "lab09.cs262.calvin.edu",
        packagePath = ""
    ),
    issuers = {
        @ApiIssuer(
            name = "firebase",
            issuer = "https://securetoken.google.com/cs262-teamb-fall2018",
            jwksUri =
                "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system"
                    + ".gserviceaccount.com"
        )
    }
)

/**
 * This class implements a RESTful service for the player table of the monopoly database.
 * Only the player relation is supported, not the game or playergame relations.
 */
public class GameResource {

    /**
     * GET
     * This method gets the full list of players from the Team table. It uses JDBC to
     * establish a DB connection, construct/send a simple SQL query, and process the results.
     *
     * @return JSON-formatted list of player records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path="game", httpMethod=GET)
    public List<Game> getGame() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Game> result = new ArrayList<Game>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectGame(statement);
            while (resultSet.next()) {
                Game p = new Game(

                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)

                );
                result.add(p);
            }
        } catch (SQLException e) {
            throw(e);
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return result;
    }

    /**
     * GET
     * This method gets the full list of players from the Team table. It uses JDBC to
     * establish a DB connection, construct/send a simple SQL query, and process the results.
     *
     * @return JSON-formatted list of player records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path="Teamname", httpMethod=GET)
    public List<Game> getTeamname(Game game) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Game> result = new ArrayList<Game>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectTeamname(game.getName(), statement);
            while (resultSet.next()) {
                Game p = new Game(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                result.add(p);
            }
        } catch (SQLException e) {
            throw(e);
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return result;
    }



    /**
     * @param question a JSON representation of the question to be created
     * @return new question entity with a system-generated ID
     * @throws SQLException
     */
    @ApiMethod(path="gamePost", httpMethod=POST)
    public Game postGame(Game game) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(ID) FROM GAME");
            if (resultSet.next()) {
                game.setId(resultSet.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique ID...");
            }
            insertGame(game, statement);
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) { resultSet.close(); }
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        }
        return game;
    }

    /** SQL Utility Functions *********************************************/

    /*
     * This function gets the player with the given id using the given JDBC statement.
     */
    private ResultSet selectGame(Statement statement) throws SQLException {
        return statement.executeQuery(
                "SELECT * FROM Game"
        );
    }

    /*
     * This function inserts the given match using the given JDBC statement.
     */
    private void insertGame(Game game, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO Game (LocationID, TeamName) VALUES ('%d', '%d', '%s')",
                        game.getId(),
                        game.getLocation(),
                        game.getName()
                )
        );
    }

    private ResultSet selectTeamname(String name, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT * FROM Game WHERE Game.name = '%s'", name)
        );
    }
}
