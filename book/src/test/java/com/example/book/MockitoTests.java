package com.example.book;

import com.example.book.entity.Author;
import com.example.book.entity.AuthorDTO;
import com.example.book.exception.NoResourceFoundException;
import com.example.book.repository.AuthorRepository;
import com.example.book.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class MockitoTests {

    @MockBean
    AuthorRepository mockRepo;

    @Autowired
    private AuthorService authorService;

    @Test
    public void testFindAuthorById() {
        Author author = new Author();
        author.setId(0);
        author.setFirstName("John");
        author.setLastName("Smith");
        given(mockRepo.findById(0)).willReturn(Optional.of(author));
        try {
            AuthorDTO found = authorService.findAuthorById(0);
            assertEquals(0, found.getId());
            assertEquals("John", found.getFirstName());
            assertEquals("Smith", found.getLastName());
        } catch (NoResourceFoundException ex) {
            fail("Cannot find id");
        }
    }
}
