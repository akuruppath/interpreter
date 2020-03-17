package com.ajai.interpreter;

import static com.ajai.interpreter.operators.Operator.DIVISION;
import static com.ajai.interpreter.operators.Operator.MINUS;
import static com.ajai.interpreter.operators.Operator.MULTIPLICATION;
import static com.ajai.interpreter.operators.Operator.PLUS;
import static com.ajai.interpreter.operators.Operator.getOperator;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static java.text.CharacterIterator.DONE;
import java.text.StringCharacterIterator;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import com.ajai.interpreter.operators.Operator;
import com.ajai.interpreter.token.Token;
import com.ajai.interpreter.token.TokenType;

public class Interpreter {

  private static final Set<Operator> OPERATOR_SET =
      EnumSet.of(PLUS, MINUS, MULTIPLICATION, DIVISION);

  private Token currentToken;
  private StringCharacterIterator charIterator;

  private Interpreter(String text) {
    charIterator = new StringCharacterIterator(text);
  }

  public IntSupplier getResult() {
    return expressionResult;
  }


  public static class InterpreterBuilder {

    private final String text;

    public InterpreterBuilder(String text) {
      this.text = text;
    }

    public Interpreter build() {
      return new Interpreter(text);
    }
  }

  private SkipWhiteSpace skipper = () -> {
    while (charIterator.current() != DONE && isWhitespace(charIterator.current())) {
      charIterator.next();
    }
  };

  private Supplier<String> numberToken = () -> {

    StringBuilder builder = new StringBuilder();

    while (charIterator.current() != DONE && isDigit(charIterator.current())) {
      builder.append(charIterator.current());
      charIterator.next();
    }

    return builder.toString();
  };

  private Supplier<Token> nextToken = () -> {

    while (charIterator.current() != DONE) {

      if (isWhitespace(charIterator.current())) {
        skipper.skip();
        continue;
      }

      if (isDigit(charIterator.current())) {
        return new Token(TokenType.INTEGER, numberToken.get());
      }

      if (charIterator.current() == '+') {
        charIterator.next();
        return new Token(TokenType.PLUS, PLUS.getSymbol());
      }

      if (charIterator.current() == '-') {
        charIterator.next();
        return new Token(TokenType.MINUS, MINUS.getSymbol());
      }

      if (charIterator.current() == '*') {
        charIterator.next();
        return new Token(TokenType.MULTIPLICATION, MULTIPLICATION.getSymbol());
      }

      if (charIterator.current() == '/') {
        charIterator.next();
        return new Token(TokenType.DIVISION, DIVISION.getSymbol());
      }

      throw new IllegalStateException("Received unexpected token [" + charIterator.current() + "]");

    }

    return new Token(TokenType.EOF, null);

  };


  private Consumer<TokenType> consumeToken = type -> {
    if (currentToken.getType() == type) {
      currentToken = nextToken.get();
    } else {
      throw new IllegalStateException(
          "Received unexpected token of type [" + currentToken.getType() + "]");
    }
  };

  private IntSupplier term = () -> {

    String operand = currentToken.get();
    consumeToken.accept(TokenType.INTEGER);
    return Integer.parseInt(operand);
  };

  private IntSupplier expressionResult = () -> {

    currentToken = nextToken.get();
    Integer result = term.getAsInt();

    try {

      while (OPERATOR_SET.contains(getOperator(currentToken.get()))) {

        switch (getOperator(currentToken.get())) {

          case PLUS:
            consumeToken.accept(TokenType.PLUS);
            result += term.getAsInt();
            break;

          case MINUS:
            consumeToken.accept(TokenType.MINUS);
            result -=  term.getAsInt();
            break;

          case MULTIPLICATION:
            consumeToken.accept(TokenType.MULTIPLICATION);
            result *= term.getAsInt();
            break;

          case DIVISION:
            consumeToken.accept(TokenType.DIVISION);
            result /= term.getAsInt();
            break;

        }
      }
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Received unexpected token.", e);
    }

    return result;
  };


  @FunctionalInterface
  interface SkipWhiteSpace {
    void skip();
  }

}
