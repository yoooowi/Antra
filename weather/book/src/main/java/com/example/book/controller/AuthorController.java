package com.example.book.controller;

import com.example.book.entity.AuthorDTO;
import com.example.book.exception.InvalidNameFormatException;
import com.example.book.exception.NoResourceFoundException;
import com.example.book.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO author) {
        logger.info("POST /author " + author.getFirstName() + " " + author.getLastName());
        AuthorDTO result = authorService.addAuthor(author);
        logger.info("POST /author " + result.getId() + " CREATED");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable int id)
            throws NoResourceFoundException {
        logger.info("GET /author/" + id);
        AuthorDTO result = authorService.findAuthorById(id);
        logger.info("GET /author/" + id + " OK");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<AuthorDTO>> findAuthorByName(@RequestParam(name = "name") String authorName)
            throws InvalidNameFormatException {
        logger.info("GET /author?name=" + authorName);
        String[] names = authorName.split(" ");
        if (names.length != 2) {
            throw new InvalidNameFormatException();
        }
        List<AuthorDTO> result = authorService.findAuthorByName(names[0], names[1]);
        logger.info("GET /author?name=" + authorName + " " + result.size() + " results");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
