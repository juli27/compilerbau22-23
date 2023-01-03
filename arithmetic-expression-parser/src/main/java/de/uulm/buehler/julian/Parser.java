package de.uulm.buehler.julian;

final class Parser extends RecursiveDescentParser {

  Parser(ExpressionLexer lexer) {
    super(lexer);
  }

  double parse() throws ParseException {
    advance();

    return s();
  }

  private double s() throws ParseException {
    var result = e();

    consume(TokenClass.EOF);

    return result;
  }

  private double e() throws ParseException {
    var result = t();

    while (check(TokenClass.PLUS) || check(TokenClass.MINUS)) {
      if (match(TokenClass.PLUS)) {
        result += t();
      } else if (match(TokenClass.MINUS)) {
        result -= t();
      }
    }

    return result;
  }

  private double t() throws ParseException {
    var result = p();

    while (check(TokenClass.MUL) || check(TokenClass.DIV)) {
      if (match(TokenClass.MUL)) {
        result *= p();
      } else if (match(TokenClass.DIV)) {
        result /= p();
      }
    }

    return result;
  }

  private double p() throws ParseException {
    var result = f();

    if (!match(TokenClass.POW)) {
      return result;
    }

    consume(TokenClass.LEFT_PAR);

    result = Math.pow(result, e());

    consume(TokenClass.RIGHT_PAR);

    return result;
  }

  private double f() throws ParseException {
    if (match(TokenClass.LEFT_PAR)) {
      var result = e();

      consume(TokenClass.RIGHT_PAR);

      return result;
    }

    if (peek() instanceof Token.NumberLiteral(var value)) {
      advance();

      return value;
    }

    throw makeError("expression expected");
  }
}
