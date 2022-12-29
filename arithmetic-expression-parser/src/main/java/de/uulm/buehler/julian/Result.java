package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public sealed interface Result<T, E> {

  static <T, E> Result<T, E> ok(T value) {
    return new Ok<>(value);
  }

  static <T, E> Result<T, E> err(E error) {
    return new Err<>(error);
  }

  boolean isOk();

  boolean isErr();

  Optional<T> toOk();

  Optional<E> toErr();

  <U> Result<U, E> map(Function<? super T, U> mapper);

  <U> Result<U, E> flatMap(Function<? super T, ? extends Result<U, E>> mapper);

  void handle(Consumer<T> okHandler, Consumer<E> errorHandler);

  record Ok<T, E>(T value) implements Result<T, E> {

    public Ok {
      requireNonNull(value);
    }

    @Override
    public boolean isOk() {
      return true;
    }

    @Override
    public boolean isErr() {
      return false;
    }

    @Override
    public Optional<T> toOk() {
      return Optional.of(value);
    }

    @Override
    public Optional<E> toErr() {
      return Optional.empty();
    }

    @Override
    public <U> Result<U, E> map(Function<? super T, U> mapper) {
      return new Ok<>(mapper.apply(value));
    }

    @Override
    public <U> Result<U, E> flatMap(Function<? super T, ? extends Result<U, E>> mapper) {
      return requireNonNull(mapper.apply(value));
    }

    @Override
    public void handle(Consumer<T> okHandler, Consumer<E> errorHandler) {
      okHandler.accept(value);
    }
  }

  record Err<T, E>(E error) implements Result<T, E> {

    public Err {
      requireNonNull(error);
    }

    @Override
    public boolean isOk() {
      return false;
    }

    @Override
    public boolean isErr() {
      return true;
    }

    @Override
    public Optional<T> toOk() {
      return Optional.empty();
    }

    @Override
    public Optional<E> toErr() {
      return Optional.of(error);
    }

    @Override
    public <U> Result<U, E> map(Function<? super T, U> mapper) {
      @SuppressWarnings("unchecked")
      var result = (Result<U, E>) this;

      return result;
    }

    @Override
    public <U> Result<U, E> flatMap(Function<? super T, ? extends Result<U, E>> mapper) {
      @SuppressWarnings("unchecked")
      var result = (Result<U, E>) this;

      return result;
    }

    @Override
    public void handle(Consumer<T> okHandler, Consumer<E> errorHandler) {
      errorHandler.accept(error);
    }
  }
}
