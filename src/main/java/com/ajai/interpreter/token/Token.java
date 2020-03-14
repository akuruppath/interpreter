package com.ajai.interpreter.token;

public enum Token {

  INTEGER("INTEGER"), PLUS("PLUS"), EOF("EOF");

  private String type;

  private Token(String type) {
    this.type = type;
  }

  public String getType() {
    return this.getType();
  }

  public Token getToken(String type) {
    return Token.valueOf(type);
  }
}
