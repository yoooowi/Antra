package com.example.book.service.impl;

import com.example.book.entity.Author;
import com.example.book.entity.AuthorDTO;
import com.example.book.exception.NoResourceFoundException;
import com.example.book.repository.AuthorRepository;
import com.example.book.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        return AuthorDTO.get(authorRepository.save(author));
    }

    @Override
    public AuthorDTO findAuthorById(int id) throws NoResourceFoundException {
        return AuthorDTO.get(authorRepository.findById(id).
                orElseThrow(() -> new NoResourceFoundException("Author", "id=" + id)));
    }

    @Override
    public List<AuthorDTO> findAuthorByName(String firstName, String lastName) {
        Author authorExample = new Author();
        authorExample.setFirstName(firstName);
        authorExample.setLastName(lastName);
        List<Author> result = authorRepository.findAll(Example.of(authorExample));
        return result.stream().map(AuthorDTO::get).collect(Collectors.toList());
    }
}
