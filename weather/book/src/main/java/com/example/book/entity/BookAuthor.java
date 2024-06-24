package com.example.book.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books_authors")
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ba_seq")
    @SequenceGenerator(name = "ba_seq", sequenceName = "books_authors_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

//    @Id
//    @Column(name = "bid")
//    private int bookId;
//
//    @Id
//    @Column(name = "aid")
//    private int authorId;

    @ManyToOne
    @JoinColumn(name = "aid")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "bid")
    private Book book;

    public BookAuthor(Book book, Author author) {
        this.book = book;
        this.author = author;
    }

    public Integer getAuthorId() {
        return author == null ? null : author.getId();
    }
}
