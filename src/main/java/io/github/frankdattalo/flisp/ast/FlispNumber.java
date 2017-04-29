package io.github.frankdattalo.flisp.ast;

import java.math.BigDecimal;

public class FlispNumber implements FlispAtom {

  private final BigDecimal value;

  public FlispNumber(BigDecimal bigDecimal) {
    this.value = bigDecimal;
  }

  public String toString() {
    return this.value.toPlainString();
  }
}
