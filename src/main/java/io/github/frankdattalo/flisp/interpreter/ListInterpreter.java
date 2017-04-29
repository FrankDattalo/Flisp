package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.FlispExpression;
import io.github.frankdattalo.flisp.ast.FlispLambda;
import io.github.frankdattalo.flisp.ast.FlispList;
import io.github.frankdattalo.flisp.ast.FlispSymbol;
import javaslang.collection.List;

public class ListInterpreter implements Interpreter<FlispExpression> {

    private static final ListInterpreter instance;

    static {
        instance = new ListInterpreter();
    }

    public static FlispExpression run(Environment environment,
                                      FlispExpression expression) {

        return instance.interpret(environment, expression);
    }

    @Override
    public FlispExpression interpret(Environment environment,
                                     FlispExpression expression) {

        if(!(expression instanceof FlispList)) {
            throw new InterpreterException("Expected list, but found " +
                    expression.getClass());
        }

        FlispList list = (FlispList) expression;

        List<FlispExpression> rep = list.getList();

        if(rep.isEmpty()) {
            throw new InterpreterException("Cannot invoke empty list");
        }

        if(rep.head() instanceof FlispSymbol &&
                SpecialFormInterpreter.specialForm(rep.head())) {

            return SpecialFormInterpreter.run(environment, expression);
        }

        FlispExpression expr = ExpressionInterpreter.run(environment, rep
                .head());

        if(expr instanceof FlispList && !(expr instanceof FlispLambda)) {
            expr = ExpressionInterpreter.run(environment, expr);
        }

        if(!(expr instanceof FlispLambda)) {
            throw new InterpreterException("Cannot apply function to non " +
                    "function type " + expr.getClass());
        }

        FlispLambda fn = (FlispLambda) expr;

        return fn.apply(environment, rep.tail());
    }
}
