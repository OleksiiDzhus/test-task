package com.sombra.test.dzhus.authorbook.exceptions;

public class NoSuchAuthorException extends RuntimeException {

  private final static String MESSAGE = "No such author ";
  private Long authorId;

  public NoSuchAuthorException(Long authorId) {
    super(MESSAGE);
    this.authorId = authorId;
  }

  @Override
  public String toString() {
    return MESSAGE + authorId;
  }
}
