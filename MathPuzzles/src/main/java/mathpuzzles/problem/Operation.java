package mathpuzzles.problem;

import java.util.Random;

public enum Operation {
    DIVIDE, MULTIPLY, PLUS, MINUS, POWER;

    public int result(int number1, int number2) {
        switch (this) {
            case DIVIDE:
                return Math.round(number1 / number2);
            case MULTIPLY:
                return number1 * number2;
            case POWER:
                return (int) Math.pow((double) number1, (double) number2);
            case PLUS:
                return number1 + number2;
            case MINUS:
                return number1 - number2;
        }
        return number1 + number2;
    }

    public String operator() {
        switch (this) {
            case DIVIDE:
                return "/";
            case MULTIPLY:
                return "*";
            case POWER:
                return "^";
            case PLUS:
                return "+";
            case MINUS:
                return "-";
        }

        return "+";
    }

    public static Operation getRandom() {
        int random = new Random().nextInt(Operation.values().length - 1);
        return Operation.values()[random];
    }
}
