package mathpuzzles.logics;

import mathpuzzles.domain.Problem;
import mathpuzzles.domain.Operation;
import mathpuzzles.domain.ProblemGenerator;
import org.apache.commons.lang3.StringUtils;

/**
 * The logic for problems
 */
public class ProblemLogic {

    private Problem problem = new Problem();

    /**
     * Returns a problem for the gui to show
     * @param minValue minimum value for number
     * @param maxValue maximum value for number
     * @param operation operation type
     * @return String form of problem
     */
    public String makeProblem(int minValue, int maxValue, Operation operation) {
        this.problem = ProblemGenerator.generateProblem(minValue, maxValue, operation);

        return problem.toString();
    }

    /**
     * Checks if given answer matches the correct one
     * @param answer the given answer
     * @return true if matches the answer for the problem false if doesn't
     */
    public boolean checkAnswer(String answer) {
        return answer.equals(Integer.toString(problem.getAnswer()));
    }

    public String getAnswer() {
        return Integer.toString(problem.getAnswer());
    }
    
    /**
     * Checks if the given string form numbers are numbers
     * @param number1 String form of first number
     * @param number2 String form of second number
     * @return true if given strings are numbers
     */
    public String checkIfValidNumbers(String number1, String number2) {
        if (!StringUtils.isNumeric(number1) || !StringUtils.isNumeric(number2)) {
            return "The minimum and maximum should be numbers";
        } else if (Integer.parseInt(number1) > Integer.parseInt(number2)) {
            return "The minimum should be smaller than the maximum";
        } else if (Math.abs(Integer.parseInt(number1) - Integer.parseInt(number2)) < 10) {
            return "The numbers should be atleast 10 apart";
        }
        
        return null;
    }
    
    public Operation getOperationFromComboBox(String value) {
        switch (value) {
            case "ALL":
                return null;
            case "DIVISION":
                return Operation.DIVIDE;
            case "ADDITION":
                return Operation.PLUS;
            case "SUBTRACTION":
                return Operation.MINUS;
            default:
                return Operation.MULTIPLY;
        }
    }
}
