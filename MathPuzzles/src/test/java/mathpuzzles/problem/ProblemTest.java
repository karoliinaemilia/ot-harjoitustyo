/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathpuzzles.problem;

import mathpuzzles.problem.Operation;
import mathpuzzles.problem.Problem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProblemTest {
    
    Problem problem = new Problem();

    @Before
    public void setUp() {
        problem = problem.generateRandomMathProblem(2, 20);
    }

    
    @Test
    public void randomProblemGetsGeneratedWithAnOperation() {
        
        assertTrue(problem.getOperation().equals(Operation.PLUS)
                || problem.getOperation().equals(Operation.MINUS)
                || problem.getOperation().equals(Operation.MULTIPLY)
                || problem.getOperation().equals(Operation.DIVIDE));
    }

    @Test
    public void twoNumbersAreGeneratedThatAreWithinLimits() {
        assertTrue(problem.getNumber1() < 21 && problem.getNumber1() > 1);
        assertTrue(problem.getNumber2() < 21 && problem.getNumber2() > 1);
    }
    
    @Test
    public void toStringIsCorrect() {
        assertEquals(problem.getNumber1() + " " + problem.getOperation().operator() + " " + problem.getNumber2() + " = ", problem.toString());
    }
     
}
