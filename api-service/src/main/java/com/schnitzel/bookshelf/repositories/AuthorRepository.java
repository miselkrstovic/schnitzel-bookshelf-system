package com.schnitzel.bookshelf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schnitzel.bookshelf.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	List<Author> findAllByBookId(Long bookId);
	void deleteAllByBookId(Long bookId);

}
