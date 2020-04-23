package com.example.QuestionsModule.exception;

public class ResponseEntity {
	private int statusCode;
	private String description;
	private Object data;
	
	public ResponseEntity(int statusCode, String description, Object data) {
		super();
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
