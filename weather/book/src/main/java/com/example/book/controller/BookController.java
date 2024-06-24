package com.example.book.controller;


import com.example.book.entity.BookDTO;
import com.example.book.exception.InvalidNameFormatException;
import com.example.book.exception.NoResourceFoundException;
import com.example.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Add a new book to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entry created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "ID is provided in the request body")
    })
    @PostMapping
    public ResponseEntity addBook(@RequestBody BookDTO book) {
        logger.info("POST /book \"" + book.getTitle() + "\"");
        if (book.getId() != null) {
            logger.warn("POST /book Create request contains id, bad request.");
            return new ResponseEntity<>("Book object should not have an id.", HttpStatus.BAD_REQUEST);
        }
        BookDTO result = bookService.addBook(book);
        logger.info("POST /book CREATED id: " + result.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Search book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "ID is not numeric"),
            @ApiResponse(responseCode = "404", description = "Result not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable(name = "id") int id)
            throws NoResourceFoundException {
        logger.info("GET /book/" + id);
        BookDTO book = bookService.findBookByBookId(id);
        logger.info("GET /book/" + id + " OK");
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Search book by author id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = BookDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Author ID is not numeric")
    })
    @GetMapping(value = "/search", params = "author_id")
    public ResponseEntity<List<BookDTO>> findBookByAuthorId(@RequestParam(name = "author_id") int id) {
        logger.info("GET /book/search author_id=" + id);
        List<BookDTO> result = bookService.findBookByAuthorId(id);
        logger.info("GET /book/search author=" + id + " OK " + result.size() + " results");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Search book by author full name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = BookDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Name is not in the format [firstName]+[lastName]")

    })
    @GetMapping(value = "/search", params = "author")
    public ResponseEntity<List<BookDTO>> findBookByAuthorName(@RequestParam(name = "author") String authorName)
            throws InvalidNameFormatException {
        logger.info("GET /book/search author=" + authorName);
        String[] fn_ln = authorName.split(" ");
        if (fn_ln.length != 2) {
            throw new InvalidNameFormatException();
        }
        List<BookDTO> result = bookService.findBookByAuthorName(fn_ln[0], fn_ln[1]);
        logger.info("GET /book/search author=" + authorName + " OK " + result.size() + " results");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Search book by title")
    @GetMapping(value = "/search", params = "title")
    public ResponseEntity<List<BookDTO>> findBookByTitle(@RequestParam(name = "title")String title) {
        logger.info("GET /book/search title=" + title);
        List<BookDTO> result = bookService.findBookByTitle(title);
        logger.info("GET /book/search title=" + title + " OK " + result.size() + " results");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Update book with given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "ID is not numeric"),
            @ApiResponse(responseCode = "404", description = "Target book doesn't exist")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(name = "id") int id, @RequestBody BookDTO book)
            throws NoResourceFoundException {
        logger.info("PUT /book/" + id);
        BookDTO result = bookService.updateBook(id, book);
        logger.info("PUT /book/" + id + " OK");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Operation(summary = "Delete book with given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "ID is not numeric")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable(name = "id") int id) {
        logger.info("DELETE /book/" + id);
        bookService.deleteByBookId(id);
        logger.info("DELETE /book/" + id + " OK");
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
