package com.sombra.test.dzhus.authorbook.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorBookCommand {

  private Long id;

  private Long authorId;

  private Long bookId;

}
