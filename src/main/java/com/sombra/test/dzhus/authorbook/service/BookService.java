package com.sombra.test.dzhus.authorbook.service;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.model.Book;
import java.util.List;
import java.util.Map;

public interface BookService {

  List<Book> getBooks();

  Book findById(Long id);

  BookCommand findCommandById(Long id);

  BookCommand saveBookCommand(BookCommand command);

  void deleteById(Long idToDelete);

  Map<String, Long> countBooksByGenre();

  List<Book> booksWithActiveAuthors();

}
