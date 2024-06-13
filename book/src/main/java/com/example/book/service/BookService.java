package com.example.book.service;

import com.example.book.entity.BookDTO;
import com.example.book.exception.NoResourceFoundException;

import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO book);

    BookDTO findBookByBookId(int id) throws NoResourceFoundException;

    List<BookDTO> findBookByAuthorId(int authorId);

    List<BookDTO> findBookByAuthorName(String firstName, String lastName);

    void deleteByBookId(int id);

    // public int deleteByBookTitle(String title);

    BookDTO updateBook(int bookId, BookDTO bookDTO) throws NoResourceFoundException;
}
