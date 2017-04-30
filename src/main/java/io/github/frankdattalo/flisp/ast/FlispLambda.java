package io.github.frankdattalo.flisp.ast;

import io.github.frankdattalo.flisp.interpreter.Environment;
import io.github.frankdattalo.flisp.interpreter.ExpressionInterpreter;
import io.github.frankdattalo.flisp.interpreter.InterpreterException;
import io.github.frankdattalo.flisp.interpreter.ListInterpreter;
import javaslang.Function1;
import javaslang.Tuple2;
import javaslang.collection.List;

import java.util.Objects;

public class FlispLambda extends FlispList {

    private static final FlispExpression lambda = new FlispSymbol("fn");

    private final List<FlispSymbol> argsList;

    private final Environment environment;

    private final FlispExpression body;

    private final Function1<Environment, FlispExpression> nativeCode;

    public FlispLambda(Environment environment,
                       List<FlispSymbol> argSymbols,
                       Function1<Environment,
                               FlispExpression> nativeFunction) {

        super(List.of(lambda)
                .append(new FlispList((List) argSymbols))
                .append(new FlispString("([native code])")));

        this.argsList = argSymbols;
        this.environment = environment;
        this.body = null;
        this.nativeCode = nativeFunction;
    }

    public FlispLambda(Environment environment,
                       List<FlispSymbol> argsSymbol,
                       FlispExpression body) {

        super(List.of(lambda)
                .append(new FlispList((List) argsSymbol))
                .append(body));

        this.argsList = argsSymbol;
        this.environment = environment;
        this.body = body;
        this.nativeCode = null;
    }

    public FlispExpression apply(Environment env, List<FlispExpression>
            parameters) {

        int expected = this.argsList.length();
        int actual = parameters.length();

        if(expected != actual) {
            throw new InterpreterException("Invalid amount of parameters " +
                    "passed to lambda. Expected: " + expected + " but was " +
                    actual);
        }

        Environment inner = new Environment(
                this.argsList.zip(parameters).toMap(x ->
                    new Tuple2<>(x._1.getValue(),
                            ExpressionInterpreter.run(env, x._2))
                ),
                this.environment);

        if(this.body != null) {
            return ExpressionInterpreter.run(inner, this.body);
        } else {
            return this.nativeCode.apply(inner);
        }
    }

    public String toString() {
        if(this.body != null) {
            return "(fn " + this.argsList.toString().substring(4).replaceAll
                    ("," , "") + " " +
                    this.body + ")";
        } else {
            return "(fn " + this.argsList.toString().substring(4).replaceAll
                    (",", "") +
                    " " +
                    "[native" +
                    " code])";
        }
    }

    public boolean equals(Object other) {
        return other == this;
    }

}
