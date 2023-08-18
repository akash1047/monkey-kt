package parser

import ast.Program
import lexer.Lexer
import token.Token

class Parser(private val lexer: Lexer) {
    var curToken: Token
    var peekToken: Token

    init {
        peekToken = lexer.nextToken()
        curToken = lexer.nextToken()
    }

    fun nextToken() {
        curToken = peekToken
        peekToken = lexer.nextToken()
    }

    fun parseProgram(): Program? {
        return null
    }
}