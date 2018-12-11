package mathpuzzles.domain;

import java.util.Random;

/**
 * Represents the problem that can be solved in application
 */
public class Problem {

    private int number1;
    private int number2;
    private Operation operation;
    private int answer;
    private int minValue = 1;
    private int maxValue = 20;

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
