package de.uulm.parsing;

import static java.util.Objects.requireNonNull;

public final class ParseException extends Exception {

  ParseException(String message) {
    super(requireNonNull(message));
  }
}
