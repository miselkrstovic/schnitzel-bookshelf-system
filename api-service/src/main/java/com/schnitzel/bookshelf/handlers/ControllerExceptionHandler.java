package com.schnitzel.bookshelf.handlers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		Map<String, Object> object = Message.buildMessage(
				Message.MessageType.FAILURE,
				ex.getMessage(), 
				new HashMap<>(), 
				HttpStatus.BAD_REQUEST,
				LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset())
		);			
	    return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
	}

}