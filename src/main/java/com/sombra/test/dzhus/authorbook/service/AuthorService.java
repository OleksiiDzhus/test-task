package com.sombra.test.dzhus.authorbook.service;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import java.util.List;

public interface AuthorService {

  List<Author> getAuthors();

  Author findById(Long id);

  AuthorCommand findCommandById(Long id);

  AuthorCommand saveAuthorCommand(AuthorCommand command);

  void deleteById(Long idToDelete);

}
