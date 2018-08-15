package com.sombra.test.dzhus.authorbook.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sombra.test.dzhus.authorbook.service.AuthorService;
import com.sombra.test.dzhus.authorbook.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class IndexControllerTest {

  @Mock
  private AuthorService authorService;

  @Mock
  private BookService bookService;

  private IndexController indexController;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    indexController = new IndexController(authorService, bookService);
  }

  @Test
  public void getIndexPage() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }
}