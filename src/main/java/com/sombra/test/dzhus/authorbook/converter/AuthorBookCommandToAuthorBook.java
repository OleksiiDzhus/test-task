package com.sombra.test.dzhus.authorbook.converter;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import com.sombra.test.dzhus.authorbook.model.Book;
import com.sombra.test.dzhus.authorbook.service.AuthorService;
import com.sombra.test.dzhus.authorbook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorBookCommandToAuthorBook implements Converter<AuthorBookCommand, AuthorBook> {

  private AuthorService authorService;
  private BookService bookService;

  @Autowired
  public AuthorBookCommandToAuthorBook(AuthorService authorService,
      BookService bookService) {
    this.authorService = authorService;
    this.bookService = bookService;
  }

  @Override
  public AuthorBook convert(AuthorBookCommand authorBookCommand) {
    AuthorBook authorBook = new AuthorBook();

    authorBook.setId(authorBookCommand.getId());
    Author author = authorService.findById(authorBookCommand.getAuthorId());
    authorBook.setAuthor(author);
    Book book = bookService.findById(authorBookCommand.getBookId());
    authorBook.setBook(book);

    return authorBook;
  }
}
