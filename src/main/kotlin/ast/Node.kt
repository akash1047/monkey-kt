package ast

import token.Span

interface Node {
    fun tokenLiteral(): Span
}