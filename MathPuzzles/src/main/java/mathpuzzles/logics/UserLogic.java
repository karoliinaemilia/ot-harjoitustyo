package mathpuzzles.logics;

import java.sql.SQLException;
import mathpuzzles.dao.UserDao;
import mathpuzzles.domain.User;

/**
 * The logic for users
 */
public class UserLogic {

    private UserDao userDao;
    private User currentUser;

    public UserLogic(UserDao userDao) {
        this.userDao = userDao;
        this.currentUser = null;
    }

    /**
     * Creates a new user
     * @param name name for new user
     * @param username username for new user
     * @param password password for new user
     * @return true if user is created
     * @throws SQLException if something fails at database level
     */
    public boolean createUser(String name, String username, String password) throws SQLException {
        if (userDao.findOne(username) != null) {
            return false;
        }

        User user = new User(name, username, password);

        try {
            userDao.save(user);
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if username and password are correct and can be found in the database
     * @param username the given username
     * @param password the given password
     * @return true if user can be found in database
     * @throws SQLException if something fails at database level
     */
    public boolean login(String username, String password) throws SQLException {
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            return false;
        }

        currentUser = user;

        return true;
    }

    public void logout() {
        currentUser = null;
    }

    /**
     * Checks if given inputs are valid
     * @param username inputted username
     * @param name inputted name
     * @param password inputted password
     * @param confirmation inputted password confirmation
     * @return null if inputs are valid and String error message if not
     */
    public String checkIfValidInputs(String username, String name, String password, String confirmation) {
        if (username.length() < 3 || name.length() < 2) {
            return "username or name too short";
        } else if (password.length() < 6) {
            return "password too short";
        } else if (!password.equals(confirmation)) {
            return "password and password confirmation do not match";
        }
        return null;
    }
}
