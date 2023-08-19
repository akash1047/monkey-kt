package lexer

import token.Span
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

        val t = Token(TokenKind.Illegal, Span(pos, 1))

        when (ch) {
            ',' -> t.kind = TokenKind.Comma
            ';' -> t.kind = TokenKind.Semicolon

            '(' -> t.kind = TokenKind.LParan
            ')' -> t.kind = TokenKind.RParan
            '{' -> t.kind = TokenKind.LBrace
            '}' -> t.kind = TokenKind.RBrace

            '+' -> t.kind = TokenKind.Plus
            '-' -> t.kind = TokenKind.Minus
            '*' -> t.kind = TokenKind.Asterisk
            '/' -> t.kind = TokenKind.Slash

            '=' -> {
                t.kind = if (peak() == '=') {
                    readChar()
                    t.span.len = 2
                    TokenKind.Equal
                } else TokenKind.Assign
            }

            '!' -> {
                t.kind = if (peak() == '=') {
                    readChar()
                    t.span.len = 2
                    TokenKind.NotEqual
                } else TokenKind.Bang
            }

            '<' -> {
                t.kind = if (peak() == '=') {
                    readChar()
                    t.span.len = 2
                    TokenKind.LessEqual
                } else TokenKind.LessThan
            }

            '>' -> {
                t.kind = if (peak() == '=') {
                    readChar()
                    t.span.len = 2
                    TokenKind.GreaterEqual
                } else TokenKind.GreaterThan
            }

            '"' -> {
                return readString()
            }

            NULL -> {
                t.kind = TokenKind.Eof
                t.span.len = 0
            }

            else -> {
                if (ch.isLetter() || ch == '_') {
                    t.span = readIdent()
                    t.kind = lookupIdent(extract(t.span))
                    return t
                } else if (ch.isDigit()) {
                    return readNumber()
                }
            }
        }

        readChar()
        return t
    }

    private fun readString(): Token {
        val start = pos

        // ch is " is checked in nextChar() function
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

        return Token(TokenKind.StringLiteral, Span(start, pos - start))
    }

    private fun skipWhitespace() {
        while (ch.isWhitespace()) {
            readChar()
        }
    }

    private fun skipComment() {
        if (ch == '/' && peak() == '/') {
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
        var k = TokenKind.IntLiteral

        // ch is digit is checked in nextChar() function
        // so incrementing position to consume first digit
        readChar()

        while (ch.isDigit()) {
            readChar()
        }

        if (ch == '.') {
            k = TokenKind.FloatLiteral
            readChar()

            while (ch.isDigit()) {
                readChar()
            }
        }

        return Token(k, Span(start, pos - start))
    }

    private fun readIdent(): Span {
        val start = pos

        // ch is letter or underscore is checked in nextChar() function
        // so incrementing position to consume first alphabet or underscore
        readChar()

        while (ch.isLetterOrDigit() || ch == '_') {
            readChar()
        }

        return Span(start, pos - start)
    }

    private fun peak() = if (readPos < input.length) input[readPos] else NULL

    fun extract(s: Span) = input.slice(s.pos..<s.pos + s.len)
}

