package com.sombra.test.dzhus.authorbook.converter;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AuthorCommandToAuthor implements Converter<AuthorCommand, Author> {

  @Synchronized
  @Nullable
  @Override
  public Author convert(AuthorCommand authorCommand) {
    if (authorCommand == null) {
      return null;
    }

    final Author author = new Author();

    author.setId(authorCommand.getId());
    author.setName(authorCommand.getName());
    author.setGender(authorCommand.getGender());
    author.setBorn(authorCommand.getBorn());

    return author;
  }
}
