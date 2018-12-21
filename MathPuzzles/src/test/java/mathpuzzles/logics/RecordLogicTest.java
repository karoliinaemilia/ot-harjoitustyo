package mathpuzzles.logics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mathpuzzles.dao.RecordDao;
import mathpuzzles.domain.Record;
import mathpuzzles.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecordLogicTest {
    
    RecordDao recordDao;
    User user;
    Record record;
    RecordLogic logic;
    List<Record> records;
    
    @Before
    public void setUp() {
        records = new ArrayList<>();
        user = new User("Test", "tester", "secre1");
        record = new Record("60", 3, user);
        records.add(record);
        recordDao = mock(RecordDao.class);
        logic = new RecordLogic(recordDao);
    }
    
    @Test
    public void amountIsIncrementedCorrectly() {
        logic.incrementAmount();
        assertEquals(1, logic.getAmountSolved());
    }
    
    @Test
    public void settingARecordSetsAmountSolvedToZero() throws SQLException {
        logic.incrementAmount();
        logic.incrementAmount();
        when(recordDao.findOneForUserWithTimeAndSolved(user, "60", 2)).thenReturn(false);
        logic.setCurrentUser(user);
        logic.setRecord("60");
        
        assertEquals(0, logic.getAmountSolved());
    }
    
    @Test
    public void nothingIsReturnedIfDatabaseDoesntHaveRecords() throws SQLException {
        List<Record> empty = new ArrayList<>();
        when(recordDao.findAllForTime("60")).thenReturn(empty);
        List<String> stringRecords = logic.getRecords("60");
        assertTrue(stringRecords.isEmpty());
        
    }
    
    @Test
    public void whenDatabaseHasOneRecordForTimeItIsReturned() throws SQLException {
        when(recordDao.findAllForTime("60")).thenReturn(records);
        List<String> stringRecords = logic.getRecords("60");
        assertEquals("Test   3", stringRecords.get(0));
    }
    
    @Test
    public void recordsAreSortedToCorrectOrder() throws SQLException {
        record = new Record("60", 14, user);
        records.add(record);
        record = new Record("60", 3, user);
        records.add(record);
        when(recordDao.findAllForTime("60")).thenReturn(records);
        
        List<String> stringRecords = logic.getRecords("60");
        assertEquals("Test   14", stringRecords.get(0));
    }
    
}
