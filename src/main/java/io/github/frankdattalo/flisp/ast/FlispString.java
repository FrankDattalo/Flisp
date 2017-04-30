package io.github.frankdattalo.flisp.ast;

public class FlispString implements FlispAtom {

    private final String value;

    public String getValue() {
      return value;
    }

    public FlispString(String input) {
    this.value = input;
    }

    public String toString() {
    return this.value;
  }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof FlispString)) return false;

        return ((FlispString) other).getValue().equals(this.getValue());
    }
}
