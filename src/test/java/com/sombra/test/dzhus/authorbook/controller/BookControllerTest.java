package com.sombra.test.dzhus.authorbook.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sombra.test.dzhus.authorbook.command.BookCommand;
import com.sombra.test.dzhus.authorbook.model.Book;
import com.sombra.test.dzhus.authorbook.service.BookService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BookControllerTest {

  @Mock
  private BookService bookService;

  private BookController bookController;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    bookController = new BookController(bookService);

    mockMvc = MockMvcBuilders.standaloneSetup(bookController)
        .setControllerAdvice(new ControllerExceptionHandler())
        .build();
  }

  @Test
  public void showBookById() throws Exception {
    Book book = new Book();
    book.setId(1L);

    when(bookService.findById(anyLong())).thenReturn(book);

    mockMvc.perform(get("/book/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("book/bookshow"))
        .andExpect(model().attributeExists("book"));
  }

  @Test
  @Ignore
  public void testBookNotFound() throws Exception {
    mockMvc.perform(get("/book/1/show"))
        .andExpect(status().isNotFound())
        .andExpect(view().name("404error"));
  }

  @Test
  public void newBook() throws Exception {
    Book book = new Book();

    mockMvc.perform(get("/book/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("book/bookform"))
        .andExpect(model().attributeExists("book"));
  }

  @Test
  public void updateBook() throws Exception {
    BookCommand command = new BookCommand();
    command.setId(2L);

    when(bookService.saveBookCommand(any())).thenReturn(command);

    mockMvc.perform(post("/book")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        .param("directions", "some directions")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/book/2/show"));
  }

  @Test
  public void saveOrUpdate() throws Exception {
    BookCommand command = new BookCommand();
    command.setId(2L);

    when(bookService.saveBookCommand(any())).thenReturn(command);

    mockMvc.perform(post("/book")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        .param("directions", "some directions")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/book/2/show"));
  }

  @Test
  public void deleteById() throws Exception {
    mockMvc.perform(get("/book/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));

    verify(bookService, times(1)).deleteById(anyLong());
  }
}