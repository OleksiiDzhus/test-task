package com.sombra.test.dzhus.authorbook.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import com.sombra.test.dzhus.authorbook.model.Book;
import com.sombra.test.dzhus.authorbook.service.impl.AuthorServiceImpl;
import com.sombra.test.dzhus.authorbook.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorBookCommandToAuthorBookTest {

  private static final Long ID_VALUE = 1L;
  private static final Long AUTHOR_ID = 1L;
  private static final Long BOOK_ID = 1L;

  @Mock
  private AuthorServiceImpl authorService;
  @Mock
  private BookServiceImpl bookService;

  private AuthorBookCommandToAuthorBook converter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    converter = new AuthorBookCommandToAuthorBook(authorService, bookService);
  }

  @Test
  public void testNullObject() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new AuthorBookCommand()));
  }

  @Test
  public void convert() throws Exception {
    AuthorBookCommand authorBookCommand = new AuthorBookCommand();
    authorBookCommand.setId(ID_VALUE);
    authorBookCommand.setAuthorId(AUTHOR_ID);
    authorBookCommand.setBookId(BOOK_ID);

    Author author = new Author();
    author.setId(1L);
    Book book = new Book();
    book.setId(1L);

    when(authorService.findById(anyLong())).thenReturn(author);
    when(bookService.findById(anyLong())).thenReturn(book);

    AuthorBook authorBook = converter.convert(authorBookCommand);

    assertEquals(ID_VALUE, authorBook.getId());
    assertEquals(AUTHOR_ID, authorBook.getAuthor().getId());
    assertEquals(BOOK_ID, authorBook.getBook().getId());
  }
}