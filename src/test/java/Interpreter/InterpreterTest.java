package Interpreter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.ajai.interpreter.Interpreter;
import com.ajai.interpreter.Interpreter.InterpreterBuilder;

class InterpreterTest {

  @Test
  void testValidExpression() {

    Interpreter interpreter = new InterpreterBuilder("1+2").build();
    
    assertEquals(3, interpreter.getResult().getAsInt());
    
    Interpreter interpreter2 = new InterpreterBuilder("5+5").build();

    assertEquals(10, interpreter2.getResult().getAsInt());

  }

  @Test
  void testExpressionWithWhiteSpace() {
    assertThrows(IllegalStateException.class, () -> new InterpreterBuilder("1 +2").build().getResult().getAsInt());
  }

  @Test
  void testExpressionWithNonPlusOperator() {
    assertThrows(IllegalStateException.class, () -> new InterpreterBuilder("1-2").build().getResult().getAsInt());
  }

}
