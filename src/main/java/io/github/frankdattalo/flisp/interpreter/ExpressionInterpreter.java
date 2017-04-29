package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.FlispAtom;
import io.github.frankdattalo.flisp.ast.FlispExpression;

public class ExpressionInterpreter implements Interpreter<FlispExpression> {

    private static final ExpressionInterpreter instance;

    static {
        instance = new ExpressionInterpreter();
    }

    public static FlispExpression run(Environment environment,
                                         FlispExpression expression) {

        return instance.interpret(environment, expression);
    }

    @Override
    public FlispExpression interpret(Environment environment,
                                       FlispExpression expression) {

        if(expression instanceof FlispAtom) {
            return AtomInterpreter.run(environment, expression);

        } else {
            return ListInterpreter.run(environment, expression);
        }
    }
}
