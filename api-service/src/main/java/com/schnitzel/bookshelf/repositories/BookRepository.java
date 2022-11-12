package com.schnitzel.bookshelf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.schnitzel.bookshelf.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Book findByIsbn(String isbn);
	Book findByName(String name);
	List<Book> findAllByIsbn(String keyword);
	List<Book> findAllByName(String keyword);
	
	
	@Query("SELECT b FROM Book b LEFT JOIN Author a ON b.id = a.book WHERE b.isbn LIKE ?1 "
			+ "OR LOWER(b.name) LIKE LOWER(CONCAT('%', CONCAT(?1, '%')))"
			+ "OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', CONCAT(?1, '%')))"
			+ "OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', CONCAT(?1, '%')))")
	List<Book> findAllByKeyword(String keyword);
	
}
