package com.sombra.test.dzhus.authorbook.controller;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.service.BookService;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class BookController {

  private static final String BOOK_BOOKFORM_URL = "book/bookform";

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/book/{id}/show")
  public String showBookById(@PathVariable String id, Model model) {

    model.addAttribute("book", bookService.findById(Long.valueOf(id)));

    return "book/bookshow";
  }

  @GetMapping("book/new")
  public String newBook(Model model) {
    model.addAttribute("book", new BookCommand());

    return "book/bookform";
  }

  @GetMapping("book/{id}/update")
  public String updateBook(@PathVariable String id, Model model) {
    model.addAttribute("book",
        bookService.findCommandById(Long.valueOf(id)));
    return BOOK_BOOKFORM_URL;
  }

  @PostMapping("/book")
  public String saveOrUpdate(@Valid @ModelAttribute("book") BookCommand
      command,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {

      bindingResult.getAllErrors().forEach(objectError -> {
        log.debug(objectError.toString());
      });

      return BOOK_BOOKFORM_URL;
    }

    BookCommand savedCommand = bookService.saveBookCommand(command);

    return "redirect:/book/" + savedCommand.getId() + "/show";
  }

  @GetMapping("book/{id}/delete")
  public String deleteById(@PathVariable String id) {

    log.debug("Deleting id: " + id);

    bookService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }

  @GetMapping("/book/calculate")
  public String calculateBooksByGenre(Model model) {
    model.addAttribute("numberOfBooksByGenre", bookService.countBooksByGenre());

    return "/book/countbooksbygenre";
  }

  @GetMapping("/book/activeauthorsbooks")
  public String getBooksWithActiveAuthors(Model model) {
    model.addAttribute("activeAuthorsBooks", bookService.booksWithActiveAuthors());

    return "/book/activeauthorsbooks";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Date.class,
        new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
  }
}