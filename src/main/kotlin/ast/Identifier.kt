package ast

import token.Span
import token.Token

class Identifier : Expression {
    lateinit var token: Token
    lateinit var value: String

    override fun expressionNode() {
    }

    override fun tokenLiteral(): Span = token.span

}
