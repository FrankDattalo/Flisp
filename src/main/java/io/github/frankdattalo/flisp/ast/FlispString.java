package io.github.frankdattalo.flisp.ast;

public class FlispString implements FlispAtom {

    private final String value;
    private final String valueWithoutQuotes;

    public String getValue() {
      return valueWithoutQuotes;
    }

    public FlispString(String input, boolean stripQuotes) {
        this.value = input;

        if(!stripQuotes) {
            this.valueWithoutQuotes = this.value;
            return;
        }

        if(this.value.length() > 2) {
            valueWithoutQuotes = this.value.substring(1, this.value.length() - 1);
        } else {
            valueWithoutQuotes = "";
        }
    }

    public String toString() {
    return this.valueWithoutQuotes;
  }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof FlispString)) return false;

        return ((FlispString) other).getValue().equals(this.getValue());
    }
}
