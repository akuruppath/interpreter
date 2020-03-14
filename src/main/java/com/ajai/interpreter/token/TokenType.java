package com.ajai.interpreter.token;

public enum TokenType {

  INTEGER("INTEGER"), PLUS("PLUS"), EOF("EOF");

  private String type;

  private TokenType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.getType();
  }

  public TokenType getToken(String type) {
    return TokenType.valueOf(type);
  }
}