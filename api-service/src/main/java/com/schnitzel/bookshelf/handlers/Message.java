package com.schnitzel.bookshelf.handlers;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class Message {

	public static enum MessageType {
		SUCCESS(0), FAILURE(1);

		private int value;

		MessageType(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

	public static Map<String, Object> buildMessage(MessageType type, String message, Map<String, Object> details, HttpStatus status,
			OffsetDateTime timestamp) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("error", type.value);
		body.put("message", message);
		body.put("details", details);
		body.put("code", status.value());
		body.put("timestamp", timestamp);
    
		return body;
	}
	
}
