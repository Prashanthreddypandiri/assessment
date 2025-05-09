package com.prodapt.assesment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prodapt.assesment.dto.BookDTO;
import com.prodapt.assesment.entity.Book;
import com.prodapt.assesment.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> searchBooks(String title, String author, Integer publicationYear, String isbn) {
        List<Book> books;
        if (isbn != null) {
            books = List.of(bookRepository.findByIsbn(isbn));
        } else if (title != null) {
            books = bookRepository.findByTitleContaining(title);
        } else if (author != null) {
            books = bookRepository.findByAuthorContaining(author);
        } else if (publicationYear != null) {
            books = bookRepository.findByPublicationYear(publicationYear);
        } else {
            books = bookRepository.findAll();
        }

        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), book.getLanguage(), book.isAvailability());
    }
}