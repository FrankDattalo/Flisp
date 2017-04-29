package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.FlispExpression;
import io.github.frankdattalo.flisp.ast.FlispLambda;
import io.github.frankdattalo.flisp.ast.FlispList;
import io.github.frankdattalo.flisp.ast.FlispSymbol;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;

import java.io.InputStream;
import java.io.PrintStream;

public class Environment {

    private static PrintStream out = System.out;
    private static PrintStream err = System.err;
    private static InputStream in = System.in;

    public static void setOut(PrintStream ps) {
        out = ps;
    }

    public static void setErr(PrintStream ps) {
        err = ps;
    }

    public static void setIn(InputStream is) {
        in = is;
    }

    public static Environment global() {

    Environment GLOBAL =  new Environment();

    HashMap rep = HashMap.ofEntries(
            new Tuple2<String, FlispExpression>("print", new FlispLambda
                    (GLOBAL, FlispSymbol.list("x"), env -> {
                        out.print(env.get("x"));
                        return FlispList.EMPTY;
                    }))
    );

      GLOBAL.setRepresentation(rep);

    return GLOBAL;
    }

    private Map<String, FlispExpression>   representation;
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

    public void put(FlispSymbol key, FlispExpression value) {
        this.setRepresentation(this.representation.put(key.getValue(), value));
    }

    public boolean isDefined(FlispSymbol key) {
    return this.representation.containsKey(key.getValue())
        || (this.outer != null && this.outer.isDefined(key));
    }
}