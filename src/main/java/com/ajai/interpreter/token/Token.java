package com.ajai.interpreter.token;

public class Token {

  private final TokenType type;
  private final Character currentChar;

  public Token(TokenType type, Character currentChar) {
    this.type = type;
    this.currentChar = currentChar;
  }

  public TokenType getType() {
    return type;
  }

  public Character getCurrentChar() {
    return currentChar;
  }

}
