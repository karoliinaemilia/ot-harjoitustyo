package mathpuzzles;

import mathpuzzles.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    User user;
    
    public UserTest() {
        user = new User("Test", "tester", "secret1");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createdUserExists() {
        assertTrue(user != null);
    }
    
    @Test
    public void usernameIsCorrect() {
        assertEquals(user.getUsername(), "tester");
    }

}
