package com.sombra.test.dzhus.authorbook.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.model.enums.Gender;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class AuthorCommandToAuthorTest {

  private static final Long ID_VALUE = 1L;
  private static final String NAME = "Test";
  private static final Gender GENDER = Gender.male;
  private static final Date BORN = new Date();

  private AuthorCommandToAuthor converter;

  @Before
  public void setUp() throws Exception {
    converter = new AuthorCommandToAuthor();
  }

  @Test
  public void testNullObject() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new AuthorCommand()));
  }

  @Test
  public void convert() {
    AuthorCommand authorCommand = new AuthorCommand();
    authorCommand.setId(ID_VALUE);
    authorCommand.setName(NAME);
    authorCommand.setGender(GENDER);
    authorCommand.setBorn(BORN);

    Author author = converter.convert(authorCommand);

    assertEquals(ID_VALUE, author.getId());
    assertEquals(NAME, author.getName());
    assertEquals(GENDER, author.getGender());
    assertEquals(BORN, author.getBorn());
  }
}