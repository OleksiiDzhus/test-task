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

import com.sombra.test.dzhus.authorbook.command.AuthorCommand;
import com.sombra.test.dzhus.authorbook.model.Author;
import com.sombra.test.dzhus.authorbook.service.AuthorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AuthorControllerTest {

  @Mock
  private AuthorService authorService;

  private AuthorController authorController;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    authorController = new AuthorController(authorService);

    mockMvc = MockMvcBuilders.standaloneSetup(authorController)
        .setControllerAdvice(new ControllerExceptionHandler())
        .build();
  }

  @Test
  public void showAuthorById() throws Exception {
    Author author = new Author();
    author.setId(1L);

    when(authorService.findById(anyLong())).thenReturn(author);

    mockMvc.perform(get("/author/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("/author/authorshow"));
  }

  @Test
  public void newAuthor() throws Exception {
    Author author = new Author();

    mockMvc.perform(get("/author/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("author/authorform"))
        .andExpect(model().attributeExists("author"));
  }

  @Test
  public void updateAuthor() throws Exception {
    AuthorCommand command = new AuthorCommand();
    command.setId(2L);

    when(authorService.saveAuthorCommand(any())).thenReturn(command);

    mockMvc.perform(post("/author")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        .param("directions", "some directions")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/author/2/show"));
  }

  @Test
  public void saveOrUpdate() throws Exception {
    AuthorCommand command = new AuthorCommand();
    command.setId(2L);

    when(authorService.saveAuthorCommand(any())).thenReturn(command);

    mockMvc.perform(post("/author")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        .param("directions", "some directions")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/author/2/show"));
  }

  @Test
  public void deleteById() throws Exception {
    mockMvc.perform(get("/author/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));

    verify(authorService, times(1)).deleteById(anyLong());
  }

}