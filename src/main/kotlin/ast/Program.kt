package ast

import token.Span

class Program : Node {
    lateinit var statement: Array<Statement>

    override fun tokenLiteral(): Span = if (statement.isNotEmpty()) statement[0].tokenLiteral() else Span(0, 0)
}