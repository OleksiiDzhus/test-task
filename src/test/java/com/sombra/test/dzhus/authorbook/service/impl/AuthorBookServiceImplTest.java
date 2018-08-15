package com.sombra.test.dzhus.authorbook.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.converter.AuthorBookCommandToAuthorBook;
import com.sombra.test.dzhus.authorbook.converter.AuthorBookToAuthorBookCommand;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchAuthorBookException;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import com.sombra.test.dzhus.authorbook.repository.AuthorBookRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorBookServiceImplTest {

  private AuthorBookServiceImpl authorBookService;

  @Mock
  private AuthorBookRepository authorBookRepository;

  @Mock
  private AuthorBookCommandToAuthorBook authorBookCommandToAuthorBook;

  @Mock
  private AuthorBookToAuthorBookCommand authorBookToAuthorBookCommand;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    authorBookService = new AuthorBookServiceImpl(authorBookRepository,
        authorBookCommandToAuthorBook, authorBookToAuthorBookCommand);
  }

  @Test
  public void findById() {
    AuthorBook authorBook = new AuthorBook();
    authorBook.setId(1L);

    Optional<AuthorBook> authorBookOptional = Optional.of(authorBook);

    when(authorBookRepository.findById(anyLong())).thenReturn(authorBookOptional);

    AuthorBook authorBookReturned = authorBookService.findById(1L);

    assertNotNull("Null authorbook returned", authorBookReturned);
    verify(authorBookRepository, times(1)).findById(anyLong());
    verify(authorBookRepository, never()).findAll();
  }

  @Test(expected = NoSuchAuthorBookException.class)
  public void getAuthorBookByIdTestNotFound() throws Exception {
    Optional<AuthorBook> authorBookOptional = Optional.empty();

    when(authorBookRepository.findById(anyLong())).thenReturn(authorBookOptional);

    AuthorBook authorBookReturned = authorBookService.findById(1L);
  }

  @Test
  public void findCommandById() {
    AuthorBook authorBook = new AuthorBook();
    authorBook.setId(1L);
    Optional<AuthorBook> authorBookOptional = Optional.of(authorBook);

    when(authorBookRepository.findById(anyLong())).thenReturn(authorBookOptional);

    AuthorBookCommand authorBookCommand = new AuthorBookCommand();
    authorBookCommand.setId(1L);

    when(authorBookToAuthorBookCommand.convert(any())).thenReturn(authorBookCommand);

    AuthorBookCommand commandById = authorBookService.findCommandById(1L);

    assertNotNull("Null authorbook returned", commandById);
    verify(authorBookRepository, times(1)).findById(anyLong());
    verify(authorBookRepository, never()).findAll();
  }

  @Test
  public void deleteById() {
    Long idToDelete = 2L;

    authorBookService.deleteById(idToDelete);

    verify(authorBookRepository, times(1)).deleteById(anyLong());
  }
}