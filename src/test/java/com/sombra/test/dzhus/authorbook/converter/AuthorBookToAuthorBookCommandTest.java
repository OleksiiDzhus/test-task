package com.sombra.test.dzhus.authorbook.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import com.sombra.test.dzhus.authorbook.model.Book;
import org.junit.Before;
import org.junit.Test;

public class AuthorBookToAuthorBookCommandTest {

  private final static Long ID_VALUE = 1L;
  private static final Long AUTHOR_ID = 1L;
  private static final Long BOOK_ID = 1L;

  private AuthorBookToAuthorBookCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new AuthorBookToAuthorBookCommand();
  }

  @Test
  public void testNullObject() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new AuthorBook()));
  }

  @Test
  public void convert() {
    AuthorBook authorBook = new AuthorBook();

    Author author = new Author();
    author.setId(AUTHOR_ID);
    Book book = new Book();
    book.setId(BOOK_ID);

    authorBook.setId(ID_VALUE);
    authorBook.setAuthor(author);
    authorBook.setBook(book);

    AuthorBookCommand command = converter.convert(authorBook);

    assertEquals(ID_VALUE, command.getId());
    assertEquals(AUTHOR_ID, command.getAuthorId());
    assertEquals(BOOK_ID, command.getBookId());
  }
}