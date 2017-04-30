package io.github.frankdattalo.flisp.runner;

import io.github.frankdattalo.flisp.ast.FlispExpression;
import io.github.frankdattalo.flisp.ast.FlispList;
import io.github.frankdattalo.flisp.ast.FlispString;
import io.github.frankdattalo.flisp.interpreter.Environment;
import io.github.frankdattalo.flisp.interpreter.ExpressionInterpreter;
import io.github.frankdattalo.flisp.parser.Parser;
import io.github.frankdattalo.flisp.parser.ParserException;

import java.util.Scanner;

public class Repl {

    private static final String LOGO =
" ______     __           ________      ______       ______    \n" +
"/_____/\\   /_/\\         /_______/\\    /_____/\\     /_____/\\   \n" +
"\\::::_\\/_  \\:\\ \\        \\__.::._\\/    \\::::_\\/_    \\:::_ \\ \\  \n" +
" \\:\\/___/\\  \\:\\ \\          \\::\\ \\      \\:\\/___/\\    \\:(_) \\ \\ \n" +
"  \\:::._\\/   \\:\\ \\____     _\\::\\ \\__    \\_::._\\:\\    \\: ___\\/ \n" +
"   \\:\\ \\      \\:\\/___/\\   /__\\::\\__/\\     /____\\:\\    \\ \\ \\   \n" +
"    \\_\\/       \\_____\\/   \\________\\/     \\_____\\/     \\_\\/   \n" +
"                                                              \n" +
" Flisp Lang. Copyright: Frank Dattalo 2017                    \n";

    private static String readline(Scanner in) {
        StringBuilder sb = new StringBuilder();

        boolean cont = true;

        while(cont && in.hasNextLine()) {
            String inline = in.nextLine();

            if (inline.length() > 0 && inline.charAt(inline.length() - 1) ==
                    '\\') {
                cont = true;
                inline = inline.substring(0, inline.length() - 1);
            } else {
                cont = false;
            }

            sb.append(inline);
        }

        return sb.toString();
    }

    public static void run() {
	System.out.print(LOGO);

        Environment env = Environment.global();
        try (Scanner in = new Scanner(System.in)) {
            while(true) {
                try {
                    System.out.print("> ");
                    String input = readline(in);

                    if(input.length() == 0) return;

                    FlispList program = Parser.fromString(input).parse();

                    for (FlispExpression flispExpression : program.getList()) {
                        FlispExpression ret = ExpressionInterpreter.run(env,
                                flispExpression);


                        System.out.println(">>> " + ret);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
