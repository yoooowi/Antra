package com.example.book.repository.impl;

import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.entity.BookAuthor;
import com.example.book.repository.BookAuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookAuthorRepositoryImpl implements BookAuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insertAuthors(Book book) {
        int bookId = book.getId();
        //BookAuthor ba;
        for (BookAuthor a : book.getBookAuthors()) {
            entityManager.persist(a);
        }
    }

    @Override
    public List<Book> findBooksWrittenBy(String firstName, String lastName) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b JOIN BookAuthor a ON b.id = a.book.id WHERE a.author.firstName = :fn AND a.author.lastName = :ln",
                Book.class
        );

        query.setParameter("fn", firstName);
        query.setParameter("ln", lastName);
        return query.getResultList();
    }
}
