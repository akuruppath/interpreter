package com.ajai.interpreter;

import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import com.ajai.interpreter.token.Token;
import com.ajai.interpreter.token.TokenType;

public class Interpreter {

  private String text;
  private int position;
  private Token currentToken;

  public Interpreter(String text) {
    this.text = text;
  }


  Supplier<Token> nextToken = () -> {

    if (position > this.text.length() - 1) {
      return new Token(TokenType.EOF, null);
    }

    Character currentChar = this.text.charAt(position);


    if (Character.isDigit(currentChar)) {
      position++;
      return new Token(TokenType.INTEGER, currentChar);
    }

    else if (currentChar == '+') {
      position++;
      return new Token(TokenType.PLUS, currentChar);
    }

    throw new IllegalStateException("Received unexpected token [" + currentChar + "]");

  };

  Consumer<TokenType> consumeToken = type -> {
    if (currentToken.getType() == type) {
      currentToken = nextToken.get();
    } else {
      throw new IllegalStateException("Received unexpected token of type [" + type + "]");
    }
  };



  public IntSupplier expression = () -> {

    currentToken = nextToken.get();

    Character left = currentToken.getCurrentChar();
    consumeToken.accept(TokenType.INTEGER);

    Character op = currentToken.getCurrentChar();
    consumeToken.accept(TokenType.PLUS);

    Character right = currentToken.getCurrentChar();
    consumeToken.accept(TokenType.INTEGER);

    switch (op) {

      case '+':
        return Character.getNumericValue(left) + Character.getNumericValue(right);
    }

    throw new IllegalStateException("Encountered invalid operator [" + op + "]");

  };

}
