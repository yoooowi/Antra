package com.example.book.repository;

import com.example.book.entity.Book;

import java.util.List;

public interface BookAuthorRepository {

    void insertAuthors(Book book);

    List<Book> findBooksWrittenBy(String firstName, String lastName);

}
