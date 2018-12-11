
package mathpuzzles.logics;

import mathpuzzles.domain.Operation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProblemLogicTest {
    
    ProblemLogic logic = new ProblemLogic();
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void problemIsMadeWithNumbersThatAreWithinParameters() {
        String problem = logic.makeProblem(2, 20, null);
        String[] parts = problem.split(" ");
        assertEquals(true, Integer.parseInt(parts[0]) < 21 && Integer.parseInt(parts[2]) < 21);
        assertEquals(true, Integer.parseInt(parts[0]) > 1 && Integer.parseInt(parts[2]) > 1);
    }
    
    @Test
    public void problemIsMadeWithRightOperation() {
        String problem = logic.makeProblem(2, 20, Operation.PLUS);
        String[] parts = problem.split(" ");
        
        assertEquals(true, parts[1].equals("+"));
    }
    
    @Test
    public void rightAnswerIsReturned() {
        String problem = logic.makeProblem(2, 20, Operation.PLUS);
        String[] parts = problem.split(" ");
        
        assertEquals("" + (Integer.parseInt(parts[0]) + Integer.parseInt(parts[2])), logic.getAnswer());
    }
    
    @Test
    public void answerIsCheckedCorrectly() {
        String problem = logic.makeProblem(2, 20, Operation.PLUS);
        String[] parts = problem.split(" ");
        
        assertEquals(true, logic.checkAnswer("" + (Integer.parseInt(parts[0]) + Integer.parseInt(parts[2]))));
    }
    
    @Test
    public void checkIfValidNumbersReturnsTrueWithValidStrings() {
        assertEquals(true, logic.checkIfValidNumbers("2", "45"));
    }
    
    @Test
    public void checkIfValidNumbersReturnsFalseWithInvalidStrings() {
        assertEquals(false, logic.checkIfValidNumbers("2d", "gf"));
        assertEquals(false, logic.checkIfValidNumbers("122", "12"));
    }
    
}
