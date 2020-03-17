package Interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import com.ajai.interpreter.Interpreter.InterpreterBuilder;

class InterpreterTest {

  @Test
  void testValidSingleDigitExpressions() {

    assertEquals(3, new InterpreterBuilder("1+2").build().getResult().getAsInt());

    assertEquals(10, new InterpreterBuilder("5+5").build().getResult().getAsInt());

    assertEquals(0, new InterpreterBuilder("5-5").build().getResult().getAsInt());

    assertEquals(100, new InterpreterBuilder("10*10").build().getResult().getAsInt());

  }

  @Test
  void testValidMultipleDigitsExpressions() {

    assertEquals(10, new InterpreterBuilder("15-5").build().getResult().getAsInt());

    assertEquals(300, new InterpreterBuilder("100+200").build().getResult().getAsInt());

  }

  @Test
  void testValidExpressionsWithMoreThanTwoOperands() {

    assertEquals(6, new InterpreterBuilder("1+2+3").build().getResult().getAsInt());

    assertEquals(20, new InterpreterBuilder("15-5+10").build().getResult().getAsInt());

    assertEquals(46, new InterpreterBuilder("15 + 5 - 10 + 40 -4").build().getResult().getAsInt());

    assertEquals(25, new InterpreterBuilder("5*5 + 5 -5").build().getResult().getAsInt());
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
