
package mathpuzzles.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProblemTest {
    
    Problem problem = new Problem();

    @Before
    public void setUp() {
        problem = ProblemGenerator.generateProblem(2, 20, null);
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
