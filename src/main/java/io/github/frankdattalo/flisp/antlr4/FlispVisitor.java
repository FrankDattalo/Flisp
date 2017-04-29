// Generated from Flisp.g4 by ANTLR 4.7

package io.github.frankdattalo.flisp.antlr4;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FlispParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FlispVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FlispParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(FlispParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlispParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(FlispParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlispParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(FlispParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlispParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(FlispParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlispParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(FlispParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlispParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(FlispParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlispParser#symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(FlispParser.SymbolContext ctx);
}