package com.sombra.test.dzhus.authorbook.converter;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.model.Book;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookToBookCommand implements Converter<Book, BookCommand> {

  @Synchronized
  @Nullable
  @Override
  public BookCommand convert(Book book) {
    if (book == null) {
      return null;
    }

    final BookCommand bookCommand = new BookCommand();
    bookCommand.setId(book.getId());
    bookCommand.setName(book.getName());
    bookCommand.setPublished(book.getPublished());
    bookCommand.setGenre(book.getGenre());
    bookCommand.setRating(book.getRating());

    return bookCommand;
  }
}
