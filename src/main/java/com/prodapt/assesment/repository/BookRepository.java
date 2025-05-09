package com.prodapt.assesment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.assesment.entity.Book;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
    List<Book> findByPublicationYear(int year);
    Book findByIsbn(String isbn);
}
