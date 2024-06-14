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
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @SequenceGenerator(name = "author_seq", sequenceName = "authors_author_id_seq", allocationSize = 1)
    @Column(name = "author_id")
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private final Set<BookAuthor> bookAuthors;

    public Author() {
        this.bookAuthors = new HashSet<>();
    }

    public void setBookAuthor(Set<BookAuthor> bookAuthors) {
        this.bookAuthors.clear();
        for (BookAuthor ba : bookAuthors) {
            this.bookAuthors.add(ba);
        }
    }
}
