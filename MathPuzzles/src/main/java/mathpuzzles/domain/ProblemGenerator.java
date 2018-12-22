package mathpuzzles.domain;

import java.util.Random;

/**
 * Class for generating a Problem for the application
 */
public class ProblemGenerator {

    /**
     * Generates a math problem based on the parameters
     * @param minValue the minimum value of a number in the problem
     * @param maxValue the maximum value of a number in the problem
     * @param operation the operation type
     * @return Problem object generated based on the parameters
     */
    public static Problem generateProblem(int minValue, int maxValue, Operation operation) {
        Problem problem = setValues(minValue, maxValue);
        if (operation == null) {
            problem.setOperation(Operation.getRandom());
        } else {
            problem.setOperation(operation);
        }
        problem = generateNumbers(problem);

        if (problem.getOperation() == Operation.DIVIDE) {
            problem = checkDivision(problem);
        }
        problem.setAnswer(problem.getOperation().result(problem.getNumber1(), problem.getNumber2()));
        return problem;
    }

    /**
     * Sets the minimum and maximum value for a problem
     * @param minValue the minimum value of a number in the problem
     * @param maxValue the maximum value of a number in the problem
     * @return Problem object with minimum and maximum values set
     */
    public static Problem setValues(int minValue, int maxValue) {
        Problem problem = new Problem();

        problem.setMaxValue(maxValue);
        problem.setMinValue(minValue);

        return problem;
    }

    /**
     * Sets the numbers for a problem
     * @param problem The problem for which the numbers are to be set
     * @return The same problem that was given as a parameter with numbers set
     */
    public static Problem generateNumbers(Problem problem) {
        Random rand = new Random();

        int firstNumber = rand.nextInt(problem.getMaxValue() - problem.getMinValue() + 1) + problem.getMinValue();
        int secondNumber = rand.nextInt(problem.getMaxValue() - problem.getMinValue() + 1) + problem.getMinValue();

        problem.setNumber1(firstNumber);
        problem.setNumber2(secondNumber);

        return problem;
    }

    /**
     * Checks if division is valid
     * @param problem The problem for which the division is checked
     * @return Given Problem object with different numbers if division was not valid
     */
    public static Problem checkDivision(Problem problem) {
        Random rand = new Random();
        while (true) {
            if (problem.getNumber1() < problem.getNumber2()) {
                int helper = problem.getNumber1();
                problem.setNumber1(problem.getNumber2());
                problem.setNumber2(helper);
            }
            if (problem.getNumber1() % problem.getNumber2() != 0 || problem.getNumber1() == problem.getNumber2()) {
                problem.setNumber1(rand.nextInt(problem.getMaxValue() - problem.getMinValue() + 1) + problem.getMinValue());
            } else {
                break;
            }
        }

        return problem;
    }
}
