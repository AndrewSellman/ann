package com.sellman.andrew.vann.spring.controller;

public class Response {
	public static final Response SUCCESS = new Response(Status.SUCCESS);
	private final Status status;
	private final String message;

	public Response(Status status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(Status status) {
		this(status, null);
	}

	public Status getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
