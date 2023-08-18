package token

enum class TokenKind {
    Illegal,
    Eof,

    // identifier + literals
    Ident,
    FloatLiteral,
    IntLiteral,
    StringLiteral,

    // operators
    Assign,
    Plus,
    Minus,
    Asterisk,
    Slash,
    Equal,
    NotEqual,
    Bang,
    LessThan,
    GreaterThan,

    // delimiters
    Comma,
    Semicolon,

    LParan,
    RParan,
    LBrace,
    RBrace,

    // keywords
    Let,
    Fn,
    If,
    Else,
    True,
    False,
}