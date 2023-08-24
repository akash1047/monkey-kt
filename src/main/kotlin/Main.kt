import lexer.Lexer
import token.TokenKind

fun main() {
    println("monkey-kt")

    while (true) {
        print("> ")
        val input = readlnOrNull()

        if (input == null) {
            println("CTRL-D")
            break
        }

        val l = Lexer(input)

        var t = l.nextToken()
        while (t.kind != TokenKind.Eof) {
            println(t)
            t = l.nextToken()
        }
    }
}