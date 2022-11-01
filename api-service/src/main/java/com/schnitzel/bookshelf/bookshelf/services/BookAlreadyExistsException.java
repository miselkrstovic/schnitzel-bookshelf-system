package com.schnitzel.bookshelf.bookshelf.services;

public class BookAlreadyExistsException extends Exception {
	
	public BookAlreadyExistsException(String message) {
		super(message);
	}

}
