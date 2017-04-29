package io.github.frankdattalo.flisp.interpreter;

import io.github.frankdattalo.flisp.ast.FlispList;
import io.github.frankdattalo.flisp.parser.Parser;
import io.github.frankdattalo.flisp.parser.ParserException;

public class FileRunner {
    public static void main(String[] args) throws ParserException {
        Parser parser = Parser.fromString(

                "(def greeter (fn () (print \"Hello World\")))" +
                "(greeter)"
        );

        FlispList list = parser.parse();

        System.out.println(list);

        ProgramInterpreter.run(list);
    }
}
