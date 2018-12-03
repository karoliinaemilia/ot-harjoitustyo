package mathpuzzles.logics;

import java.sql.SQLException;
import mathpuzzles.dao.UserDao;
import mathpuzzles.user.User;

public class UserLogic {

    private UserDao userDao;
    private User currentUser;

    public UserLogic(UserDao userDao) {
        this.userDao = userDao;
        this.currentUser = null;
    }

    public boolean createUser(String name, String username, String password) throws SQLException {
        if (userDao.findOne(username) != null) {
            return false;
        }

        User user = new User(name, username, password);

        try {
            userDao.save(user);
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

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
