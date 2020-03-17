package com.ajai.interpreter;

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

  private static final Set<Operator> OPERATOR_SET = EnumSet.of(Operator.PLUS, Operator.MINUS, Operator.MULTIPLICATION);

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

  WhiteSpaceSkip skipper = () -> {
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
        skipper.skip();
        continue;
      }

      if (isDigit(charIterator.current())) {
        return new Token(TokenType.INTEGER, numberRepresentation.get());
      }

      if (charIterator.current() == '+') {
        charIterator.next();
        return new Token(TokenType.PLUS, Operator.PLUS.getSymbol());
      }

      if (charIterator.current() == '-') {
        charIterator.next();
        return new Token(TokenType.MINUS, Operator.MINUS.getSymbol());
      }
      
      if (charIterator.current() == '*') {
        charIterator.next();
        return new Token(TokenType.MULTIPLICATION, Operator.MULTIPLICATION.getSymbol());
      }
      
      throw new IllegalStateException("Received unexpected token [" + charIterator.current() + "]");

    }

    return new Token(TokenType.EOF, null);

  };


  Consumer<TokenType> consumeToken = type -> {
    if (currentToken.getType() == type) {
      currentToken = nextToken.get();
    } else {
      throw new IllegalStateException(
          "Received unexpected token of type [" + currentToken.getType() + "]");
    }
  };


  IntSupplier expressionResult = () -> {

    currentToken = nextToken.get();

    String left = currentToken.get();
    consumeToken.accept(TokenType.INTEGER);

    Integer result = Integer.parseInt(left);

    try {

      while (OPERATOR_SET.contains(Operator.getOperator(currentToken.get()))) {

        Operator operator = Operator.getOperator(currentToken.get());

        if (operator == Operator.PLUS) {
          consumeToken.accept(TokenType.PLUS);
          String number = currentToken.get();
          consumeToken.accept(TokenType.INTEGER);
          result += Integer.parseInt(number);
        }

        else if (operator == Operator.MINUS) {
          consumeToken.accept(TokenType.MINUS);
          String number = currentToken.get();
          consumeToken.accept(TokenType.INTEGER);
          result -= Integer.parseInt(number);
        }
        
        else if (operator == Operator.MULTIPLICATION) {
          consumeToken.accept(TokenType.MULTIPLICATION);
          String number = currentToken.get();
          consumeToken.accept(TokenType.INTEGER);
          result *= Integer.parseInt(number);
        }

      }
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Received unexpected token.", e);
    }

    return result;
  };


  @FunctionalInterface
  interface WhiteSpaceSkip {
    void skip();
  }

}
