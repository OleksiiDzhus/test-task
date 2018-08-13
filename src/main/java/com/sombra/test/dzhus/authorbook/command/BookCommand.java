package com.sombra.test.dzhus.authorbook.command;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand {

  private Long id;

  private String name;

  private Date published;

  private String genre;

  private BigDecimal rating;

}
