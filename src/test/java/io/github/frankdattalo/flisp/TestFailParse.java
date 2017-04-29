package io.github.frankdattalo.flisp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.github.frankdattalo.flisp.parser.Parser;
import io.github.frankdattalo.flisp.parser.ParserException;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class TestFailParse extends TestCase {

  public static void parse(String input) throws ParserException {
    Parser.fromString(input).parse();
  }

  @Test(expected = ParserException.class)
  public void testFailParseNonMatchingBrackets() throws ParserException {
    parse("(");
  }

  @Test(expected = ParserException.class)
  public void testFailParseNonMatchingBrackets2() throws ParserException {
    parse("((");
  }

  @Test(expected = ParserException.class)
  public void testFailParseNonMatchingBrackets3() throws ParserException {
    parse("())");
  }

  @Test(expected = ParserException.class)
  public void testFailParseEmptyString() throws ParserException {
    parse("");
  }

  @Test(expected = ParserException.class)
  public void testParseNonClosedString() throws ParserException {
    parse("\"");
  }

  @Test(expected = ParserException.class)
  public void testParseNonClosedString2() throws ParserException {
    parse("\"\"\"");
  }

  @Test(expected = ParserException.class)
  public void testParseNumberNoTrainingDigitsWithDecial()
      throws ParserException {
    parse("3.");
  }

  @Test(expected = ParserException.class)
  public void testUnmatchedBracketsRight()
      throws ParserException {
    parse(")))))))");
  }

  @Test(expected = ParserException.class)
  public void testUnmatchedBracketsLeft()
      throws ParserException {
    parse("((((((((((((((");
  }
}
