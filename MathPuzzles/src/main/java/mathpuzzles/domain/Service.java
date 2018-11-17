
package mathpuzzles.domain;

import java.sql.SQLException;
import mathpuzzles.database.UserDao;

public class Service {
    
    private UserDao userDao;
    private User loggedIn;
    
    public Service(UserDao userDao) {
        this.userDao = userDao;
        this.loggedIn = null;
    }
    
    public boolean createUser(String name, String username, String password) throws SQLException {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(name, username, password);
        try {
            userDao.save(user);
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

    public User getLoggedIn() {
        return loggedIn;
    }
    

    
    public boolean login(String username, String password) throws SQLException {
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            return false;
        }
        
        loggedIn = user;
        
        return true;
    }
}
