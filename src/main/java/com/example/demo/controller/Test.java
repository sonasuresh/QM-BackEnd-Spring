package com.example.demo.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
//@RequestMapping("/test")
public class Test {
	
	@GetMapping("/user")
	@PreAuthorize ("hasAnyRole('USER','ADMIN')")
	public String test1() {
		return "user";
	}
	//@RequestMapping("/core")
	@GetMapping("/admin")
	@PreAuthorize ("hasRole('ADMIN')")
	public String test2() {
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		 
	   return generatedString;
	}

}
