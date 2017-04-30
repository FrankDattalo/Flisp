package io.github.frankdattalo.flisp.parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import io.github.frankdattalo.flisp.antlr4.FlispLexer;
import io.github.frankdattalo.flisp.antlr4.FlispBaseVisitor;
import io.github.frankdattalo.flisp.antlr4.FlispParser;
import io.github.frankdattalo.flisp.ast.FlispAtom;
import io.github.frankdattalo.flisp.ast.FlispExpression;
import io.github.frankdattalo.flisp.ast.FlispList;
import io.github.frankdattalo.flisp.ast.FlispNumber;
import io.github.frankdattalo.flisp.ast.FlispSymbol;
import io.github.frankdattalo.flisp.ast.FlispString;
import javaslang.collection.List;

public class ParserImpl implements Parser {

  private static final class ParserExceptionBypass extends RuntimeException {
    private static final long serialVersionUID = 6628702804357743024L;

    private ParserExceptionBypass(String str) {
      super(str);
    }
  }

  private final String  input;
  private final boolean isFile;

  ParserImpl(String input, boolean isFile) {
    this.input = input;
    this.isFile = isFile;
  }

  private FlispList parse(FlispParser parser) {
    ProgramVisitor pv = new ProgramVisitor();

    return pv.visit(parser.program());
  }

  @Override
  public FlispList parse() throws ParserException {
    if (this.isFile && !Pattern.matches(".+\\.flisp$", this.input)) {
      throw new RuntimeException(
          "file extension for flisp programs must be .flisp but was "
              + this.input);
    }

    try {
      CharStream inputStream = this.isFile
          ? CharStreams.fromFileName(this.input)
          : CharStreams.fromString(input);

      Lexer lexer = new FlispLexer(inputStream);
      TokenStream tokenStream = new BufferedTokenStream(lexer);
      FlispParser parser = new FlispParser(tokenStream);

      lexer.removeErrorListeners();
      parser.removeErrorListeners();

      parser.setErrorHandler(new BailErrorStrategy());

      lexer.addErrorListener(FlispErrorListener.instance);
      parser.addErrorListener(FlispErrorListener.instance);

      return parse(parser);

    } catch (IOException | ParseCancellationException | ParserExceptionBypass
    /* | StringIndexOutOfBoundsException */ e) {
      throw new ParserException(e.getMessage());
    }
  }
  
  private static class FlispErrorListener extends BaseErrorListener {

    public static final FlispErrorListener instance = new FlispErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
        int line, int charPositionInLine, String msg, RecognitionException e) {

      String sourceName = recognizer.getInputStream().getSourceName();

      if (!sourceName.isEmpty()) {
        sourceName = String.format("%s:%d:%d: ", sourceName, line,
            charPositionInLine);
      }

      throw new ParserExceptionBypass(
          sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
    }
  }

  private static class ProgramVisitor extends FlispBaseVisitor<FlispList> {

    @Override
    public FlispList visitProgram(FlispParser.ProgramContext ctx) {
      ExpressionVisitor ev = new ExpressionVisitor();
      return new FlispList(
          List.ofAll(ctx.expression()).map(ec -> ec.accept(ev)));
    }
  }

  private static class ExpressionVisitor
      extends FlispBaseVisitor<FlispExpression> {

    @Override
    public FlispExpression visitExpression(FlispParser.ExpressionContext ctx) {
      if (ctx.atom() != null) {
        return ctx.accept(new AtomVisitor());
      } else {
        return ctx.accept(new ListVisitor());
      }
    }
  }

  public static class ListVisitor extends FlispBaseVisitor<FlispList> {
    @Override
    public FlispList visitList(FlispParser.ListContext ctx) {
      ExpressionVisitor ev = new ExpressionVisitor();
      return new FlispList(
          List.ofAll(ctx.expression()).map(ec -> ec.accept(ev)));
    }
  }

  private static class AtomVisitor extends FlispBaseVisitor<FlispAtom> {
    @Override
    public FlispAtom visitAtom(FlispParser.AtomContext ctx) {
      if (ctx.string() != null) {
        return new FlispString(ctx.string().getText(), true);
      } else if (ctx.symbol() != null) {
        return new FlispSymbol(ctx.symbol().getText());
      } else { // ctx.number()
        return new FlispNumber(new BigDecimal(ctx.number().getText()));
      }
    }

  }

}
