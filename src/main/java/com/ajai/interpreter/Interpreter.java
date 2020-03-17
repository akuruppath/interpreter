package com.ajai.interpreter;

import static java.text.CharacterIterator.DONE;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

import java.text.StringCharacterIterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import com.ajai.interpreter.token.Token;
import com.ajai.interpreter.token.TokenType;

public class Interpreter {

  private static final String MINUS_OPERATOR = "-";
  private static final String PLUS_OPERATOR = "+";
  
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

  SkipWhiteSpace whiteSpaceSkipper = () -> {

    while (charIterator.current() != DONE && isWhitespace(charIterator.current())) {
      charIterator.next();
    }
  };

  Supplier<String> numberRepresentation = () -> {

    StringBuilder builder = new StringBuilder();

    while (charIterator.current() != DONE && isDigit(charIterator.current())) {
      builder.append(charIterator.current());
      charIterator.next();
    }

    return builder.toString();
  };

  Supplier<Token> nextToken = () -> {

    while (charIterator.current() != DONE) {

      if (isWhitespace(charIterator.current())) {
        whiteSpaceSkipper.skip();
        continue;
      }

      if (isDigit(charIterator.current())) {
        return new Token(TokenType.INTEGER, numberRepresentation.get());
      }

      if (charIterator.current() == '+') {
        charIterator.next();
        return new Token(TokenType.PLUS, PLUS_OPERATOR);
      }

      if (charIterator.current() == '-') {
        charIterator.next();
        return new Token(TokenType.MINUS, MINUS_OPERATOR);
      }
      throw new IllegalStateException("Received unexpected token [" + charIterator.current() + "]");

    }

    return new Token(TokenType.EOF, null);

  };


  Consumer<TokenType> consumeToken = type -> {
    if (currentToken.getType() == type) {
      currentToken = nextToken.get();
    } else {
      throw new IllegalStateException("Received unexpected token of type [" + currentToken.getType() + "]");
    }
  };


  IntSupplier expressionResult = () -> {

    currentToken = nextToken.get();

    String left = currentToken.get();
    consumeToken.accept(TokenType.INTEGER);

    String operator = currentToken.get();
    consumeToken.accept(Objects.equals(operator, PLUS_OPERATOR) ? TokenType.PLUS : TokenType.MINUS);

    String right = currentToken.get();
    consumeToken.accept(TokenType.INTEGER);

    Integer leftOperand = Integer.parseInt(left);
    Integer rightOperand = Integer.parseInt(right);

    return Objects.equals(operator, PLUS_OPERATOR) ? leftOperand + rightOperand
        : leftOperand - rightOperand;
  };

  @FunctionalInterface
  interface SkipWhiteSpace {
    void skip();
  }

}
