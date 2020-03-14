package Interpreter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.ajai.interpreter.Interpreter;

class InterpreterTest {

  @Test
  void testValidExpression() {

    assertEquals(3, new Interpreter("1+2").expression.getAsInt());

    assertEquals(10, new Interpreter("5+5").expression.getAsInt());

  }

  @Test
  void testExpressionWithWhiteSpace() {

    assertThrows(IllegalStateException.class,
        () -> new Interpreter("1 +2").expression.getAsInt());
  }

  @Test
  void testExpressionWithNonPlusOperator() {
    assertThrows(IllegalStateException.class,
        () -> new Interpreter("1-2").expression.getAsInt());
  }

}
