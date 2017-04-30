package io.github.frankdattalo.flisp.ast;

import javaslang.collection.List;

public class FlispSymbol implements FlispAtom {

    private String value;

    public FlispSymbol(String text) {
    this.value = text;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
    return this.value;
    }

    public static List<FlispSymbol> list(String... symbols) {
        return List.of(symbols).map(x -> new FlispSymbol(x));
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof FlispSymbol)) return false;

        return ((FlispSymbol) other).getValue().equals(this.getValue());
    }
}
