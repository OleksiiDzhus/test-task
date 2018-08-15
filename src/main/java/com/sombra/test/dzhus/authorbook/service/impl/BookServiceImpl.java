package com.sombra.test.dzhus.authorbook.service.impl;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.converter.BookCommandToBook;
import com.sombra.test.dzhus.authorbook.converter.BookToBookCommand;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchBookException;
import com.sombra.test.dzhus.authorbook.model.Book;
import com.sombra.test.dzhus.authorbook.repository.BookRepository;
import com.sombra.test.dzhus.authorbook.service.BookService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;
  private BookToBookCommand bookToBookCommand;
  private BookCommandToBook bookCommandToBook;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository,
      BookToBookCommand bookToBookCommand,
      BookCommandToBook bookCommandToBook) {
    this.bookRepository = bookRepository;
    this.bookToBookCommand = bookToBookCommand;
    this.bookCommandToBook = bookCommandToBook;
  }


  @Override
  public List<Book> getBooks() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(Long id) {
    Optional<Book> book = bookRepository.findById(id);

    if (!book.isPresent()) {
      throw new NoSuchBookException(id);
    }

    return book.get();
  }

  @Override
  public BookCommand findCommandById(Long id) {
    return bookToBookCommand.convert(findById(id));
  }

  @Override
  public BookCommand saveBookCommand(BookCommand command) {
    Book detachedBook = bookCommandToBook.convert(command);

    Book savedBook = bookRepository.save(detachedBook);
    log.debug("Saved book id: " + savedBook.getId());

    return bookToBookCommand.convert(savedBook);
  }

  @Override
  public void deleteById(Long idToDelete) {
    bookRepository.deleteById(idToDelete);
  }

  @Override
  public Map<String, Long> countBooksByGenre() {
    return bookRepository.findAll()
        .stream()
        .filter(i -> Objects.nonNull(i.getGenre()))
        .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));
  }
}
