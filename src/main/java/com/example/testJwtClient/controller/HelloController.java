package com.example.testJwtClient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {		
	
	@GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
	
	
	
	
	@GetMapping("/data")
    public String getSecureData() {
        return "This is secure data accessible only with a valid JWT.";
    }

}
