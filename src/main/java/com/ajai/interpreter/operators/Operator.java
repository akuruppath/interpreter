package com.ajai.interpreter.operators;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

public enum Operator {

  PLUS("+"), MINUS("-"), MULTIPLICATION("*");

  private static final Map<String, Operator> OPERATOR_MAP = ImmutableMap.of("+", PLUS, "-", MINUS, "*", MULTIPLICATION);

  private String operator;

  private Operator(String operator) {
    this.operator = operator;
  }

  public String getSymbol() {
    return this.operator;
  }

  public static Operator getOperator(String symbol) {
    return OPERATOR_MAP.get(symbol);
  }
}
