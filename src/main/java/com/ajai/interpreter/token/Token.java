package com.ajai.interpreter.token;

public class Token {

  private final TokenType type;
  private final String value;

  public Token(TokenType type, String value) {
    this.type = type;
    this.value = value;
  }

  public TokenType getType() {
    return type;
  }

  public String get() {
    return value;
  }

  public String toString() {
    return new StringBuilder().append("Token").append("{").append(type).append("}").append("{")
        .append(value).append("}").toString();
  }

}
