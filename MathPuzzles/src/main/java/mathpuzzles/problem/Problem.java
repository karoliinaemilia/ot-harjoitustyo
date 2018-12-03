package mathpuzzles.problem;

import java.util.Random;

public class Problem {

    private int number1;
    private int number2;
    private Operation operation;
    private int answer;
    private int minValue = 1;
    private int maxValue = 20;

    public Problem generateRandomMathProblem(int minValue, int maxValue, Operation operation) {
        Problem problem;
        if (operation == null) {
            problem = generateProblem(minValue, maxValue, Operation.getRandom());
        } else {
            problem = generateProblem(minValue, maxValue, operation);
        }
        return problem;
    }

    public Problem generateProblem(int minValue, int maxValue, Operation operation) {
        Problem problem = setValues(minValue, maxValue);
        problem = generateNumbers(problem);
        problem.setOperation(operation);
        if (problem.getOperation() == Operation.DIVIDE) {
            problem = checkDivision(problem);
        }
        problem.setAnswer(problem.getOperation().result(problem.getNumber1(), problem.getNumber2()));
        return problem;
    }

    public Problem setValues(int minValue, int maxValue) {
        Problem problem = new Problem();

        problem.setMaxValue(maxValue);
        problem.setMinValue(minValue);

        return problem;
    }

    public Problem generateNumbers(Problem problem) {
        Random rand = new Random();

        int firstNumber = rand.nextInt(problem.getMaxValue() - 1) + problem.getMinValue();
        int secondNumber = rand.nextInt(problem.getMaxValue() - 1) + problem.getMinValue();

        problem.setNumber1(firstNumber);
        problem.setNumber2(secondNumber);

        return problem;
    }

    public Problem checkDivision(Problem problem) {
        Random rand = new Random();
        while (true) {
            if (problem.getNumber1() < problem.getNumber2()) {
                int helper = problem.getNumber1();
                problem.setNumber1(problem.getNumber2());
                problem.setNumber2(helper);
            }
            if (problem.getNumber1() % problem.getNumber2() != 0 || problem.getNumber1() == problem.getNumber2()) {
                problem.setNumber1(rand.nextInt(problem.getMaxValue() - 1) + problem.getMinValue());
            } else {
                break;
            }
        }

        return problem;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return number1 + " " + this.getOperation().operator() + " " + number2 + " = ";
    }
}
