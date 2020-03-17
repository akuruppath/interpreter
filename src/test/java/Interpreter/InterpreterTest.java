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

    Interpreter interpreter3 = new InterpreterBuilder("5-5").build();

    assertEquals(0, interpreter3.getResult().getAsInt());

    Interpreter interpreter4 = new InterpreterBuilder("15-5").build();

    assertEquals(10, interpreter4.getResult().getAsInt());

    Interpreter interpreter5 = new InterpreterBuilder("100+200").build();

    assertEquals(300, interpreter5.getResult().getAsInt());

  }

  @Test
  void testExpressionWithWhiteSpace() {
    
    assertEquals(3, new InterpreterBuilder("1 +2").build().getResult().getAsInt());

    assertEquals(1, new InterpreterBuilder("3- 2").build().getResult().getAsInt());

    assertEquals(18, new InterpreterBuilder("14 + 4").build().getResult().getAsInt());
    
  }

  @Test
  void testExpressionWithNonPlusOrMinusOperator() {
    assertThrows(IllegalStateException.class,
        () -> new InterpreterBuilder("1%2").build().getResult().getAsInt());
  }

  @Test
  void testExpressionWithOneOperand() {
    assertThrows(IllegalStateException.class,
        () -> new InterpreterBuilder("14 + ").build().getResult().getAsInt());
    
    assertThrows(IllegalStateException.class,
        () -> new InterpreterBuilder(" + 3").build().getResult().getAsInt());
    
  }
  
  @Test
  void testExpressionWithJustOperator() {
    assertThrows(IllegalStateException.class,
        () -> new InterpreterBuilder("+").build().getResult().getAsInt());
  }

}
