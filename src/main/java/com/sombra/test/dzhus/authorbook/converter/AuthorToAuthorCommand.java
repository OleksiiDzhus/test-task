package com.sombra.test.dzhus.authorbook.converter;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AuthorToAuthorCommand implements Converter<Author, AuthorCommand> {

  @Synchronized
  @Nullable
  @Override
  public AuthorCommand convert(Author author) {
    if (author == null) {
      return null;
    }

    final AuthorCommand authorCommand = new AuthorCommand();

    authorCommand.setId(author.getId());
    authorCommand.setName(author.getName());
    authorCommand.setBorn(author.getBorn());
    authorCommand.setGender(author.getGender());

    return authorCommand;
  }
}
