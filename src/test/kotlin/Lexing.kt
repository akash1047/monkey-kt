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
            assertEquals(v, token.literal)
            token = lexer.nextToken()
        }

        match(TokenKind.Int, "10")
        match(TokenKind.String, "\"ten\"")
        match(TokenKind.Eof, "")
    }

    @Test
    fun endingWithEof() {
        fun testing(input: String, k: TokenKind, v: String) {
            val lexer = Lexer(input)

            val token = lexer.nextToken()
            assertEquals(k, token.kind)
            assertEquals(v, token.literal)

            val eof = lexer.nextToken()
            assertEquals(TokenKind.Eof, eof.kind)
            assertEquals("", eof.literal)
        }

        // testing("1234", TokenKind.Int, "1234")
        // testing("1234.", TokenKind.FloatLiteral, "1234.")
        // testing("1234.56", TokenKind.FloatLiteral, "1234.56")
        testing("\"not enclosed in double quotes", TokenKind.String, "\"not enclosed in double quotes")
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
            assertEquals(v, token.literal)
            token = lexer.nextToken()
        }

        match(TokenKind.True, "true")
        match(TokenKind.NEq, "!=")
        match(TokenKind.False, "false")

        match(TokenKind.False, "false")
        match(TokenKind.Eq, "==")
        match(TokenKind.Bang, "!")
        match(TokenKind.True, "true")

        match(TokenKind.Int, "10")
        match(TokenKind.LEq, "<=")
        match(TokenKind.Int, "10")

        match(TokenKind.Int, "10")
        match(TokenKind.LT, "<")
        match(TokenKind.Int, "20")

        match(TokenKind.Int, "100")
        match(TokenKind.GT, ">")
        match(TokenKind.Int, "12")

        match(TokenKind.Int, "12")
        match(TokenKind.GEq, ">=")
        match(TokenKind.Int, "12")

        match(TokenKind.Eof, "")
    }
}
