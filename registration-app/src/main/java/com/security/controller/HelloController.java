package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private RestTemplate rTemplate;
	
	
	
	@GetMapping
	public BodyBuilder hello() {
	//	rTemplate.de
		rTemplate.postForEntity(null, rTemplate, null);
		return ResponseEntity.ok();
	}
}
