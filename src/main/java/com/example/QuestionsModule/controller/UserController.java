package com.example.QuestionsModule.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuestionsModule.dto.AddUserInfo;
import com.example.QuestionsModule.dto.CreateQuestionInfo;
import com.example.QuestionsModule.dto.EditUserInfo;
import com.example.QuestionsModule.exception.ServiceException;
import com.example.QuestionsModule.model.AuthenticationRequest;
import com.example.QuestionsModule.model.Level;
import com.example.QuestionsModule.model.Question;
import com.example.QuestionsModule.model.User;
import com.example.QuestionsModule.service.UserService;

//
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/")
	public ResponseEntity<?> getAllUsers() throws Exception {
		String errorResult = null;
		List<User> users = null;
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

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addUser(@RequestBody AddUserInfo addUserInfo) throws Exception {
		String errorResult = null;
		AuthenticationRequest user = null;

		try {

			user = userService.addUser(addUserInfo);
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (addUserInfo != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/edit")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> editUser(@RequestBody EditUserInfo editUserInfo) throws Exception {
		String user = null;
		String errorResult = null;
		try {

			user = userService.editUser(editUserInfo);
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}
	}

}
