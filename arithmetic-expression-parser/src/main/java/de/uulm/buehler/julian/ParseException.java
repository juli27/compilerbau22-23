package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

final class ParseException extends Exception {

  ParseException(String message) {
    super(requireNonNull(message));
  }
}
