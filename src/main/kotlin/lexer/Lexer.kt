package lexer

import token.Token
import token.TokenKind

const val NULL: Char = 0.toChar()

private val KEYWORDS = mapOf(
    "let" to TokenKind.Let,
    "fn" to TokenKind.Fn,
    "if" to TokenKind.If,
    "else" to TokenKind.Else,
    "true" to TokenKind.True,
    "false" to TokenKind.False,
    "return" to TokenKind.Return,
)

private fun lookupIdent(s: String) = KEYWORDS[s] ?: TokenKind.Ident

class Lexer(private val input: String) {
    private var pos: Int = 0
    private var readPos: Int = 0
    private var ch: Char = NULL

    init {
        readChar()
    }

    fun nextToken(): Token {
        skipWhitespace()
        skipComment()
        skipWhitespace()

        return when (ch) {
            '+' -> char1(TokenKind.Plus)
            '-' -> char1(TokenKind.Minus)
            '*' -> char1(TokenKind.Asterisk)
            '/' -> char1(TokenKind.Slash)

            '=' -> if (peek() == '=') char2(TokenKind.Eq) else char1(TokenKind.Assign)
            '!' -> if (peek() == '=') char2(TokenKind.NEq) else char1(TokenKind.Bang)
            '<' -> if (peek() == '=') char2(TokenKind.LEq) else char1(TokenKind.LT)
            '>' -> if (peek() == '=') char2(TokenKind.GEq) else char1(TokenKind.GT)

            ',' -> char1(TokenKind.Comma)
            ';' -> char1(TokenKind.Semicolon)
            '(' -> char1(TokenKind.Lparan)
            ')' -> char1(TokenKind.Rparan)
            '{' -> char1(TokenKind.Lbrace)
            '}' -> char1(TokenKind.Rbrace)

            '"' -> readString()

            NULL -> eof()

            else ->
                if (ch.isLetter() || ch == '_') readIdent()
                else if (ch.isDigit()) readNumber()
                else char1(TokenKind.Illegal)
        }
    }

    private fun eof() = Token(TokenKind.Eof, "")

    private fun char1(kind: TokenKind) = Token(kind, input.substring(pos, pos + 1))
        .also {
            readChar()
        }


    private fun char2(kind: TokenKind) = Token(kind, input.substring(pos, pos + 2))
        .also {
            readChar()
            readChar()
        }

    private fun readString(): Token {
        val start = pos

        // ch is " is checked in nextToken() function
        // so incrementing position to consume first "
        readChar()

        while (ch != '"' && ch != NULL) {
            readChar()
        }

        // since the above loop is stopped because
        // ch is " or Eof encountered
        // so incrementing if ch is " then consume "
        if (ch == '"') {
            readChar()
        }

        return Token(TokenKind.String, input.substring(start, pos))
    }

    private fun skipWhitespace() {
        while (ch.isWhitespace()) {
            readChar()
        }
    }

    private fun skipComment() {
        if (ch == '/' && peek() == '/') {
            readChar()

            while (ch != '\n' && ch != NULL) {
                readChar()
            }
        }
    }

    private fun readChar() {
        if (readPos < input.length) {
            ch = input[readPos]
            pos = readPos
        } else {
            ch = NULL
            pos = input.length
        }

        readPos++
    }

    private fun readNumber(): Token {
        val start = pos
        var k = TokenKind.Int

        // ch is digit is checked in nextToken() function
        // so incrementing position to consume first digit
        readChar()

        while (ch.isDigit()) {
            readChar()
        }

        if (ch == '.') {
            k = TokenKind.Float
            readChar()

            while (ch.isDigit()) {
                readChar()
            }
        }

        return Token(k, input.substring(start, pos))
    }

    private fun readIdent(): Token {
        val start = pos

        // ch is letter or underscore is checked in nextToken() function
        // so incrementing position to consume first alphabet or underscore
        readChar()

        while (ch.isLetterOrDigit() || ch == '_') {
            readChar()
        }

        val literal = input.substring(start, pos)

        return Token(lookupIdent(literal), literal)
    }

    private fun peek() = if (readPos < input.length) input[readPos] else NULL
}

