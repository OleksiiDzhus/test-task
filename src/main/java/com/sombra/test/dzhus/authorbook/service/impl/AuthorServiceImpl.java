package com.sombra.test.dzhus.authorbook.service.impl;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.converter.AuthorCommandToAuthor;
import com.sombra.test.dzhus.authorbook.converter.AuthorToAuthorCommand;
import com.sombra.test.dzhus.authorbook.exceptions.NoSuchAuthorException;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.repository.AuthorRepository;
import com.sombra.test.dzhus.authorbook.service.AuthorService;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

  private AuthorRepository authorRepository;
  private AuthorToAuthorCommand authorToAuthorCommand;
  private AuthorCommandToAuthor authorCommandToAuthor;

  @Autowired
  public AuthorServiceImpl(AuthorRepository authorRepository,
      AuthorToAuthorCommand authorToAuthorCommand,
      AuthorCommandToAuthor authorCommandToAuthor) {
    this.authorRepository = authorRepository;
    this.authorToAuthorCommand = authorToAuthorCommand;
    this.authorCommandToAuthor = authorCommandToAuthor;
  }

  @Override
  public List<Author> getAuthors() {
    return authorRepository.findAll();
  }

  @Override
  public Author findById(Long id) {

    Optional<Author> author = authorRepository.findById(id);

    if (!author.isPresent()) {
      throw new NoSuchAuthorException(id);
    }

    return author.get();
  }

  @Override
  @Transactional
  public AuthorCommand findCommandById(Long id) {
    return authorToAuthorCommand.convert(findById(id));
  }

  @Override
  @Transactional
  public AuthorCommand saveAuthorCommand(AuthorCommand command) {
    Author detachedAuthor = authorCommandToAuthor.convert(command);

    Author savedAuthor = authorRepository.save(detachedAuthor);
    log.debug("Saved author id: " + savedAuthor.getId());

    return authorToAuthorCommand.convert(savedAuthor);
  }

  @Override
  public void deleteById(Long idToDelete) {
    authorRepository.deleteById(idToDelete);
  }

  @Override
  public List<Author> findOldAuthors() {
    return getAuthors().stream()
        .filter(i -> Objects.nonNull(i.getBorn()))
        .filter(i -> substractDates(i.getBorn()) > 55)
        .sorted(Comparator.comparing(Author::getBorn))
        .collect(Collectors.toList());
  }

  private int substractDates(Date dateToSubstract){
    LocalDate now = LocalDate.now();
    LocalDate dateToSubstractFormatted = dateToSubstract.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

    return Period.between(dateToSubstractFormatted, now).getYears();
  }
}
