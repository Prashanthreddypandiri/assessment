package com.prodapt.assesment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.assesment.dto.BookDTO;
import com.prodapt.assesment.entity.Book;
import com.prodapt.assesment.repository.BookRepository;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByTitle() {
        Book book = new Book("Java Programming", "John Doe", 2021, "1234567890", "English", true);
        when(bookRepository.findByTitleContaining("Java")).thenReturn(List.of(book));

        List<BookDTO> books = bookService.searchBooks("Java", null, null, null);

        assertEquals(1, books.size());
        assertEquals("Java Programming", books.get(0).getTitle());
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByAuthor() {
        Book book = new Book("Spring Boot Guide", "Jane Doe", 2022, "0987654321", "English", true);
        when(bookRepository.findByAuthorContaining("Jane")).thenReturn(List.of(book));

        List<BookDTO> books = bookService.searchBooks(null, "Jane", null, null);

        assertEquals(1, books.size());
        assertEquals("Jane Doe", books.get(0).getAuthor());
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByPublicationYear() {
        Book book = new Book("Effective Java", "Joshua Bloch", 2018, "1111111111", "English", true);
        when(bookRepository.findByPublicationYear(2018)).thenReturn(List.of(book));

        List<BookDTO> books = bookService.searchBooks(null, null, 2018, null);

        assertEquals(1, books.size());
        assertEquals(2018, books.get(0).getPublicationYear());
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenSearchedByIsbn() {
        Book book = new Book("Clean Code", "Robert C. Martin", 2008, "2222222222", "English", true);
        when(bookRepository.findByIsbn("2222222222")).thenReturn(book);

        List<BookDTO> books = bookService.searchBooks(null, null, null, "2222222222");

        assertEquals(1, books.size());
        assertEquals("Clean Code", books.get(0).getTitle());
    }

    @Test
    void searchBooks_ShouldReturnAllBooks_WhenNoFiltersAreApplied() {
        Book book1 = new Book("Book A", "Author A", 2000, "3333333333", "English", true);
        Book book2 = new Book("Book B", "Author B", 2010, "4444444444", "English", true);
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<BookDTO> books = bookService.searchBooks(null, null, null, null);

        assertEquals(2, books.size());
    }

    @Test
    void searchBooks_ShouldReturnEmptyList_WhenNoBooksMatch() {
        when(bookRepository.findByTitleContaining("NonExistent")).thenReturn(List.of());

        List<BookDTO> books = bookService.searchBooks("NonExistent", null, null, null);

        assertTrue(books.isEmpty());
    }
}