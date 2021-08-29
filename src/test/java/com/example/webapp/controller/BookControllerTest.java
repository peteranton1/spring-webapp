package com.example.webapp.controller;

import com.example.webapp.exception.BookMismatchException;
import com.example.webapp.exception.BookNotFoundException;
import com.example.webapp.model.Book;
import com.example.webapp.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BookControllerTest extends BaseTestCase {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController underTest;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static String BASEURL = "/api/books";

    private static final long ID_1 = 1L;
    private static final String TEST_AUTHOR_1 = "Test Author 1";
    private static final String TEST_TITLE_1 = "Test Title 1";
    private static Book BOOK1 = new Book(
            ID_1,
            TEST_AUTHOR_1,
            TEST_TITLE_1);

    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules();
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(underTest)
                .build();
    }

    @Test
    void findAll_When_Empty() throws Exception {
        doReturn(ImmutableList.of())
                .when(bookRepository)
                .findAll();
        String url = BASEURL + "";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void findAll_When_1Item() throws Exception {
        doReturn(ImmutableList.of(BOOK1))
                .when(bookRepository)
                .findAll();
        String url = BASEURL + "";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void findByTitle_When_Missing() throws Exception {
        doReturn(ImmutableList.of())
                .when(bookRepository)
                .findByTitle(TEST_TITLE_1);
        String url = BASEURL + "/title/" + TEST_TITLE_1;
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void findByTitle_When_Available() throws Exception {
        doReturn(ImmutableList.of(BOOK1))
                .when(bookRepository)
                .findByTitle(TEST_TITLE_1);
        String url = BASEURL + "/title/" + TEST_TITLE_1;
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void findOne_When_Missing() {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + ID_1;
        Exception exception = assertThrows(
                NestedServletException.class,
                () -> mockMvc
                        .perform(MockMvcRequestBuilders
                                .get(url))
        );
        String expected = "Book Not found: 1";
        Throwable cause = exception.getCause();
        String message = cause.getMessage();
        assertTrue(cause instanceof BookNotFoundException);
        assertEquals(message, expected);
    }

    @Test
    void findOne_When_Found() throws Exception {
        doReturn(Optional.of(BOOK1))
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + ID_1;
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void createBook_When_Valid() throws Exception {
        doReturn(BOOK1)
                .when(bookRepository)
                .save(BOOK1);
        String url = BASEURL + "";
        String content = objectMapper
                .writeValueAsString(BOOK1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status()
                        .is(HttpStatus.CREATED.value()));
    }

    @Test
    void deleteBook_When_Valid() throws Exception {
        doReturn(Optional.of(BOOK1))
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + ID_1;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(url)
                )
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void deleteBook_When_NotFound() {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + ID_1;
        Exception exception = assertThrows(
                NestedServletException.class,
                () -> mockMvc
                        .perform(MockMvcRequestBuilders
                                .delete(url))
        );
        String expected = "Book Not found: 1";
        Throwable cause = exception.getCause();
        String message = cause.getMessage();
        assertTrue(cause instanceof BookNotFoundException);
        assertEquals(message, expected);
    }

    @Test
    void updateBook_When_Valid() throws Exception {
        doReturn(Optional.of(BOOK1))
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + ID_1;
        String content = objectMapper
                .writeValueAsString(BOOK1);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status()
                        .is(HttpStatus.OK.value()));
    }

    @Test
    void updateBook_When_Invalid() throws Exception {
        doReturn(Optional.of(BOOK1))
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + 2L;
        String content = objectMapper
                .writeValueAsString(BOOK1);
        Exception exception = assertThrows(
                NestedServletException.class,
                () -> mockMvc
                        .perform(MockMvcRequestBuilders
                                .put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                        )
        );
        String expected = "Book id: 1, url id: 2";
        Throwable cause = exception.getCause();
        String message = cause.getMessage();
        assertTrue(cause instanceof BookMismatchException);
        assertEquals(message, expected);
    }

    @Test
    void updateBook_When_NotFound() throws Exception {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(ID_1);
        String url = BASEURL + "/" + ID_1;
        String content = objectMapper
                .writeValueAsString(BOOK1);
        Exception exception = assertThrows(
                NestedServletException.class,
                () -> mockMvc
                        .perform(MockMvcRequestBuilders
                                .put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                        )
        );
        String expected = "Book Not found: 1";
        Throwable cause = exception.getCause();
        String message = cause.getMessage();
        assertTrue(cause instanceof BookNotFoundException);
        assertEquals(message, expected);
    }
}