package com.schnitzel.bookshelf.services;

public class BookAlreadyExistsException extends Exception {
	
	public BookAlreadyExistsException(String message) {
		super(message);
	}

}
