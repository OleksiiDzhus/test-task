package com.sombra.test.dzhus.authorbook.command;

import com.sombra.test.firsttry.model.enums.Gender;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorCommand {

  private Long id;

  private String name;

  private Gender gender;

  private Date born;

}
