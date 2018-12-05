package edu.calvin.cs262.lab09;

/**
 * This class implements a Team Data-Access Object (DAO) class for the Team relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Monopoly database.
 *
 */
public class Game {

    private String name;
    private String location;
    private int id;

    public Game() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public Game(String name) {
        this.name = name;
        this.location = location;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
