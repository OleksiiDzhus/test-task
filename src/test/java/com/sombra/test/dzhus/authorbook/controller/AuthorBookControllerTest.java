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

import com.sombra.test.dzhus.authorbook.command.AuthorBookCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import com.sombra.test.dzhus.authorbook.model.Book;
import com.sombra.test.dzhus.authorbook.service.AuthorBookService;
import com.sombra.test.dzhus.authorbook.service.AuthorService;
import com.sombra.test.dzhus.authorbook.service.BookService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AuthorBookControllerTest {

  @Mock
  private AuthorBookService authorBookService;

  @Mock
  private AuthorService authorService;

  @Mock
  private BookService bookService;

  private AuthorBookController authorBookController;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    authorBookController = new AuthorBookController(authorBookService,
        authorService, bookService);

    mockMvc = MockMvcBuilders.standaloneSetup(authorBookController)
        .setControllerAdvice(new ControllerExceptionHandler())
        .build();
  }

  @Test
  public void showAll() throws Exception {
    mockMvc.perform(get("/authorbook/showall"))
        .andExpect(status().isOk())
        .andExpect(view().name("/authorbook/authorbookshowall"))
        .andExpect(model().attributeExists("authorsBooks"));
  }

  @Test
  public void showAuthorById() throws Exception {
    AuthorBook authorBook = new AuthorBook();
    authorBook.setId(1L);

    when(authorBookService.findById(anyLong())).thenReturn(authorBook);

    mockMvc.perform(get("/authorbook/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("/authorbook/authorbookshow"));
  }

  @Test
  public void newAuthorBook() throws Exception {
    AuthorBook author = new AuthorBook();

    mockMvc.perform(get("/authorbook/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("authorbook/authorbookform"))
        .andExpect(model().attributeExists("authorBook"));
  }

  @Test
  public void updateAuthorBook() throws Exception {
    AuthorBookCommand command = new AuthorBookCommand();
    command.setId(2L);
    command.setAuthorId(2L);
    command.setBookId(2L);

    Author author = new Author();
    author.setId(5L);
    Book book = new Book();
    book.setId(5L);
    List<Author> authors = List.of(author);
    List<Book> books = List.of(book);

    when(authorService.getAuthors()).thenReturn(authors);
    when(bookService.getBooks()).thenReturn(books);
    when(authorBookService.saveAuthorBookCommand(any())).thenReturn(command);

    mockMvc.perform(post("/authorbook")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        .param("directions", "some directions")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/authorbook/2/show"));
  }

  @Test
  public void saveOrUpdate() throws Exception {
    AuthorBookCommand command = new AuthorBookCommand();
    command.setId(2L);
    command.setAuthorId(3L);
    command.setBookId(3L);

    Author author = new Author();
    author.setId(5L);
    Book book = new Book();
    book.setId(5L);
    List<Author> authors = new ArrayList<>();
    authors.add(author);
    List<Book> books = new ArrayList<>();
    books.add(book);

    when(authorService.getAuthors()).thenReturn(authors);
    when(bookService.getBooks()).thenReturn(books);
    when(authorBookService.saveAuthorBookCommand(any())).thenReturn(command);

    mockMvc.perform(post("/authorbook")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        .param("directions", "some directions")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/authorbook/2/show"));
  }

  @Test
  public void deleteById() throws Exception {
    mockMvc.perform(get("/authorbook/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/authorbook/showall"));

    verify(authorBookService, times(1)).deleteById(anyLong());
  }
}