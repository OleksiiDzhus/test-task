package com.sombra.test.dzhus.authorbook.controller;

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.service.AuthorBookService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AuthorBookController {

  private static final String AUTHORBOOK_AUTHORBOOKFORM_URL = "authorbook/authorbookform";

  private AuthorBookService authorBookService;

  @Autowired
  public AuthorBookController(AuthorBookService authorService) {
    this.authorBookService = authorService;
  }

  @GetMapping("authorbook/showall")
  public String showAll(Model model) {

    model.addAttribute("authorsBooks", authorBookService.getAuthorBooks());

    return "/authorbook/authorbookshowall";
  }

  @GetMapping("authorbook/{id}/show")
  public String showAuthorById(@PathVariable String id, Model model) {

    model.addAttribute("authorBook", authorBookService.findById(Long.valueOf(id)));

    return "/authorbook/authorbookshow";
  }

  @GetMapping("authorbook/new")
  public String newAuthor(Model model) {
    model.addAttribute("authorBook", new AuthorBookCommand());

    return AUTHORBOOK_AUTHORBOOKFORM_URL;
  }


  @GetMapping("/authorbook/{id}/update")
  public String updateAuthor(@PathVariable String id, Model model) {
    model.addAttribute("authorBook", authorBookService.findCommandById(Long.valueOf(id)));
    return AUTHORBOOK_AUTHORBOOKFORM_URL;
  }

  @PostMapping("authorbook")
  public String saveOrUpdate(@Valid @ModelAttribute("authorBook") AuthorBookCommand command,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {

      bindingResult.getAllErrors().forEach(objectError -> {
        log.debug(objectError.toString());
      });

      return AUTHORBOOK_AUTHORBOOKFORM_URL;
    }

    AuthorBookCommand savedCommand = authorBookService.saveAuthorBookCommand(command);

    return "redirect:/authorbook/" + savedCommand.getId() + "/show";
  }

  @GetMapping("authorbook/{id}/delete")
  public String deleteById(@PathVariable String id) {

    log.debug("Deleting id: " + id);

    authorBookService.deleteById(Long.valueOf(id));
    return "redirect:/authorbook/showall";
  }


}
