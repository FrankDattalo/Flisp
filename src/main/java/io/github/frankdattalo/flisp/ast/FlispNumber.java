package io.github.frankdattalo.flisp.ast;

import java.math.BigDecimal;

public class FlispNumber implements FlispAtom {

  private final BigDecimal value;

  public FlispNumber(BigDecimal bigDecimal) {
    this.value = bigDecimal;
  }

  public BigDecimal getValue() {
      return value;
  }

  public String toString() {
    return this.value.toPlainString();
  }

  public boolean equals(Object other) {
      if(other == null) return false;
      if(!(other instanceof FlispNumber)) return false;

      return ((FlispNumber) other).getValue().equals(this.getValue());
  }
}
