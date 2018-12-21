
package mathpuzzles.dao;

import mathpuzzles.domain.User;
import mathpuzzles.dao.UserDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mathpuzzles.dao.Database;
import mathpuzzles.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDaoTest {
    
    Database db;
    UserDao userDao;
    
    @Before
    public void setUp() throws SQLException {
        db = new Database("jdbc:sqlite:databaseForTests.db");
        userDao = new UserDao(db);
        db.initializeDatabase();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User;");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO User (name, username, password) VALUES ('Test', 'tester', 'secret1')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO User (name, username, password) VALUES ('John', 'john', 'secret2')");
        stmt.execute();
    }
    
    @Test
    public void oneCanBeFoundFromDatabaseByUsername() throws SQLException {
        assertEquals(userDao.findOne("john").getName(), "John");
    }
    
    @Test
    public void oneCanBeFounFromDatabaseByUsernameAndPassword() throws SQLException {
        User user = userDao.findByUsernameAndPassword("john", "secret2");
        assertEquals("John", user.getName());
    }
    
    @Test
    public void nonExistingUserCannotBeFound() throws SQLException {
        assertEquals(userDao.findOne("benjamin"), null);
    }
    
    @Test
    public void nonExistingUserCannotBeFoundByUsernameAndPassword() throws SQLException {
        User user = userDao.findByUsernameAndPassword("elizabeth", "supersecret");
        assertEquals(null, user);
    }
    
    @Test
    public void userIsSavedToDatabase() throws SQLException {
        User newUser = new User("Alice", "alice", "secretest");
        
        userDao.save(newUser);
        
        assertEquals(userDao.findOne("alice").getName(), "Alice");
    }
    
    
    @Test
    public void findAllReturnsAllUsers() throws SQLException {
        List<User> users = userDao.findAll();
        
        boolean foundJohn = false;
        boolean foundTest = false;
        
        for (int i = 0; i< users.size(); i++) {
            if (users.get(i).getName().equals("John")){
                foundJohn = true;
            } else if(users.get(i).getName().equals("Test")) {
                foundTest = true;
            }
        }
        
        assertTrue(foundJohn);
        assertTrue(foundTest);
    }
}
