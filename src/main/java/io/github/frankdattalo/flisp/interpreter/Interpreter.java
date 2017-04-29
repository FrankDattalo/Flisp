package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.FlispExpression;

import java.util.function.Function;

public interface Interpreter<K> {

    public K interpret(Environment environment, FlispExpression expression);

}
