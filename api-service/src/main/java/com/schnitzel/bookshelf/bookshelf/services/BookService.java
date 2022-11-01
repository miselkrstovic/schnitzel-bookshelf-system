package com.schnitzel.bookshelf.bookshelf.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schnitzel.bookshelf.bookshelf.models.Author;
import com.schnitzel.bookshelf.bookshelf.models.Book;
import com.schnitzel.bookshelf.bookshelf.repositories.AuthorRepository;
import com.schnitzel.bookshelf.bookshelf.repositories.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {

	final private Long WITHOUT_LIMIT = Long.MAX_VALUE;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authorRepository;

	public final Comparator BOOK_COMPARATOR = new Comparator<Book>() {

		@Override
		public int compare(Book o1, Book o2) {
			return o1.getName().compareTo(o2.getName());
		}
		
	};
	
	public final Comparator AUTHOR_COMPARATOR = new Comparator<Author>() {

		@Override
		public int compare(Author o1, Author o2) {
			return o1.getFirstName().compareTo(o2.getFirstName());
		}
		
	};
	
	public boolean exists(String isbn) {
		Book book = bookRepository.findByIsbn(isbn);
		return book != null;
	}
	
	public List<Book> findAll() {
		return findBy(null, WITHOUT_LIMIT);
	}
	
	public List<Book> findBy(String keyword, Long limit) {
		Stream books;
		if (keyword == null) {
			books = bookRepository.findAll()					
					.stream()
					.sorted(BOOK_COMPARATOR)
					.limit(limit == null || limit < 0l ? WITHOUT_LIMIT : limit);

		} else {
			books = bookRepository.findAllByKeyword(keyword)
					.stream()
					.sorted(BOOK_COMPARATOR)
					.limit(limit == null || limit < 0l ? WITHOUT_LIMIT : limit);
		}
		return books.toList();
	}
	
	public Optional<Book> fetch(long id) {
		return bookRepository.findById(id);
	}
	
	@Transactional
	public void addBook(String isbn, String name, String annotation, Set<Author> authors) throws BookAlreadyExistsException {
		isbn = isbn.trim();
		name = name.trim();
		annotation = annotation.trim();
		
		if (!exists(isbn)) {
			LocalDateTime currentDate = LocalDateTime.now();
			Book book = new Book(isbn, name, annotation, currentDate, currentDate);
			Book refBook = bookRepository.save(book);
			
			if (authors != null && authors.size() > 0) {
				for (Author author : authors) {
					Author newAuthor = new Author(author.getFirstName(), author.getLastName());
					newAuthor = authorRepository.save(author);
					newAuthor.setBook(refBook);
					authorRepository.save(author);
				}
			}
		} else {
			throw new BookAlreadyExistsException("Book is already added");
		}
	}

	@Transactional
	public void updateBook(Long bookId, String isbn, String name, String annotation, Set<Author> authors) throws NoSuchRecordException {
		if (isbn != null && isbn.trim().length() > 0 &&
				name != null && name.trim().length() > 0 &&
				annotation != null && annotation.trim().length() > 0) {
			isbn = isbn.trim();
			name = name.trim();
			annotation = annotation.trim();
			
			Optional<Book> bookOpt = bookRepository.findById(bookId);
			if (bookOpt != null && bookOpt.get() != null) {
				Book book = bookOpt.get();
				
				LocalDateTime modifiedDate = LocalDateTime.now();
				book.setIsbn(isbn);
				book.setName(name);
				book.setAnnotation(annotation);
				book.setModified(modifiedDate);
				Book refBook = bookRepository.save(book);
				
				authorRepository.deleteAllByBookId(bookId);
				if (authors != null && authors.size() > 0) {
					for (Author author : authors) {
						Author newAuthor = new Author(author.getFirstName(), author.getLastName());
						newAuthor = authorRepository.save(author);
						newAuthor.setBook(refBook);
						authorRepository.save(author);
					}
				}
			} else {
				throw new NoSuchRecordException("No matching record found");
			}
		}
	}

	@Transactional
	public void deleteBook(Long bookId)  throws NoSuchRecordException {
		Optional<Book> bookOpt = bookRepository.findById(bookId);
		if (bookOpt != null && bookOpt.get() != null) {
			Book book = bookOpt.get();
			
			if (book.getAuthors().size() > 0) {
				authorRepository.deleteAllByBookId(bookId);
			}
			bookRepository.delete(book);
		} else {
			throw new NoSuchRecordException("No matching record found");
		}
	}
	
	@Transactional
	public void addAuthor(Long bookId, String firstName, String lastName) {
		if (firstName != null && firstName.trim().length() > 0 &&
				lastName != null && lastName.trim().length() > 0) { 
			Optional<Book> bookOpt = bookRepository.findById(bookId);
			if (bookOpt != null && bookOpt.get() != null) {
				Book book = bookOpt.get();
				
				Author author = new Author(firstName, lastName);
				author.setBook(book);
				book.getAuthors().add(author);
				
				authorRepository.save(author);
				bookRepository.save(book);
			}
		}
	}

	@Transactional
	public void updateAuthor(Long bookId, Long authorId, String firstName, String lastName) throws NoSuchRecordException {
		if (firstName != null && firstName.trim().length() > 0 &&
				lastName != null && lastName.trim().length() > 0) { 
			Optional<Book> bookOpt = bookRepository.findById(bookId);
			if (bookOpt != null && bookOpt.get() != null) {
				Book book = bookOpt.get();
				
				LocalDateTime modifiedDate = LocalDateTime.now();
				Optional<Author> authorOpt = authorRepository.findById(authorId);
				if (authorOpt != null && authorOpt.get() != null) {
					Author author = authorOpt.get();
					author.setFirstName(firstName);
					author.setLastName(lastName);
					book.getAuthors().add(author);
				
					authorRepository.save(author);
					bookRepository.save(book);
				} else {
					throw new NoSuchRecordException("No matching record found");
				}
			}
		}
	}

	@Transactional
	public void deleteAuthor(Long bookId, Long authorId)  throws NoSuchRecordException {
		Optional<Book> bookOpt = bookRepository.findById(bookId);
		if (bookOpt != null && bookOpt.get() != null) {
			Book book = bookOpt.get();
			
			Optional<Author> authorOpt = authorRepository.findById(authorId);
			if (authorOpt != null && authorOpt.get() != null) {
				Author author = authorOpt.get();
				if (book.getAuthors().contains(author)) {
					authorRepository.deleteById(authorId);
				} else { 
					throw new NoSuchRecordException("No matching record found");
				}
			} else {
				throw new NoSuchRecordException("No matching record found");
			}
		}		
	}

	public List<Author> findAuthors(Long bookId) {
		return authorRepository.findAllByBookId(bookId).stream()
				.sorted(AUTHOR_COMPARATOR)
				.toList();
	}

}

