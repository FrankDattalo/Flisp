package io.github.frankdattalo.flisp.ast;

import javaslang.collection.List;

public class FlispList implements FlispExpression {

    public static final FlispList EMPTY = new FlispList(List.empty());

    private final List<FlispExpression> expressions;

    public FlispList(List<FlispExpression> expressions) {
        this.expressions = expressions;
    }

    public List<FlispExpression> getList() {
        return this.expressions;
    }

    public String toString() {
        return expressions.toString().substring(4).replaceAll(",", "");
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof FlispList)) return false;

        return this.getList().equals(((FlispList) other).getList());
    }
}
