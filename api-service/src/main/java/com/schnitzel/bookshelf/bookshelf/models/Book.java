package com.schnitzel.bookshelf.bookshelf.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="BOOKS", schema="SCHNITZEL")
public class Book {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
    @Column(name="ISBN", length=250, nullable=false, unique=true)
    private String isbn;
    
    @Column(name="NAME", length=1_000_000_000, nullable=false, unique=false)
    private String name;
    
    @Column(name="ANNOTATION", length=1_000_000_000, nullable=false, unique=false)
    private String annotation;

    @OneToMany
    @JoinColumn(name = "book_id")
    private Set<Author> authors = new HashSet<>();
    
	@Column(name="CREATED", length=250, nullable=false, unique=false)
	private LocalDateTime created;
	
	@Column(name="MODIFIED", length=250, nullable=false, unique=false)
	private LocalDateTime modified;
	
	public Book() {
		super();
	}

	public Book(String isbn, String name, String annotation, LocalDateTime created, LocalDateTime modified) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.annotation = annotation;
		this.created = created;
		this.modified = modified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modifies) {
		this.modified = modifies;
	}
	
}