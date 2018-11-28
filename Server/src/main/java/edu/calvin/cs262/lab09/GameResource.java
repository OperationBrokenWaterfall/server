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
    name = "monopoly",
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
            issuer = "https://securetoken.google.com/YOUR-PROJECT-ID",
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
public class TeamResource {

    /**
     * GET
     * This method gets the full list of players from the Team table. It uses JDBC to
     * establish a DB connection, construct/send a simple SQL query, and process the results.
     *
     * @return JSON-formatted list of player records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path="teams", httpMethod=GET)
    public List<Team> getTeams() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Team> result = new ArrayList<Team>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectGame(statement);
            while (resultSet.next()) {
                Team p = new Team(
                        resultSet.getString(1)
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


    @ApiMethod(path="player/{id}", httpMethod=PUT)
    public  List<Team> putTeams() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            match.setID(id);
            resultSet = selectMatch(id, statement);
            if (resultSet.next()) {
                updateMatch(match, statement);
            } else {
                insertMatch(match, statement);
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return match;
    }
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


}