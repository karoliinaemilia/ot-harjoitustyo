
package mathpuzzles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mathpuzzles.domain.Record;
import mathpuzzles.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecordDaoTest {
    
    Database db;
    RecordDao recordDao;
    UserDao userDao;
    User user;
    
    @Before
    public void setUp() throws SQLException {
        db = new Database("jdbc:sqlite:databaseForTests.db");
        userDao = new UserDao(db);
        recordDao = new RecordDao(db, userDao);
        Connection conn = db.getConnection();
        db.initializeDatabase();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User;");
        stmt.execute();
        stmt =  conn.prepareStatement("DELETE FROM Record");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO User (name, username, password) VALUES ('Test', 'tester', 'secret1')");
        stmt.execute();
        user = new User("Test", "tester", "secret1");
        stmt = conn.prepareStatement("INSERT INTO Record (time, solved, username) VALUES ('60', 4, 'tester')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO Record (time, solved, username) VALUES ('60', 23, 'tester')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO Record (time, solved, username) VALUES ('30', 2, 'tester')");
        stmt.execute();
    }
    
    @Test
    public void findOneReturnsCorrectRecord() throws SQLException {
        assertEquals((int) 4, (int) recordDao.findOne(1).getSolved());
    }
    
    @Test
    public void allAreReturned() throws SQLException {
        List<Record> records = recordDao.findAll();
        
        assertEquals(3, records.size());
        assertEquals((int) 4, (int) records.get(0).getSolved());
        assertEquals((int) 23, (int) records.get(1).getSolved());
        assertEquals((int) 2, (int) records.get(2).getSolved());
    }
    
    @Test
    public void correctRecordsReturnedForTime() throws SQLException {
        List<Record> records = recordDao.findAllForTime("30");
        
        assertEquals(1, records.size());
        assertEquals((int) 2, (int) records.get(0).getSolved());
    }
    
    @Test
    public void oneCanBeFoundWIthUserSolvedAndTime() throws SQLException {
        assertTrue(recordDao.findOneForUserWithTimeAndSolved(user, "60", 4));
    }
    
    @Test
    public void recordCanBeSaved() throws SQLException {
        recordDao.save(new Record("60", 8, user));
        
        assertTrue(recordDao.findOneForUserWithTimeAndSolved(user, "60", 8));
    }
    
    
}