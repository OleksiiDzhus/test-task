package com.sombra.test.dzhus.authorbook.converter;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.model.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {

  @Override
  public Book convert(BookCommand bookCommand) {
    if (bookCommand == null) {
      return null;
    }

    final Book book = new Book();
    book.setId(bookCommand.getId());
    book.setName(bookCommand.getName());
    book.setPublished(bookCommand.getPublished());
    book.setGenre(bookCommand.getGenre());
    book.setRating(bookCommand.getRating());

    return book;
  }
}
