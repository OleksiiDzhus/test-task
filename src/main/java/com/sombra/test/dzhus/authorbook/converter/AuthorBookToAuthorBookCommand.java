package com.sombra.test.dzhus.authorbook.converter;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorBookToAuthorBookCommand implements Converter<AuthorBook, AuthorBookCommand> {

  @Override
  public AuthorBookCommand convert(AuthorBook authorBook) {
    if (authorBook == null) {
      return null;
    }
    AuthorBookCommand authorBookCommand = new AuthorBookCommand();

    authorBookCommand.setId(authorBook.getId());
    if (hasNeededInfo(authorBook)) {
      authorBookCommand.setAuthorId(authorBook.getAuthor().getId());
      authorBookCommand.setBookId(authorBook.getBook().getId());
    } else {
      authorBookCommand.setAuthorId(0L);
      authorBookCommand.setBookId(0L);
    }

    return authorBookCommand;
  }

  private boolean hasNeededInfo(AuthorBook authorBook) {
    return authorIsPresent(authorBook) && bookIsPresent(authorBook);
  }

  private boolean authorIsPresent(AuthorBook authorBook) {
    return authorBook.getAuthor() != null;
  }

  private boolean bookIsPresent(AuthorBook authorBook) {
    return authorBook.getBook() != null;
  }
}
