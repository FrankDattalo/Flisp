package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.*;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class Environment {

    private static PrintStream out = System.out;
    private static PrintStream err = System.err;
    private static Scanner in = new Scanner(System.in);

    public static void setOut(PrintStream ps) {
        out = ps;
    }

    public static void setErr(PrintStream ps) {
        err = ps;
    }

    public static void setIn(InputStream is) {
        if (in != null) {
            in.close();
        }

        in = new Scanner(is);
    }

    public static Environment global() {

        Environment GLOBAL =  new Environment();

        HashMap rep = HashMap.ofEntries(
                new Tuple2<String, FlispExpression>("print", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x"), env -> {
                            out.print(env.get("x"));
                            return FlispList.EMPTY;
                        })),

                new Tuple2<String, FlispExpression>("error", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x"), env -> {
                            err.print(env.get("x"));
                            return FlispList.EMPTY;
                        })),

                new Tuple2<String, FlispExpression>("read", new FlispLambda
                        (GLOBAL, FlispSymbol.list(), env -> new FlispString
                                (in.nextLine(), true))),

                new Tuple2<String, FlispExpression>("true", new FlispSymbol
                        ("true")),

                new Tuple2<String, FlispExpression>("false", new FlispSymbol
                        ("false")),

                new Tuple2<String, FlispExpression>("newline", new FlispString
                        (System.lineSeparator(), false)),

                new Tuple2<String, FlispExpression>("+", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"), env -> {
                            FlispExpression xe = env.get("x");
                            FlispExpression ye = env.get("y");

                            if(!(xe instanceof FlispNumber) ||
                                    !(ye instanceof FlispNumber)) {
                                throw new InterpreterException("+ requries " +
                                        "two numbers");
                            }

                            return new FlispNumber(((FlispNumber) xe)
                                    .getValue().add(((FlispNumber) ye).getValue()));

                        })),

                new Tuple2<String, FlispExpression>("-", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"), env -> {
                            FlispExpression xe = env.get("x");
                            FlispExpression ye = env.get("y");

                            if(!(xe instanceof FlispNumber) || !(ye
                                    instanceof FlispNumber)) {
                                throw new InterpreterException("- requires " +
                                        "two numbers");
                            }

                            return new FlispNumber(((FlispNumber) xe)
                                    .getValue().subtract(((FlispNumber) ye).getValue()));
                        })),

                new Tuple2<String, FlispExpression>("*", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"), env -> {
                            FlispExpression xe = env.get("x");
                            FlispExpression ye = env.get("y");

                            if(!(xe instanceof FlispNumber) || !(ye
                                    instanceof FlispNumber)) {
                                throw new InterpreterException("* requries " +
                                        "two numbers");
                            }

                            return new FlispNumber(((FlispNumber) xe)
                                    .getValue().multiply(((FlispNumber) ye).getValue()));
                        })),

                new Tuple2<String, FlispExpression>("/", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"), env -> {
                            FlispExpression xe = env.get("x");
                            FlispExpression ye = env.get("y");

                            if(!(xe instanceof FlispNumber) || !(ye
                                    instanceof FlispNumber)) {
                                throw new InterpreterException("/ requries " +
                                        "two numbers");
                            }

                            return new FlispNumber(((FlispNumber) xe)
                                    .getValue().subtract(((FlispNumber) ye).getValue()));
                        })),

                new Tuple2<String, FlispExpression>("floor", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x"), env -> {
                            FlispExpression xe = env.get("x");

                            if(!(xe instanceof FlispNumber)) {
                                throw new InterpreterException("floor " +
                                        "requires a number");
                            }

                            return new FlispNumber(new BigDecimal(
                                    ((FlispNumber) xe).getValue().toBigInteger()));
                        })),

                new Tuple2<String, FlispExpression>("concat", new
                        FlispLambda(GLOBAL, FlispSymbol.list("x", "y"),
                        env -> new FlispString(env.get("x").toString()
                                .concat(env.get("y").toString()), false))),

                new Tuple2<String, FlispExpression>("length", new
                        FlispLambda(GLOBAL, FlispSymbol.list("x"), env -> {

                            FlispExpression s = env.get("x");

                            if(!(s instanceof FlispString)) {
                                throw new InterpreterException("length " +
                                        "requires a string");
                            }

                            return new FlispNumber(BigDecimal.valueOf(
                                    ((FlispString) s).getValue().length()));
                        })),

                new Tuple2<String, FlispExpression>("charAt", new
                        FlispLambda(GLOBAL, FlispSymbol.list("str", "i"),
                        env -> {
                            FlispExpression strE = env.get("str");
                            FlispExpression iE = env.get("i");

                            if(!(strE instanceof FlispString) ||
                                    !(iE instanceof FlispNumber)) {
                                throw new InterpreterException("charAt takes " +
                                        "a string and a number");
                            }

                            return new FlispString("" +
                                    ((FlispString) strE).getValue().charAt((
                                    (FlispNumber) iE).getValue().intValue()),
                                    false);
                        })),

                new Tuple2<String, FlispExpression>("toString", new
                        FlispLambda(GLOBAL, FlispSymbol.list("x"),
                        env -> new FlispString(env.get("x").toString(),
                                false))),

                new Tuple2<String, FlispExpression>("=", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"),
                        env -> env.get("x").equals(env.get("y"))
                                ? env.get("true") : env.get("false"))),

                new Tuple2<String, FlispExpression>(">", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"), env -> {
                            FlispExpression xe = env.get("x");
                            FlispExpression ye = env.get("y");

                            if(!(xe instanceof FlispNumber) || !(ye
                                    instanceof FlispNumber)) {
                                throw new InterpreterException("> requries " +
                                        "two numbers");
                            }

                            return ((FlispNumber) xe).getValue().compareTo(
                                    ((FlispNumber) ye).getValue()) > 0 ? env
                                    .get("true") : env.get("false");
                        })),

                new Tuple2<String, FlispExpression>("<", new FlispLambda
                        (GLOBAL, FlispSymbol.list("x", "y"), env -> {
                            FlispExpression xe = env.get("x");
                            FlispExpression ye = env.get("y");

                            if(!(xe instanceof FlispNumber) || !(ye
                                    instanceof FlispNumber)) {
                                throw new InterpreterException("< requries " +
                                        "two numbers");
                            }

                            return ((FlispNumber) xe).getValue().compareTo(
                                    ((FlispNumber) ye).getValue()) < 0 ? env
                                    .get("true") : env.get("false");
                        }))
        );

        GLOBAL.setRepresentation(rep);

        return GLOBAL;
    }

    private Map<String, FlispExpression>     representation;
    private Map<FlispSymbol, FlispExpression> exports = HashMap.empty();
    private final Environment               outer;

    public Environment(Map<String, FlispExpression> rep,
                        Environment outer) {

    this.representation = rep;
    this.outer = outer;
    }

    private Environment() {
      this.outer = null;
    }

    private void setRepresentation(Map<String, FlispExpression> rep) {
      this.representation = rep;
    }

    public FlispExpression get(FlispSymbol key) {
    if (this.representation.containsKey(key.getValue())) {
      return this.representation.get(key.getValue()).get();

    } else if (this.outer != null && this.outer.isDefined(key)) {
      return this.outer.get(key);

    } else {
      throw new InterpreterException(key.getValue() + " is undefined");
    }
    }

    public FlispExpression get(String key) {
      if (this.representation.containsKey(key)) {
          return this.representation.get(key).get();

      } else if (this.outer != null && this.outer.isDefined(new FlispSymbol
              (key))) {
          return this.outer.get(key);

      } else {
          throw new InterpreterException(key + " is undefined");
      }
    }

    public void export(FlispSymbol key) {
        this.exports = this.exports.put(key, this.get(key));
    }

    public Map<FlispSymbol, FlispExpression> getExports() {
        return this.exports;
    }

    public void put(FlispSymbol key, FlispExpression value) {
        this.setRepresentation(this.representation.put(key.getValue(), value));
    }

    public boolean isDefined(FlispSymbol key) {
    return this.representation.containsKey(key.getValue())
        || (this.outer != null && this.outer.isDefined(key));
    }
}