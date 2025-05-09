package com.prodapt.assesment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.assesment.controller.BookController;
import com.prodapt.assesment.dto.BookDTO;
import com.prodapt.assesment.service.BookService;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByTitle() {
        BookDTO book = new BookDTO("Java Programming", "John Doe", 2021, "1234567890", "English", true);
        when(bookService.searchBooks("Java", null, null, null)).thenReturn(List.of(book));

        ResponseEntity<List<BookDTO>> response = bookController.searchBooks("Java", null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Java Programming", response.getBody().get(0).getTitle());
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByAuthor() {
        BookDTO book = new BookDTO("Spring Boot Guide", "Jane Doe", 2022, "0987654321", "English", true);
        when(bookService.searchBooks(null, "Jane", null, null)).thenReturn(List.of(book));

        ResponseEntity<List<BookDTO>> response = bookController.searchBooks(null, "Jane", null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Jane Doe", response.getBody().get(0).getAuthor());
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByPublicationYear() {
        BookDTO book = new BookDTO("Effective Java", "Joshua Bloch", 2018, "1111111111", "English", true);
        when(bookService.searchBooks(null, null, 2018, null)).thenReturn(List.of(book));

        ResponseEntity<List<BookDTO>> response = bookController.searchBooks(null, null, 2018, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(2018, response.getBody().get(0).getPublicationYear());
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByIsbn() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 2008, "2222222222", "English", true);
        when(bookService.searchBooks(null, null, null, "2222222222")).thenReturn(List.of(book));

        ResponseEntity<List<BookDTO>> response = bookController.searchBooks(null, null, null, "2222222222");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Clean Code", response.getBody().get(0).getTitle());
    }

    @Test
    void searchBooks_ShouldReturnAllBooks_WhenNoFiltersAreApplied() {
        BookDTO book1 = new BookDTO("Book A", "Author A", 2000, "3333333333", "English", true);
        BookDTO book2 = new BookDTO("Book B", "Author B", 2010, "4444444444", "English", true);
        when(bookService.searchBooks(null, null, null, null)).thenReturn(List.of(book1, book2));

        ResponseEntity<List<BookDTO>> response = bookController.searchBooks(null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void searchBooks_ShouldReturnNotFound_WhenNoBooksMatch() {
        when(bookService.searchBooks("NonExistent", null, null, null)).thenReturn(List.of());

        ResponseEntity<List<BookDTO>> response = bookController.searchBooks("NonExistent", null, null, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}