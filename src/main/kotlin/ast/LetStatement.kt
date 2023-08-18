package ast

import token.Span
import token.Token

class LetStatement : Statement {
    lateinit var token: Token
    lateinit var name: Identifier
    lateinit var value: Expression
    override fun statementNode() {
    }

    override fun tokenLiteral(): Span = token.span
}