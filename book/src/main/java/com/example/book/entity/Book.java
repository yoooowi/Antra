package com.example.book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "books_book_id_seq", allocationSize = 1)
    @Column(name = "book_id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<BookAuthor> bookAuthors;

    public Book() {
        this.bookAuthors = new HashSet<>();
    }

    public void setBookAuthors(Set<BookAuthor> bookAuthors) {
        this.bookAuthors.clear();
        for (BookAuthor ba : bookAuthors) {
            this.bookAuthors.add(ba);
        }
    }
}
