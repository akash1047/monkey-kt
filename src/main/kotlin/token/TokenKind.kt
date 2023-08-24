package token

enum class TokenKind {
    Illegal,
    Eof,

    // identifier + literals
    Ident,
    Float,
    Int,
    String,

    // operators
    Assign,
    Plus,
    Minus,
    Asterisk,
    Slash,
    Eq,
    NEq,
    Bang,
    LT,
    GT,
    LEq,
    GEq,

    // delimiters
    Comma,
    Semicolon,

    Lparan,
    Rparan,
    Lbrace,
    Rbrace,

    // keywords
    Let,
    Fn,
    If,
    Else,
    True,
    False,
    Return,
}