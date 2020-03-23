package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddUserInfo;
import com.example.demo.dto.CreateQuestionInfo;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Level;
import com.example.demo.model.Question;
import com.example.demo.model.Users;
import com.example.demo.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired 
	UserService userService;
	@GetMapping("/")
	public ResponseEntity<?> getLevels() throws Exception {
		String errorResult = null;
		List<Users> users = null;
		try {

			users = userService.getAllUsers();
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/login")
		public ResponseEntity<?> login(@RequestBody AddUserInfo addUserInfo) throws Exception {
			String errorResult = null;
			boolean successmessage = false;
			
			
			try {
				
				successmessage =userService.login(addUserInfo);
			} catch (ServiceException e) {
				errorResult = e.getMessage();
				return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
			}
			if (successmessage) {
				 return new ResponseEntity<>(successmessage, HttpStatus.OK);
			} else{

				return new ResponseEntity<>(successmessage, HttpStatus.UNAUTHORIZED);
			}

		}
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody AddUserInfo addUserInfo) throws Exception {
		String errorResult = null;
		Users user = null;
		
		
		try {
			
			user =userService.addUser(addUserInfo);
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (addUserInfo != null) {
			 return new ResponseEntity<>(addUserInfo, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}

	}
}
