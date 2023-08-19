import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.TokenKind

class Lexing {
    @Test
    fun comment() {
        val input = """// a single line comment
10 // a integer literal
"ten" // a string literal
"""

        val lexer = Lexer(input)
        var token = lexer.nextToken()

        fun match(t: TokenKind, v: String) {
            assertEquals(t, token.kind)
            assertEquals(v, lexer.extract(token.span))
            token = lexer.nextToken()
        }

        match(TokenKind.IntLiteral, "10")
        match(TokenKind.StringLiteral, "\"ten\"")
        match(TokenKind.Eof, "")
    }

    @Test
    fun endingWithEof() {
        fun testing(input: String, k: TokenKind, v: String) {
            val lexer = Lexer(input)

            val token = lexer.nextToken()
            assertEquals(k, token.kind)
            assertEquals(v, lexer.extract(token.span))

            val eof = lexer.nextToken()
            assertEquals(TokenKind.Eof, eof.kind)
            assertEquals("", lexer.extract(eof.span))
        }

        // testing("1234", TokenKind.IntLiteral, "1234")
        // testing("1234.", TokenKind.FloatLiteral, "1234.")
        // testing("1234.56", TokenKind.FloatLiteral, "1234.56")
        testing("\"not enclosed in double quotes", TokenKind.StringLiteral, "\"not enclosed in double quotes")
        testing("// starting with comment \"not enclosed in double quotes", TokenKind.Eof, "")
    }

    @Test
    fun comparisonExpression() {
        val input = """true != false
false == !true 
10 <= 10
10 < 20
100 > 12
12 >= 12
"""

        val lexer = Lexer(input)
        var token = lexer.nextToken()

        fun match(t: TokenKind, v: String) {
            assertEquals(t, token.kind)
            assertEquals(v, lexer.extract(token.span))
            token = lexer.nextToken()
        }

        match(TokenKind.True, "true")
        match(TokenKind.NotEqual, "!=")
        match(TokenKind.False, "false")

        match(TokenKind.False, "false")
        match(TokenKind.Equal, "==")
        match(TokenKind.Bang, "!")
        match(TokenKind.True, "true")

        match(TokenKind.IntLiteral, "10")
        match(TokenKind.LessEqual, "<=")
        match(TokenKind.IntLiteral, "10")

        match(TokenKind.IntLiteral, "10")
        match(TokenKind.LessThan, "<")
        match(TokenKind.IntLiteral, "20")

        match(TokenKind.IntLiteral, "100")
        match(TokenKind.GreaterThan, ">")
        match(TokenKind.IntLiteral, "12")

        match(TokenKind.IntLiteral, "12")
        match(TokenKind.GreaterEqual, ">=")
        match(TokenKind.IntLiteral, "12")

        match(TokenKind.Eof, "")
    }
}
