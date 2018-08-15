package com.sombra.test.dzhus.authorbook.converter;

import static org.junit.Assert.*;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.model.enums.Gender;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class AuthorToAuthorCommandTest {

  private static final Long ID_VALUE = 1L;
  private static final String NAME = "Test";
  private static final Gender GENDER = Gender.male;
  private static final Date BORN = new Date();

  private AuthorToAuthorCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new AuthorToAuthorCommand();
  }

  @Test
  public void testNullObject() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new Author()));
  }

  @Test
  public void convert() {
    Author author = new Author();
    author.setId(ID_VALUE);
    author.setName(NAME);
    author.setGender(GENDER);
    author.setBorn(BORN);

    AuthorCommand authorCommand = converter.convert(author);

    assertEquals(ID_VALUE, authorCommand.getId());
    assertEquals(NAME, authorCommand.getName());
    assertEquals(GENDER, authorCommand.getGender());
    assertEquals(BORN, authorCommand.getBorn());
  }
}