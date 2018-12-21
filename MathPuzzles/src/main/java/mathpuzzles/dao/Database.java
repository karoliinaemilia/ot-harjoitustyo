package mathpuzzles.dao;

import java.sql.*;

/**
 * Class for connecting to a database and managing it
 */
public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    /**
     * Tries to get a connection to the database
     * @return Connection object 
     * @throws SQLException if something fails at database level
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
        
    }
    
    /**
     * Initializes the database with statement from method createTableUser()
     */
    public void initializeDatabase() {
        try {
            Connection conn = DriverManager.getConnection(databaseAddress);
            Statement stmt = conn.createStatement();
            stmt.execute(createTableUser());
            stmt = conn.createStatement();
            stmt.execute(createTableRecord());
        } catch (Exception r) {
            
        }
    }
    
    /**
     * Statement for User table creation
     * @return String for user table creation
     */
    public String createTableUser() {
        return "CREATE TABLE IF NOT EXISTS"
                + " User("
                + " id integer PRIMARY KEY,"
                + " name varchar(255),"
                + " username varchar(20),"
                + " password varchar(200));";
    }
    
    /**
     * Statement for Record table creation
     * @return String for record table creation
     */
    public String createTableRecord() {
        return "CREATE TABLE IF NOT EXISTS"
                + " Record("
                + " id integer PRIMARY KEY,"
                + " time varchar(20),"
                + " solved integer,"
                + " username varchar(20));";
    }
    
}
