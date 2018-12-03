package mathpuzzles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mathpuzzles.user.User;
import mathpuzzles.user.User;

public class UserDao implements Dao<User, String> {

    private Database database;

    public UserDao(Database database) {
        this.database = database;
    }

    @Override
    public User findOne(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String password = rs.getString("password");

        User u = new User(id, name, username, password);

        rs.close();
        stmt.close();
        conn.close();

        return u;
    }
    
    public User findByUsernameAndPassword(String user, String pass) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");
        stmt.setObject(1, user);
        stmt.setObject(2, pass);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String password = rs.getString("password");

        User u = new User(id, name, username, password);

        rs.close();
        stmt.close();
        conn.close();

        return u;
    }
    
    

    @Override
    public List<User> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User");
        List<User> users = new ArrayList();

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("nimi"), rs.getString("username"), rs.getString("password"));
            users.add(user);
        }

        stmt.close();
        rs.close();

        conn.close();

        return users;
    }

    public User save(User object) throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User " + "(name, username, password) " + "VALUES (?, ?, ?)");
        
        stmt.setString(1, object.getName());
        stmt.setString(2, object.getUsername());
        stmt.setString(3, object.getPassword());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, object.getUsername());
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"));
        
        stmt.close();
        rs.close();
        conn.close();
        return user;
        
    }
    
    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
