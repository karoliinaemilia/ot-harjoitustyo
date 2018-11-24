package mathpuzzles.problem;

import java.util.Random;

public class Problem {
    
    private int number1;
    private int number2;
    private Operation operation;
    private int answer;
    
    
    public Problem generateRandomMathProblem(int minValue, int maxValue) {
        Problem problem = new Problem();
        Random rand = new Random();
        
        int firstNumber = rand.nextInt(maxValue - 1) + minValue;
        int secondNumber = rand.nextInt(maxValue - 1) + minValue;
        
        problem.setNumber1(firstNumber);
        problem.setNumber2(secondNumber);
        
        problem.setOperation(Operation.getRandom());
        
        if (problem.getOperation() == Operation.DIVIDE) {
            if (problem.getNumber1() < problem.getNumber2()) {
                int helper = problem.getNumber1();
                problem.setNumber1(problem.getNumber2());
                problem.setNumber2(helper);
            }
        }
        
        problem.setAnswer(problem.getOperation().result(problem.getNumber1(), problem.getNumber2()));
        
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

    @Override
    public String toString() {
        return number1 + " " + this.getOperation().operator() + " " + number2 + " = ";
    }
    
    
}
