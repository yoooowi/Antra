package com.example.book.service.impl;


import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.entity.BookAuthor;
import com.example.book.entity.BookDTO;
import com.example.book.exception.NoResourceFoundException;
import com.example.book.repository.BookAuthorRepository;
import com.example.book.repository.BookRepository;
import com.example.book.service.BookService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {
        // We don't know id of the book until it has been inserted
        Book bookRes = bookRepository.save(new Book(null, bookDTO.getTitle(), new HashSet<>()));

        bookRes.setBookAuthors(bookDTO.getAuthorIds().stream().
                map(aid -> new BookAuthor(bookRes, new Author(aid, null, null, null)))
                .collect(Collectors.toSet()));

        // Save author information
        bookAuthorRepository.insertAuthors(bookRes);

        BookDTO result = BookDTO.get(bookRes);
        result.setAuthorIds(bookDTO.getAuthorIds());
        return result;
    }

    @Override
    public BookDTO findBookByBookId(int id) throws NoResourceFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoResourceFoundException("book", "id = " + id));

        return BookDTO.get(book);
    }

    @Override
    public List<BookDTO> findBookByAuthorId(int authorId) {
        return bookRepository.findAllBooksByAuthorId(authorId).stream().map(BookDTO::get).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBookByAuthorName(String firstName, String lastName) {
        return bookAuthorRepository.findBooksWrittenBy(firstName, lastName).stream().map(BookDTO::get).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBookByTitle(String title) {
        return bookRepository.findByTitle(title).stream().map(BookDTO::get).collect(Collectors.toList());
    }

    @Override
    public void deleteByBookId(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookDTO updateBook(int bookId, BookDTO bookDTO) throws NoResourceFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoResourceFoundException("book", "id = " + bookId));
        book.setTitle(bookDTO.getTitle());
        book.setBookAuthors(bookDTO.getAuthorIds().stream().map(aid -> {
            Author author = new Author();
            author.setId(aid);
            return new BookAuthor(book, author);
        }).collect(Collectors.toSet()));

        //bookAuthorRepository.insertAuthors(book);

        bookRepository.save(book);
        return BookDTO.get(book);
    }
}
