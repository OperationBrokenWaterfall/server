package edu.calvin.cs262.lab09;

/**
 * This class implements a Team Data-Access Object (DAO) class for the Team relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Monopoly database.
 *
 */
public class Team {

    private String name;


    public Team() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }
    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
