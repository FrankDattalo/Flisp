package io.github.frankdattalo.flisp.runner;

import io.github.frankdattalo.flisp.ast.FlispList;
import io.github.frankdattalo.flisp.interpreter.ProgramInterpreter;
import io.github.frankdattalo.flisp.parser.Parser;
import io.github.frankdattalo.flisp.parser.ParserException;

public class Main {
    public static void main(String[] args) throws ParserException {
        if(args.length > 0) {
            FlispList program = Parser.fromFile(args[0]).parse();
            ProgramInterpreter.run(program);
        } else {
            Repl.run();
        }
    }
}
