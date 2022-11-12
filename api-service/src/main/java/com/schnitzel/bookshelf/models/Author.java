package com.schnitzel.bookshelf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "AUTHORS", schema = "SCHNITZEL")
public class Author {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="FIRSTNAME", length=250, nullable=false, unique=false)
	private String firstName;
	
	@Column(name="LASTNAME", length=250, nullable=false, unique=false)
	private String lastName;
	
	@JsonIgnore
	@ManyToOne
	private Book book;
	
	public Author() {
		super();	
	}

	public Author(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Author) {
			return this.firstName.equals(((Author) obj).firstName) &&
					this.lastName.equals(((Author) obj).lastName);
		} else {
			return super.equals(obj);
		}
	}
		
}
