package Interpreter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.ajai.interpreter.Interpreter;

class InterpreterTest {

  @Test
  void testExpression() {
    
    assertEquals(3, new Interpreter("1+2").expression.getAsInt());

    assertEquals(10, new Interpreter("5+5").expression.getAsInt());
    
  }
}
