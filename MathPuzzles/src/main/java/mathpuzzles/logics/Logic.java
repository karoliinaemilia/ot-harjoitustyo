
package mathpuzzles.logics;

import mathpuzzles.user.User;
import java.sql.SQLException;
import java.util.ArrayList;
import mathpuzzles.problem.Problem;
import mathpuzzles.user.UserDao;

public class Service {
    
    private UserDao userDao;
    private User currentUser;
    private Problem problem = new Problem();
    
    public Service(UserDao userDao) {
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
    
    public String makeProblem() {
        this.problem = problem.generateRandomMathProblem(2, 20);
        
        return problem.toString();
    }
    
    public boolean checkAnswer(String answer) {
        return answer.equals(Integer.toString(problem.getAnswer()));
    }
    
    public String getAnswer() {
        return Integer.toString(problem.getAnswer());
    }
}
