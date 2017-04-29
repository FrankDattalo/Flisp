package io.github.frankdattalo.flisp.antlr4.tree;

import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import io.github.frankdattalo.flisp.antlr4.FlispLexer;
import io.github.frankdattalo.flisp.antlr4.FlispParser;

public class TreeViewTest {
  public static void main(String[] args) throws IOException {

    CharStream cs = CharStreams.fromFileName("test/flispGrammarTest.flisp");

    FlispLexer fl = new FlispLexer(cs);

    TokenStream ts = new CommonTokenStream(fl);

    FlispParser p = new FlispParser(ts);

    ParseTree pt = p.program();

    System.out.println(pt.toStringTree(p));

    JFrame jf = new JFrame("Grammar Tree");
    JPanel jp = new JPanel();

    TreeViewer tv = new TreeViewer(Arrays.asList(p.getRuleNames()), pt);
    tv.setScale(1.25);

    jp.add(tv);
    jf.add(jp);

    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setSize(1700, 600);

    jf.setVisible(true);
  }
}
