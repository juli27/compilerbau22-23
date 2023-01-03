package de.uulm.buehler.julian.experimental;

import static java.util.Objects.requireNonNull;

public record ParseError(String message) {

  public ParseError(String message) {
    this.message = requireNonNull(message);
  }
}
