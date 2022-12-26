package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

public record ParseError(String message) {

  public ParseError(String message) {
    this.message = requireNonNull(message);
  }
}
