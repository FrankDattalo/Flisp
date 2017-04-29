package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.FlispExpression;
import io.github.frankdattalo.flisp.ast.FlispList;
import javaslang.collection.List;

public class ProgramInterpreter implements Interpreter<Environment> {

    private static final ProgramInterpreter instance;

    static {
        instance = new ProgramInterpreter();
    }

    public static Environment run(FlispExpression expression) {

        return instance.interpret(Environment.global(), expression);
    }

    @Override
    public Environment interpret(Environment environment,
                                 FlispExpression expression) {

        if(expression instanceof FlispList) {
            for (FlispExpression flispExpression : ((FlispList) expression).getList()) {
                ExpressionInterpreter.run(environment, flispExpression);
            }

        } else {
            ExpressionInterpreter.run(environment, expression);
        }

        return environment;
    }
}
