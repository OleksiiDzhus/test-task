package com.sombra.test.dzhus.authorbook.service;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import java.util.List;

public interface AuthorBookService {

  List<AuthorBook> getAuthorBooks();

  AuthorBook findById(Long id);

  AuthorBookCommand findCommandById(Long id);

  AuthorBookCommand saveAuthorBookCommand(AuthorBookCommand command);

  void deleteById(Long idToDelete);

}
