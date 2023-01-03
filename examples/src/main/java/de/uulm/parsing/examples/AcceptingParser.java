package de.uulm.parsing.examples;

import de.uulm.parsing.ParseException;

@FunctionalInterface
public interface AcceptingParser {

  void parse() throws ParseException;
}
