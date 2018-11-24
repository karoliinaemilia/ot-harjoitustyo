package mathpuzzles.user;

import mathpuzzles.user.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User("Test", "tester", "secret1");
    }

    @Test
    public void createdUserExists() {
        assertTrue(user != null);
    }

    @Test
    public void usernameIsCorrect() {
        assertEquals("tester", user.getUsername());
    }

    @Test
    public void passwordIsCorrect() {
        assertEquals("secret1", user.getPassword());
    }

    @Test
    public void nameIsCorrect() {
        assertEquals("Test", user.getName());
    }
    
    @Test
    public void canBeCreatedWithId() {
        user = new User(23, "Test", "tester", "secret");
        
        assertEquals(Integer.toString(23), Integer.toString(user.getId()));
        
    }

}
