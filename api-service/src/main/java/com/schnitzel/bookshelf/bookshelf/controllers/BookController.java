package com.schnitzel.bookshelf.bookshelf.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schnitzel.bookshelf.bookshelf.models.Author;
import com.schnitzel.bookshelf.bookshelf.models.Book;
import com.schnitzel.bookshelf.bookshelf.services.BookService;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping({"/books", "/books/"})
	public ResponseEntity<List<Book>> getBooks(@RequestParam Optional<String> keyword, @RequestParam Optional<Long> limit) {
		if (keyword != null && keyword.isPresent() && StringUtils.hasText(keyword.get())) {
			return ResponseEntity.ok(bookService.findBy(keyword.get(), limit.isPresent() ? limit.get() : null));
		} else {
			return ResponseEntity.ok(bookService.findAll());
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> fetchBook(@PathVariable Optional<Long> id) {
		if (id != null && id.isPresent() && id.get() != null) {
			Optional<Book> book = bookService.fetch(id.get());
			if (book.isPresent()) {
				return ResponseEntity.ok(book.get());
			} else {
				return new ResponseEntity("Book was not found in bookshelf", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity("Illegal parameters were sent", HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping({"/books", "/books/"})
	public ResponseEntity<String> addBook(@RequestBody Book book) throws Exception {
		if (book != null && !isBlankString(book.getIsbn()) && !isBlankString(book.getName())
				&& !isBlankString(book.getAnnotation())) {
			try {
				String isbn = book.getIsbn().trim();
				String name = book.getName().trim();
				String annotation = book.getAnnotation().trim();

				try {
					bookService.addBook(isbn, name, annotation, book.getAuthors());
					return new ResponseEntity<>("Book was successfully added to bookshelf", HttpStatus.OK);
				} catch (Exception ex) {
					return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
				}
			} catch (Exception ex) {
				return new ResponseEntity("Illegal parameters were sent (missing fields)", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity("Illegal parameters were sent", HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/books/{bookId}")
	public ResponseEntity<String> updateBook(@PathVariable Long bookId, @RequestBody Book book) throws Exception {
		if (book != null && !isBlankString(book.getIsbn()) && !isBlankString(book.getName())
				&& !isBlankString(book.getAnnotation())) {
			try {
				String isbn = book.getIsbn().trim();
				String name = book.getName().trim();
				String annotation = book.getAnnotation().trim();

				try {
					bookService.updateBook(bookId, isbn, name, annotation, book.getAuthors());
					return new ResponseEntity<>("Book details were successfully updated", HttpStatus.OK);
				} catch (Exception ex) {
					return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
				}
			} catch (Exception ex) {
				return new ResponseEntity("Illegal parameters were sent (missing fields)", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity("Illegal parameters were sent", HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable Long bookId) throws Exception {
		try {
			bookService.deleteBook(bookId);
			return new ResponseEntity<>("Book was successfully deleted from bookshelf", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping({"/books/{bookId}/authors", "/books/{bookId}/authors/"})
	public ResponseEntity<String> addAuthor(@PathVariable Long bookId, @RequestBody Author author) throws Exception {
		if (author != null) {
			try {
				String firstName = author.getFirstName().trim();
				String lastName = author.getLastName().trim();

				try {
					bookService.addAuthor(bookId, firstName, lastName);
					return new ResponseEntity<>("Author was successfully added to book details", HttpStatus.OK);
				} catch (Exception ex) {
					return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
				}
			} catch (Exception ex) {
				return new ResponseEntity("Illegal parameters were sent (missing fields)", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity("Illegal parameters were sent", HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping({"/books/{bookId}/authors", "/books/{bookId}/authors/"})
	public ResponseEntity<List<Author>> getAuthors(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookService.findAuthors(bookId));
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/books/{bookId}/authors/{authorId}")
	public ResponseEntity<String> updateAuthor(@PathVariable Long bookId, @PathVariable Long authorId,
			@RequestBody Author author) throws Exception {
		if (author != null) {
			try {
				String firstName = author.getFirstName().trim();
				String lastName = author.getLastName().trim();

				try {
					bookService.updateAuthor(bookId, authorId, firstName, lastName);
					return new ResponseEntity<>("Author details were successfully updated", HttpStatus.OK);
				} catch (Exception ex) {
					return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
				}
			} catch (Exception ex) {
				return new ResponseEntity("Illegal parameters were sent (missing fields)", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity("Illegal parameters were sent", HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/books/{bookId}/authors/{authorId}")
	public ResponseEntity<String> deleteAuthor(@PathVariable Long bookId, @PathVariable Long authorId) throws Exception {
		try {
			bookService.deleteAuthor(bookId, authorId);
			return new ResponseEntity<>("Author was successfully deleted", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	public boolean isBlankString(String string) {
	    return string == null || string.isBlank();
	}

}
