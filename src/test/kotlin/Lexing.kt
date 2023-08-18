import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.TokenKind

class Lexing {
    @Test
    fun compareExpression() {
        val input = """
            true != false
            false == !true 
            10 <= 10
            10 < 20
            100 > 12
            12 >= 12
        """.trimIndent()

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
