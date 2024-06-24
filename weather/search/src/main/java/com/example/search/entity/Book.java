package com.example.search.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    private Integer id;
    private String title;
    @JsonProperty("author_ids")
    private Set<Integer> authorIds;
}
