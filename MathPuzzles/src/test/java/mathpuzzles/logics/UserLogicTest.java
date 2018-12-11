
package mathpuzzles.logics;

import java.sql.SQLException;
import mathpuzzles.domain.Problem;
import mathpuzzles.domain.User;
import mathpuzzles.dao.UserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserLogicTest {
    
    UserDao userDao;
    User user;
    UserLogic logic;
    
    @Before
    public void setUp() {
        user = new User("Test", "tester", "secre1");
        userDao = mock(UserDao.class);
        logic = new UserLogic(userDao);
    }
    
    @Test
    public void newUserCanBeCreatedWithUniqueUsername() throws SQLException {
        when(userDao.findOne("tester2")).thenReturn(false);
        
        when(userDao.save(anyObject())).thenReturn(anyObject());
        
        assertEquals(true, logic.createUser("Test2", "tester2", "secret2"));
        
        verify(userDao, times(1)).findOne("tester2");
        verify(userDao, times(1)).save(anyObject());
    }
    
    @Test
    public void newUserIsntCreatedWithSameUsername() throws SQLException {
        when(userDao.findOne("tester")).thenReturn(true);
        when(userDao.save(user)).thenReturn(user);
        
        assertEquals(false, logic.createUser("Test", "tester", "secret"));
        
        verify(userDao, times(1)).findOne("tester");
        verify(userDao, times(0)).save(user);
       
    }
    
    @Test
    public void ifNoOneISLoggedInCurrentUserIsNull() {
        assertEquals(null, logic.getCurrentUser());
    }
    
    @Test
    public void loginWorksIfUserExists() throws SQLException {
        when(userDao.findByUsernameAndPassword("tester", "secret1")).thenReturn(user);
        
        assertEquals(true, logic.login("tester", "secret1"));
        
        
        verify(userDao, times(1)).findByUsernameAndPassword("tester", "secret1");
    }
    
    @Test
    public void loginDoesntWorkWithNonexistentUsername() throws SQLException {
        when(userDao.findByUsernameAndPassword("tester", "secret")).thenReturn(null);
        
        assertEquals(false, logic.login("tester", "secret1"));
        assertEquals(null, logic.getCurrentUser());
    }
    
    @Test
    public void logoutWorks() throws SQLException {
        when(userDao.findByUsernameAndPassword("tester", "secret1")).thenReturn(user);
        logic.login("tester", "secret1");
        assertEquals(user, logic.getCurrentUser());
        
        logic.logout();
        assertEquals(null, logic.getCurrentUser());
    }
    
    @Test
    public void checkIfValidInputsReturnsRightErrorMessage() {
        assertEquals("username or name too short", logic.checkIfValidInputs("nk", "sdf", "s", "s"));
        assertEquals("password too short", logic.checkIfValidInputs("alice", "Alice", "sec", "sec"));
        assertEquals("password and password confirmation do not match", logic.checkIfValidInputs("alice", "Alice", "secret1", "secre1"));
    }
    
    @Test
    public void checkIfValidInputsReturnsNullIfValidInputs() {
        assertEquals(null, logic.checkIfValidInputs("alice", "Alice", "secret1", "secret1"));
    }
}
