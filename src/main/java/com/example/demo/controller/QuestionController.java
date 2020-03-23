package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateQuestionInfo;
import com.example.demo.dto.DeleteQuestionInfo;
import com.example.demo.dto.MatchInfo;
import com.example.demo.dto.UpdateQuestionInfo;
import com.example.demo.dto.UpdateQuestionStatusInfo;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.BestChoice;
import com.example.demo.model.Category;
import com.example.demo.model.Level;
import com.example.demo.model.Match;
import com.example.demo.model.MultipleChoice;
import com.example.demo.model.Question;
import com.example.demo.model.Types;
import com.example.demo.service.QuestionService;
import com.google.gson.Gson;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
//create a new question
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	QuestionService questionService;

	@PostMapping("/add")
	public ResponseEntity<?> addQuestion(@RequestBody CreateQuestionInfo createQuestionInfo) throws Exception {
		String errorResult = null;
		Question question = null;
		
		
		try {
			
			question =questionService.addQuestion(createQuestionInfo);
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (question != null) {
			 return new ResponseEntity<>(question, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}

	}

//fetch questions whose status is active
	@GetMapping("/activated")
	public ResponseEntity<?> viewActivatedQuestion(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id")String sortBy)throws Exception {

		String result = null;
		Page<Question> activatedQuestions = null;
		try {
			activatedQuestions = questionService.getactivatedQuestions(pageNo, pageSize, sortBy);
		} catch (ServiceException e) {
			result = e.getMessage();
		}
		if (activatedQuestions != null) {
			return new ResponseEntity<>(activatedQuestions, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/match")
	public ResponseEntity<?> getMatch(@RequestParam int qid) throws Exception {
		String result = null;
		List<Match> match=null;
		try {
			match = questionService.getMatches(qid);
		} catch (ServiceException e) {
			result = e.getMessage();
		}
		if (match != null) {
			return new ResponseEntity<>(match, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/bestchoice")
	public ResponseEntity<?> getBestChoice(@RequestParam int qid) throws Exception {
		String result = null;
		List<BestChoice> bestChoice=null;
		try {
			bestChoice = questionService.getBestChoice(qid);
		} catch (ServiceException e) {
			result = e.getMessage();
		}
		if (bestChoice != null) {
			return new ResponseEntity<>(bestChoice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/multiplechoice")
	public ResponseEntity<?> getMultipleChoice(@RequestParam int qid) throws Exception {
		String result = null;
		List<MultipleChoice> multipleChoice=null;
		try {
			multipleChoice = questionService.getMultipleChoice(qid);
		} catch (ServiceException e) {
			result = e.getMessage();
		}
		if (multipleChoice != null) {
			return new ResponseEntity<>(multipleChoice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/levels")
	public ResponseEntity<?> getLevels() throws Exception {
		String errorResult = null;
		List<Level> levels = null;
		try {

			levels = questionService.getLevels();
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (levels != null) {
			return new ResponseEntity<>(levels, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/types")
	public ResponseEntity<?> getTypes() throws Exception {
		String errorResult = null;
		List<Types> types = null;
		try {

			types = questionService.getTypes();
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (types != null) {
			return new ResponseEntity<>(types, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}
	}

//fetch questions whose status is deactive
	@GetMapping("/deactivated")
	public ResponseEntity<?> viewDeactivatedQuestion(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id")String sortBy) throws Exception {
		String result = null;
		Page<Question> deactivatedQuestions = null;

		try {
			deactivatedQuestions = questionService.getDeactivatedQuestions(pageNo, pageSize, sortBy);
		} catch (ServiceException e) {
			result = e.getMessage();
		}
		if (deactivatedQuestions != null) {
			return new ResponseEntity<>(deactivatedQuestions, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
//update question details for a particular question
	@PutMapping("/edit")
	public ResponseEntity<?> editQuestion(@RequestBody UpdateQuestionInfo updateQuestionInfo)throws Exception{
		String updateResult=null;
		String errorResult=null;
		Question question=null;
		try {
			updateResult = questionService.updateQuestion(updateQuestionInfo);
		}catch(ServiceException e) {
			errorResult=e.getMessage();
		}
		if(updateResult!=null) {
		return new ResponseEntity<>(updateResult,HttpStatus.OK);
	}else {

		return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
	}
	}

//update status based on ids(applicable for single and bulk update)
	@PutMapping("/updatestatus")
	public ResponseEntity<?> update(@RequestBody UpdateQuestionStatusInfo updateQuestionStatusInfo) throws Exception {
		String updateStatusResult = null;
		String errorResult = null;
		try {

			updateStatusResult = questionService.updateQuestionStatus(updateQuestionStatusInfo);
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (updateStatusResult != null) {
			return new ResponseEntity<>(updateStatusResult, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}
	}

//delete question based on set of ids(applicable for single and bulk delete)
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteQuestion(@RequestBody DeleteQuestionInfo deleteQuestionInfo) throws Exception {
		String deleteResult = null;
		String errorResult = null;
		try {
			deleteResult = questionService.deleteQuestion(deleteQuestionInfo);
			return new ResponseEntity<>(deleteResult, HttpStatus.OK);
		} catch (ServiceException e) {
			errorResult = e.getMessage();
			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}

	}

//search question based on tags
	@GetMapping("/basedontags")
	public ResponseEntity<?> getQuestionBasedOnTags(@RequestParam String tagName,@RequestParam String status,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id")String sortBy
			) throws Exception {
		String result = null;
		Page<Question> questions = null;

		try {
			questions = questionService.getQuestionBasedOnTags(tagName,status,pageNo, pageSize, sortBy);
		} catch (ServiceException e) {
			result = e.getMessage();
		}
		if (questions != null) {
			return new ResponseEntity<>(questions, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
	

//get question details based on id
	@GetMapping("/")
	public ResponseEntity<?> getQuestion(@RequestParam int id) throws Exception {
		String errorResult = null;
		Optional<Question> question = null;
		try {

			question = questionService.getQuestion(id);
			
		} catch (ServiceException e) {
			errorResult = e.getMessage();
		}
		if (question != null) {
			return new ResponseEntity<>(question, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/categories")
		public ResponseEntity<?> getCategories() throws Exception {
			String errorResult = null;
			List<Category> categories = null;
			try {

				categories = questionService.getCategories();
			} catch (ServiceException e) {
				errorResult = e.getMessage();
			}
			if (categories != null) {
				return new ResponseEntity<>(categories, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
			}
		}
	
	}
