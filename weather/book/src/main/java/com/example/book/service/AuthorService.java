package com.example.book.service;

import com.example.book.entity.AuthorDTO;
import com.example.book.exception.NoResourceFoundException;

import java.util.List;

public interface AuthorService {
    AuthorDTO addAuthor(AuthorDTO author);

    AuthorDTO findAuthorById(int id) throws NoResourceFoundException;

    List<AuthorDTO> findAuthorByName(String firstName, String lastName);

}
