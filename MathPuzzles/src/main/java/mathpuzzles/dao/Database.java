package mathpuzzles.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
    public void intializeDatabase() {
        try {
            Connection conn = DriverManager.getConnection(databaseAddress);
            Statement stmt = conn.createStatement();
            stmt.execute(createTableUser());
        } catch (Exception r) {
            
        }
    }
    
    private String createTableUser() {
        return "CREATE TABLE IF NOT EXISTS"
                + " User("
                + " id integer PRIMARY KEY,"
                + " name varchar(255),"
                + " username varchar(20),"
                + " password varchar(200));";
    }
    
}
