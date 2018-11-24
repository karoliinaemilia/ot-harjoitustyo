
package mathpuzzles.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mathpuzzles.database.Database;
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
    public void setUp() {
        db = new Database("jdbc:sqlite:databaseForTests.db");
        userDao = new UserDao(db);
    }
    
    @Test
    public void oneCanBeFoundFromDatabaseByUsername() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (name, username, password) VALUES ('Test', 'tester', 'secret1')");
        stmt.execute();
        
        User user = userDao.findOne("tester");
        
        assertEquals("Test", user.getName());
        assertEquals("tester", user.getUsername());
        assertEquals("secret1", user.getPassword());
    }
}
