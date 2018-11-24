
package mathpuzzles.logics;

import java.sql.SQLException;
import mathpuzzles.problem.Problem;
import mathpuzzles.user.User;
import mathpuzzles.user.UserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceTest {
    
    UserDao userDao;
    User user;
    Problem problem;
    Service service;
    
    @Before
    public void setUp() {
        user = new User("Test", "tester", "secre1");
        userDao = mock(UserDao.class);
        service = new Service(userDao);
        problem = mock(Problem.class);
    }
    
    @Test
    public void newUserCanBeCreatedWithUniqueUsername() throws SQLException {
        when(userDao.findOne("tester2")).thenReturn(null);
        
        when(userDao.save(anyObject())).thenReturn(anyObject());
        
        assertEquals(true, service.createUser("Test2", "tester2", "secret2"));
        
        verify(userDao, times(1)).findOne("tester2");
        verify(userDao, times(1)).save(anyObject());
    }
    
    @Test
    public void newUserIsntCreatedWithSameUsername() throws SQLException {
        when(userDao.findOne("tester")).thenReturn(user);
        when(userDao.save(user)).thenReturn(user);
        
        assertEquals(false, service.createUser("Test", "tester", "secret"));
        
        verify(userDao, times(1)).findOne("tester");
        verify(userDao, times(0)).save(user);
       
    }
    
    @Test
    public void ifNoOneISLoggedInCurrentUserIsNull() {
        assertEquals(null, service.getCurrentUser());
    }
    
    @Test
    public void loginWorksIfUserExists() throws SQLException {
        when(userDao.findByUsernameAndPassword("tester", "secret1")).thenReturn(user);
        
        assertEquals(true, service.login("tester", "secret1"));
        assertEquals(user, service.getCurrentUser());
        
        verify(userDao, times(1)).findByUsernameAndPassword("tester", "secret1");
    }
    
    @Test
    public void loginDoesntWorkWithNonexistentUsername() throws SQLException {
        when(userDao.findByUsernameAndPassword("tester", "secret")).thenReturn(null);
        
        assertEquals(false, service.login("tester", "secret1"));
        assertEquals(null, service.getCurrentUser());
    }
    
    
}
