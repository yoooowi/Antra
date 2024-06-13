package com.example.book.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Integer id;
    private String title;
    @JsonProperty("author_ids")
    private Set<Integer> authorIds;

    public static BookDTO get(Book book) {
        BookDTO obj = new BookDTO(book.getId(), book.getTitle(), null);
        obj.authorIds = book.getBookAuthors() == null ? new HashSet<>() :
                book.getBookAuthors().stream().map(ba -> ba.getAuthorId()).collect(Collectors.toSet());
        return obj;
    }

//    public Book toBook(){
//        Set<BookAuthor> ba = null;
//
//        if (id != null) {
//            ba = authorIds == null ? new HashSet<>() :
//                    authorIds.stream().map(aid -> {
//                        Author author = new Author();
//                        author.setId(aid);
//                        return new Book(id, title, new BookAuthor())
//                    }).collect(Collectors.toSet());
//        }
//        return new Book(id, title, ba);
//    }

}
