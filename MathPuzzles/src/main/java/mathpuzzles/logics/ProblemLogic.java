package mathpuzzles.logics;

import mathpuzzles.user.User;
import java.sql.SQLException;
import java.util.ArrayList;
import mathpuzzles.problem.Problem;
import mathpuzzles.dao.UserDao;
import mathpuzzles.problem.Operation;
import org.apache.commons.lang3.StringUtils;

public class ProblemLogic {

    private Problem problem = new Problem();

    public String makeProblem(int minValue, int maxValue, Operation operation) {
        this.problem = problem.generateRandomMathProblem(minValue, maxValue, operation);

        return problem.toString();
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(Integer.toString(problem.getAnswer()));
    }

    public String getAnswer() {
        return Integer.toString(problem.getAnswer());
    }
    
    public boolean checkIfValidNumbers(String number1, String number2) {
        if (!StringUtils.isNumeric(number1) || !StringUtils.isNumeric(number2)) {
            return false;
        } else if (Integer.parseInt(number1) > Integer.parseInt(number2)) {
            return false;
        }
        
        return true;
    }
}
