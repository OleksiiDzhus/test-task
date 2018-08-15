package com.sombra.test.dzhus.authorbook.service.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.converter.BookCommandToBook;
import com.sombra.test.dzhus.authorbook.converter.BookToBookCommand;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchAuthorException;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchBookException;
import com.sombra.test.dzhus.authorbook.model.Book;
import com.sombra.test.dzhus.authorbook.repository.BookRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookServiceImplTest {

  private BookServiceImpl bookService;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookToBookCommand bookToBookCommand;

  @Mock
  private BookCommandToBook bookCommandToBook;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    bookService = new BookServiceImpl(bookRepository,
        bookToBookCommand, bookCommandToBook);
  }

  @Test
  public void findById() {
    Book book = new Book();
    book.setId(1L);

    Optional<Book> authorBookOptional = Optional.of(book);

    when(bookRepository.findById(anyLong())).thenReturn(authorBookOptional);

    Book bookReturned = bookService.findById(1L);

    assertNotNull("Null book returned", bookReturned);
    verify(bookRepository, times(1)).findById(anyLong());
    verify(bookRepository, never()).findAll();
  }

  @Test(expected = NoSuchBookException.class)
  public void getAuthorBookByIdTestNotFound() throws Exception {
    Optional<Book> authorBookOptional = Optional.empty();

    when(bookRepository.findById(anyLong())).thenReturn(authorBookOptional);

    Book bookReturned = bookService.findById(1L);
  }

  @Test
  public void findCommandById() {
    Book book = new Book();
    book.setId(1L);
    Optional<Book> authorOptional = Optional.of(book);

    when(bookRepository.findById(anyLong())).thenReturn(authorOptional);

    BookCommand authorBookCommand = new BookCommand();
    authorBookCommand.setId(1L);

    when(bookToBookCommand.convert(any())).thenReturn(authorBookCommand);

    BookCommand commandById = bookService.findCommandById(1L);

    assertNotNull("Null book returned", commandById);
    verify(bookRepository, times(1)).findById(anyLong());
    verify(bookRepository, never()).findAll();
  }

  @Test
  public void deleteById() {
    Long idToDelete = 2L;

    bookService.deleteById(idToDelete);

    verify(bookRepository, times(1)).deleteById(anyLong());
  }
}