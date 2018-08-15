package com.sombra.test.dzhus.authorbook.service.impl;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.converter.AuthorBookCommandToAuthorBook;
import com.sombra.test.dzhus.authorbook.converter.AuthorBookToAuthorBookCommand;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchAuthorBookException;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import com.sombra.test.dzhus.authorbook.repository.AuthorBookRepository;
import com.sombra.test.dzhus.authorbook.service.AuthorBookService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthorBookServiceImpl implements AuthorBookService {

  private AuthorBookRepository authorBookRepository;
  private AuthorBookCommandToAuthorBook authorBookCommandToAuthorBook;
  private AuthorBookToAuthorBookCommand authorBookToAuthorBookCommand;

  @Autowired
  public AuthorBookServiceImpl(
      AuthorBookRepository authorBookRepository,
      AuthorBookCommandToAuthorBook authorBookCommandToAuthorBook,
      AuthorBookToAuthorBookCommand authorBookToAuthorBookCommand) {
    this.authorBookRepository = authorBookRepository;
    this.authorBookCommandToAuthorBook = authorBookCommandToAuthorBook;
    this.authorBookToAuthorBookCommand = authorBookToAuthorBookCommand;
  }

  @Override
  public List<AuthorBook> getAuthorBooks() {
    return authorBookRepository.findAll();
  }

  @Override
  public AuthorBook findById(Long id) {
    Optional<AuthorBook> authorBook = authorBookRepository.findById(id);

    if (!authorBook.isPresent()) {
      throw new NoSuchAuthorBookException(id);
    }

    return authorBook.get();
  }

  @Override
  @Transactional
  public AuthorBookCommand findCommandById(Long id) {
    return authorBookToAuthorBookCommand.convert(findById(id));
  }

  @Override
  @Transactional
  public AuthorBookCommand saveAuthorBookCommand(AuthorBookCommand command) {
    AuthorBook detachedAuthorBook = authorBookCommandToAuthorBook.convert(command);

    AuthorBook savedAuthorBook = authorBookRepository.save(detachedAuthorBook);
    log.debug("Saved author id: " + savedAuthorBook.getId());

    return authorBookToAuthorBookCommand.convert(savedAuthorBook);
  }

  @Override
  public void deleteById(Long idToDelete) {
    authorBookRepository.deleteById(idToDelete);
  }
}
