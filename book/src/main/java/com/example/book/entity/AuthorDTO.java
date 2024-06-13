package com.example.book.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDTO {

    private Integer id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    public static AuthorDTO get(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.id = author.getId();
        authorDTO.firstName = author.getFirstName();
        authorDTO.lastName = author.getLastName();
        return authorDTO;
    }

}
