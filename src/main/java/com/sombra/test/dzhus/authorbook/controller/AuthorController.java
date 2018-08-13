package com.sombra.test.dzhus.authorbook.controller;

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.service.AuthorService;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class AuthorController {

  private static final String AUTHOR_AUTHORFORM_URL = "author/authorform";

  private AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping("author/{id}/show")
  public String showAuthorById(@PathVariable String id, Model model) {

    model.addAttribute("author", authorService.findById(Long.valueOf(id)));

    return "/author/authorshow";
  }

  @GetMapping("author/new")
  public String newAuthor(Model model) {
    model.addAttribute("author", new AuthorCommand());

    return AUTHOR_AUTHORFORM_URL;
  }


  @GetMapping("/author/{id}/update")
  public String updateAuthor(@PathVariable String id, Model model) {
    model.addAttribute("author", authorService.findCommandById(Long.valueOf(id)));
    return AUTHOR_AUTHORFORM_URL;
  }

  @PostMapping("author")
  public String saveOrUpdate(@Valid @ModelAttribute("author") AuthorCommand command,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {

      bindingResult.getAllErrors().forEach(objectError -> {
        log.debug(objectError.toString());
      });

      return AUTHOR_AUTHORFORM_URL;
    }

    AuthorCommand savedCommand = authorService.saveAuthorCommand(command);

    return "redirect:/author/" + savedCommand.getId() + "/show";
  }

  @GetMapping("author/{id}/delete")
  public String deleteById(@PathVariable String id) {

    log.debug("Deleting id: " + id);

    authorService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ModelAndView handleNotFound(Exception exception) {

    log.error("Handling not found exception");
    log.error(exception.getMessage());

    ModelAndView modelAndView = new ModelAndView();

    modelAndView.setViewName("404error");
    modelAndView.addObject("exception", exception);

    return modelAndView;
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Date.class,
        new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
  }

}
