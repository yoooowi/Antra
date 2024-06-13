package com.example.book;

import com.example.book.entity.BookDTO;
import com.example.book.exception.NoResourceFoundException;
import com.example.book.service.BookService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BookApplicationTests {

	@Autowired
	private BookService bookService;

	@Test
	public void testAddBook() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("Test Book");
		Set<Integer> authors = new HashSet<>();
		authors.add(1);
		authors.add(2);
		bookDTO.setAuthorIds(authors);

		BookDTO result = bookService.addBook(bookDTO);
		try{
			BookDTO saved = bookService.findBookByBookId(result.getId());
			assertEquals("Test Book", saved.getTitle());
			assertEquals(authors, saved.getAuthorIds());
		} catch (NoResourceFoundException ex) {
			fail("Can't find newly inserted object");
		}

	}

	@Test
	public void testUpdate(){
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("New Book");
		Set<Integer> authors = new HashSet<>();
		authors.add(3);
		authors.add(4);
		bookDTO.setAuthorIds(authors);

		try{
			bookService.updateBook(1, bookDTO);
			BookDTO saved = bookService.findBookByBookId(1);
			assertEquals("New Book", saved.getTitle());
			assertEquals(authors, saved.getAuthorIds());
		} catch (NoResourceFoundException ex) {
			fail("Can't find target entry");
		}

	}



}
