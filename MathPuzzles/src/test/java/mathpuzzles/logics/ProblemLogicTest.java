
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
    public void checkIfValidNumbersReturnsNullWithValidStrings() {
        assertEquals(null, logic.checkIfValidNumbers("2", "45"));
    }
    
    @Test
    public void checkIfValidNumbersReturnsCorrectStringWithInvalidStrings() {
        assertEquals("The minimum and maximum should be numbers", logic.checkIfValidNumbers("2d", "gf"));
        assertEquals("The minimum should be smaller than the maximum", logic.checkIfValidNumbers("122", "12"));
        assertEquals("The numbers should be atleast 20 apart", logic.checkIfValidNumbers("23", "24"));
        assertEquals("One of the numbers is too high", logic.checkIfValidNumbers("986543456", "876545678"));
    }
    
    @Test
    public void getOperationFromComboBoxWorksCorrectly() {
        assertEquals(Operation.DIVIDE, logic.getOperationFromComboBox("DIVISION"));
        assertEquals(Operation.PLUS, logic.getOperationFromComboBox("ADDITION"));
        assertEquals(Operation.MINUS, logic.getOperationFromComboBox("SUBTRACTION"));
        assertEquals(Operation.MULTIPLY, logic.getOperationFromComboBox("MULTIPLICTION"));
        assertEquals(null, logic.getOperationFromComboBox("ALL"));
    }
}
