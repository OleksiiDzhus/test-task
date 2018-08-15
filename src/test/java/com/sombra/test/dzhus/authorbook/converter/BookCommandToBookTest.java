package com.sombra.test.dzhus.authorbook.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.model.Book;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class BookCommandToBookTest {

  private static final Long ID_VALUE = 1L;
  private static final String NAME = "Test";
  private static final String GENRE = "GENRE";
  private static final Date PUBLISHED = new Date();
  private static final BigDecimal RATING = new BigDecimal(5);

  private BookCommandToBook converter;

  @Before
  public void setUp() throws Exception {
    converter = new BookCommandToBook();
  }

  @Test
  public void testNullObject() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new BookCommand()));
  }

  @Test
  public void convert() {
    BookCommand bookCommand = new BookCommand();
    bookCommand.setId(ID_VALUE);
    bookCommand.setName(NAME);
    bookCommand.setGenre(GENRE);
    bookCommand.setPublished(PUBLISHED);
    bookCommand.setRating(RATING);

    Book book = converter.convert(bookCommand);

    assertEquals(ID_VALUE, book.getId());
    assertEquals(NAME, book.getName());
    assertEquals(GENRE, book.getGenre());
    assertEquals(PUBLISHED, book.getPublished());
    assertEquals(RATING, book.getRating());
  }
}