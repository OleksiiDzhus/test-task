package com.sombra.test.dzhus.authorbook.exceptions;

public class NoSuchAuthorBookException extends RuntimeException {

  private final String MESSAGE = "No such author_book record ";
  private Long authorBookID;

  public NoSuchAuthorBookException(Long authorBookID) {
    this.authorBookID = authorBookID;
  }

  @Override
  public String toString() {
    return MESSAGE + authorBookID;
  }
}
