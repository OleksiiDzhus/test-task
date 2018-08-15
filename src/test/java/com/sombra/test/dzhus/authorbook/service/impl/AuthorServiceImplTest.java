package com.sombra.test.dzhus.authorbook.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.converter.AuthorCommandToAuthor;
import com.sombra.test.dzhus.authorbook.converter.AuthorToAuthorCommand;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchAuthorException;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.repository.AuthorRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorServiceImplTest {

  private AuthorServiceImpl authorService;

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private AuthorToAuthorCommand authorToAuthorCommand;

  @Mock
  private AuthorCommandToAuthor authorCommandToAuthor;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    authorService = new AuthorServiceImpl(authorRepository,
        authorToAuthorCommand, authorCommandToAuthor);
  }

  @Test
  public void findById() {
    Author author = new Author();
    author.setId(1L);

    Optional<Author> authorOptional = Optional.of(author);

    when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

    Author authorBookReturned = authorService.findById(1L);

    assertNotNull("Null authorbook returned", authorBookReturned);
    verify(authorRepository, times(1)).findById(anyLong());
    verify(authorRepository, never()).findAll();
  }

  @Test(expected = NoSuchAuthorException.class)
  public void getAuthorBookByIdTestNotFound() throws Exception {
    Optional<Author> authorOptional = Optional.empty();

    when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

    Author authorReturned = authorService.findById(1L);
  }

  @Test
  public void findCommandById() {
    Author author = new Author();
    author.setId(1L);
    Optional<Author> authorOptional = Optional.of(author);

    when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

    AuthorCommand authorBookCommand = new AuthorCommand();
    authorBookCommand.setId(1L);

    when(authorToAuthorCommand.convert(any())).thenReturn(authorBookCommand);

    AuthorCommand commandById = authorService.findCommandById(1L);

    assertNotNull("Null author returned", commandById);
    verify(authorRepository, times(1)).findById(anyLong());
    verify(authorRepository, never()).findAll();
  }

  @Test
  public void deleteById() {
    Long idToDelete = 2L;

    authorService.deleteById(idToDelete);

    verify(authorRepository, times(1)).deleteById(anyLong());
  }
}