package io.github.frankdattalo.flisp;

import io.github.frankdattalo.flisp.parser.Parser;
import io.github.frankdattalo.flisp.parser.ParserException;
import junit.framework.TestCase;

public class TestShouldParse extends TestCase {

  public static void parse(String input) throws ParserException {
    Parser.fromString(input).parse();
  }

  public void testParseJustString() throws ParserException {
    parse("\"Hello World\"");
  }

  public void testParseEmptyString() throws ParserException {
    parse("\"\"");
  }

  public void testParseNumberNoDecimal() throws ParserException {
    parse("100");
  }

  public void testParseNumberWithDecimal() throws ParserException {
    parse("100.0");
  }

  public void testTwoEmptyStrings() throws ParserException {
    parse("\"\"\"\"");
  }

  public void testNumbers1() throws ParserException {
    parse("346598610000000000000044345285242424242452452452777");
  }

  public void testNumbers2() throws ParserException {
    parse("-346598610000000000000044345285242424242452452452777");
  }

  public void testNumbers3() throws ParserException {
    parse("7");
  }

  public void testNumbers4() throws ParserException {
    parse("-7");
  }

  public void testNumbers5() throws ParserException {
    parse("7.00000");
  }

  public void testNumbers6() throws ParserException {
    parse("-7.00000");
  }
}
