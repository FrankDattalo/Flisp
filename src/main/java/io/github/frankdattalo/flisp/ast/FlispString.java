package io.github.frankdattalo.flisp.ast;

public class FlispString implements FlispAtom {

  private final String value;

  public FlispString(String input) {
    this.value = input;
  }

  public String toString() {
    return this.value;
  }
}
