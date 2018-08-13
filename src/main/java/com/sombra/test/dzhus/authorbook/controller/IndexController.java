package com.sombra.test.dzhus.authorbook.controller;

import com.sombra.test.dzhus.authorbook.service.AuthorService;
import com.sombra.test.dzhus.authorbook.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

  private AuthorService authorService;
  private BookService bookService;

  @Autowired
  public IndexController(AuthorService authorService, BookService bookService) {
    this.authorService = authorService;
    this.bookService = bookService;
  }

  @RequestMapping({"", "/", "index"})
  public String getIndexPage(Model model) {
    log.debug("Getting index page");

    model.addAttribute("authors", authorService.getAuthors());
    model.addAttribute("books", bookService.getBooks());

    return "index";
  }
}
