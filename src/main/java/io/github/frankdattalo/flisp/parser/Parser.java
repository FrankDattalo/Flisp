package io.github.frankdattalo.flisp.parser;

import io.github.frankdattalo.flisp.ast.FlispList;

public interface Parser {
  FlispList parse() throws ParserException;

  public static Parser fromFile(String fileName) {
    return new ParserImpl(fileName, true);
  }

  public static Parser fromString(String input) {
    return new ParserImpl(input, false);
  }
}
