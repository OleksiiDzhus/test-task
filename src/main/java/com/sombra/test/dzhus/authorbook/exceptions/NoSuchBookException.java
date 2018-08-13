package com.sombra.test.dzhus.authorbook.exceptions;

public class NoSuchBookException extends RuntimeException {

  private final static String MESSAGE = "No such book ";
  private Long bookId;

  public NoSuchBookException(Long bookId) {
    super(MESSAGE);
    this.bookId = bookId;
  }

  @Override
  public String toString() {
    return MESSAGE + bookId;
  }
}
