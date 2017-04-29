package io.github.frankdattalo.flisp.interpreter;


import io.github.frankdattalo.flisp.ast.*;

public class AtomInterpreter implements Interpreter<FlispExpression> {

    private static final AtomInterpreter instance;

    static {
        instance = new AtomInterpreter();
    }

    public static FlispExpression run(Environment environment,
                                      FlispExpression expression) {

        return instance.interpret(environment, expression);
    }


    @Override
    public FlispExpression interpret(Environment environment,
                                     FlispExpression expression) {

        if(!(expression instanceof FlispAtom)) {
            throw new InterpreterException("Expected atom, but found " +
                    expression.getClass());
        }

        FlispAtom atom = (FlispAtom) expression;

        if(atom instanceof FlispNumber || atom instanceof FlispString) {
            return atom;

        } else {
            return environment.get((FlispSymbol) atom);
        }
    }
}
