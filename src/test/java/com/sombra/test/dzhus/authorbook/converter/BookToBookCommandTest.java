package com.sombra.test.dzhus.authorbook.converter;

import static org.junit.Assert.*;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.model.Book;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class BookToBookCommandTest {

  private static final Long ID_VALUE = 1L;
  private static final String NAME = "Test";
  private static final String GENRE = "GENRE";
  private static final Date PUBLISHED = new Date();
  private static final BigDecimal RATING = new BigDecimal(5);

  private BookToBookCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new BookToBookCommand();
  }

  @Test
  public void testNullObject() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new Book()));
  }

  @Test
  public void convert() {
    Book book = new Book();
    book.setId(ID_VALUE);
    book.setName(NAME);
    book.setGenre(GENRE);
    book.setPublished(PUBLISHED);
    book.setRating(RATING);

    BookCommand bookCommand = converter.convert(book);

    assertEquals(ID_VALUE, bookCommand.getId());
    assertEquals(NAME, bookCommand.getName());
    assertEquals(GENRE, bookCommand.getGenre());
    assertEquals(PUBLISHED, bookCommand.getPublished());
    assertEquals(RATING, bookCommand.getRating());
  }

}