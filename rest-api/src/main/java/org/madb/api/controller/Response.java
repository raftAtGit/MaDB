package org.madb.api.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor class Response {
	static final Response OK = new Response("OK");
	
	String status;
}