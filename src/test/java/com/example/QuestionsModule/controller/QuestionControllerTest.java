package com.example.QuestionsModule.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.QuestionsModule.dto.CreateQuestionInfo;
import com.example.QuestionsModule.dto.DeleteQuestionInfo;
import com.example.QuestionsModule.dto.ShortAnswerInfo;
import com.example.QuestionsModule.dto.UpdateQuestionInfo;
import com.example.QuestionsModule.dto.UpdateQuestionStatusInfo;
import com.example.QuestionsModule.exception.ServiceException;
import com.example.QuestionsModule.model.BestChoice;
import com.example.QuestionsModule.model.Category;
import com.example.QuestionsModule.model.Level;
import com.example.QuestionsModule.model.Match;
import com.example.QuestionsModule.model.MultipleChoice;
import com.example.QuestionsModule.model.Question;
import com.example.QuestionsModule.model.Types;
import com.example.QuestionsModule.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebAppConfiguration
@AutoConfigureRestDocs()
public class QuestionControllerTest {

	private MockMvc mockmvc;

	private ObjectMapper om = new ObjectMapper();
	@InjectMocks
	QuestionController questioncontroller;
	@Mock
	QuestionService questionservice;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {

		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(questioncontroller)
				.apply(documentationConfiguration(restDocumentation)).build();
	}

	public Question QuestionObject() {
		Question question = new Question();
		question.setId(1);
		question.setTitle("test");
		question.setStatus("active");
		Category category = new Category();
		category.setId(1);
		question.setCategory_Id(category);
		question.setScore(1);
		question.setTags("test");

		question.setDuration(null);
		question.setContent("what is java?");
		Level level = new Level();
		level.setId(1);
		question.setLevel_Id(level);
		question.setOption(null);
		question.setSkill_points(5);
		Types type = new Types();
		type.setId(4);
		question.setType_Id(type);
		return question;
	}

	@Test
	public void getLevelsTest() throws Exception {
		List<Level> levels = new ArrayList<Level>();
		Level level = new Level();
		level.setId(1);
		level.setName("level 100");
		levels.add(level);
		when(questionservice.getLevels()).thenReturn(levels);
		this.mockmvc.perform(get("/questions/levels").contentType("application/json")).andDo(print())
				.andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getLevelsTestFailure() throws Exception {
		List<Level> levels = new ArrayList<Level>();
		Level level = new Level();
		level.setId(1);
		level.setName("level 100");
		levels.add(level);
		doThrow(ServiceException.class).when(questionservice).getLevels();
		this.mockmvc.perform(get("/questions/levels").contentType("application/json"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getTypesTest() throws Exception {
		List<Types> types = new ArrayList<>();
		Types type = new Types();
		type.setId(1);
		type.setName("type 5");
		types.add(type);
		when(questionservice.getTypes()).thenReturn(types);
		this.mockmvc.perform(get("/questions/types")).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void getTypesTestFailure() throws Exception {
		List<Types> types = new ArrayList<>();
		Types type = new Types();
		type.setId(1);
		type.setName("type 5");
		types.add(type);
		doThrow(ServiceException.class).when(questionservice).getTypes();
		this.mockmvc.perform(get("/questions/types")).andExpect(status().isBadRequest());
	}

	@Test
	public void getCategoriesTest() throws Exception {
		List<Category> categories = new ArrayList<>();
		Category category = new Category();
		category.setId(1);
		category.setName("category test");
		categories.add(category);
		when(questionservice.getCategories()).thenReturn(categories);
		this.mockmvc.perform(get("/questions/categories").contentType("application/json")).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getCategoriesTestFailure() throws Exception {
		List<Category> categories = new ArrayList<>();
		Category category = new Category();
		category.setId(1);
		category.setName("category test");
		categories.add(category);
		doThrow(ServiceException.class).when(questionservice).getCategories();
		this.mockmvc.perform(get("/questions/categories").contentType("application/json"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void addQuestionTest() throws Exception {
		CreateQuestionInfo question = new CreateQuestionInfo();
		question.setId(1);
		question.setTitle("test");
		question.setStatus("active");
		question.setCategory_Id(1);
		question.setScore(1);
		question.setTags("test");

		question.setDuration(null);
		question.setContent("what is java?");
		Level level = new Level();
		question.setLevel_Id(1);
//		ShortAnswerInfo shortAnswer = new ShortAnswerInfo();
//		shortAnswer.setAnswer("{key:ans}");
		question.setBest_choice(null);
		question.setMultiple_choice(null);
		question.setNumericalValue(null);
		question.setMatch(null);
		question.setShortAnswer(null);
		question.setTrue_false(null);
		question.setSkill_points(5);
		question.setType_Id(1);
		doNothing().when(questionservice).addQuestion(question);
		String Json = om.writeValueAsString(question);
		MvcResult result = this.mockmvc.perform(post("/questions/add").contentType("application/json").content(Json))
				.andExpect(status().isCreated())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
	}

	@Test
	public void addQuestionTestFailure() throws Exception {
		CreateQuestionInfo question = new CreateQuestionInfo();
		doNothing().when(questionservice).addQuestion(question);
		this.mockmvc.perform(post("/questions/add").contentType("application/json")).andExpect(status().isBadRequest());
	}

	@Test
	public void updateQuestionTest() throws Exception {
		UpdateQuestionInfo question = new UpdateQuestionInfo();
		question.setId(1);
		question.setTitle("test");
		question.setStatus("active");
		question.setCategory_Id(1);
		question.setScore(1);
		question.setTags("test");

		question.setDuration(null);
		question.setContent("what is java?");
		Level level = new Level();
		question.setLevel_Id(1);
//		ShortAnswerInfo shortAnswer = new ShortAnswerInfo();
//		shortAnswer.setAnswer("{key:ans}");
		question.setBest_choice(null);
		question.setMultiple_choice(null);
		question.setNumericalValue(null);
		question.setMatch(null);
		question.setShortAnswer(null);
		question.setTrue_false(null);
		question.setSkill_points(5);
		question.setType_Id(1);
		doNothing().when(questionservice).updateQuestion(question);
		String Json = om.writeValueAsString(question);
		MvcResult result = this.mockmvc.perform(put("/questions/edit").contentType("application/json").content(Json))
				.andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
	}

	@Test
	public void updateQuestionTestFailure() throws Exception {
		UpdateQuestionInfo question = new UpdateQuestionInfo();
		doThrow(ServiceException.class).when(questionservice).updateQuestion(question);
		this.mockmvc.perform(put("/questions/edit").contentType("application/json")).andExpect(status().isBadRequest());
	}

	@Test
	public void deleteQuestionsTest() throws Exception {
		DeleteQuestionInfo id = new DeleteQuestionInfo();
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		id.setId(ids);
		doNothing().when(questionservice).deleteQuestion(id);
		String Json = om.writeValueAsString(id);
		this.mockmvc.perform(delete("/questions/delete").contentType("application/json").content(Json))
				.andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void deleteQuestionsTestFailure() throws Exception {
		DeleteQuestionInfo id = new DeleteQuestionInfo();
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		id.setId(ids);
		doThrow(ServiceException.class).when(questionservice).deleteQuestion(id);
		this.mockmvc.perform(delete("/questions/delete").contentType("application/json"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateQuestionsTest() throws Exception {
		UpdateQuestionStatusInfo updatedetails = new UpdateQuestionStatusInfo();
		List<Integer> ids = new ArrayList<>();
		String status = "active";
		ids.add(1);
		ids.add(2);
		ids.add(3);
		updatedetails.setId(ids);
		updatedetails.setStatus(status);
		doNothing().when(questionservice).updateQuestionStatus(updatedetails);
		String Json = om.writeValueAsString(updatedetails);
		this.mockmvc.perform(put("/questions/updatestatus").contentType("application/json").content(Json))
				.andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void updateQuestionsTestFailure() throws Exception {
		UpdateQuestionStatusInfo updatedetails = new UpdateQuestionStatusInfo();
		List<Integer> ids = new ArrayList<>();
		String status = "active";
		ids.add(1);
		ids.add(2);
		ids.add(3);
		updatedetails.setId(ids);
		updatedetails.setStatus(status);
		doThrow(ServiceException.class).when(questionservice).updateQuestionStatus(updatedetails);
		this.mockmvc.perform(put("/questions/updatestatus")).andExpect(status().isBadRequest());
	}

	@Test
	public void getMultipleChoiceTest() throws Exception {
		List<MultipleChoice> multipleChoices = new ArrayList<>();
		MultipleChoice multipleChoice = new MultipleChoice();
		multipleChoice.setId(1);
		multipleChoice.setIs_sticky(1);
		multipleChoice.setIs_yes(1);
		multipleChoice.setQ_id(3);
		multipleChoice.setValue("multiple choice test");
		multipleChoices.add(multipleChoice);
		int qid = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("qid", "1");
		when(questionservice.getMultipleChoice(qid)).thenReturn(multipleChoices);
		this.mockmvc.perform(get("/questions/multiplechoice").params(requestParams)).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void getMultipleChoiceTestFailure() throws Exception {
		List<MultipleChoice> multipleChoices = new ArrayList<>();
		MultipleChoice multipleChoice = new MultipleChoice();
		multipleChoice.setId(1);
		multipleChoice.setIs_sticky(1);
		multipleChoice.setIs_yes(1);
		multipleChoice.setQ_id(3);
		multipleChoice.setValue("multiple choice test");
		multipleChoices.add(multipleChoice);
		int qid = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("qid", "1");
		doThrow(ServiceException.class).when(questionservice).getMultipleChoice(qid);
		this.mockmvc.perform(get("/questions/multiplechoice").params(requestParams)).andExpect(status().isBadRequest())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getBestChoiceTest() throws Exception {
		List<BestChoice> bestChoices = new ArrayList<>();
		BestChoice bestChoice = new BestChoice();
		bestChoice.setId(1);
		bestChoice.setIs_sticky(1);
		bestChoice.setIs_yes(1);
		bestChoice.setQ_id(1);
		bestChoice.setValue("best choice test");
		bestChoices.add(bestChoice);
		int qid = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("qid", "1");
		when(questionservice.getBestChoice(qid)).thenReturn(bestChoices);
		this.mockmvc.perform(get("/questions/bestchoice").params(requestParams)).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void getBestChoiceTestFailure() throws Exception {
		List<BestChoice> bestChoices = new ArrayList<>();
		BestChoice bestChoice = new BestChoice();
		bestChoice.setId(1);
		bestChoice.setIs_sticky(1);
		bestChoice.setIs_yes(1);
		bestChoice.setQ_id(1);
		bestChoice.setValue("best choice test");
		bestChoices.add(bestChoice);
		int qid = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("qid", "1");
		doThrow(ServiceException.class).when(questionservice).getBestChoice(qid);
		this.mockmvc.perform(get("/questions/bestchoice").params(requestParams)).andExpect(status().isBadRequest());
	}

	@Test
	public void getMatchTest() throws Exception {
		List<Match> matches = new ArrayList<>();
		Match match = new Match();
		match.setId(1);
		match.setCol_a("column a");
		match.setCol_b("column b");
		match.setId(1);
		match.setMatch_option_id(2);
		match.setQ_id(1);
		matches.add(match);
		int qid = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("qid", "1");
		when(questionservice.getMatches(qid)).thenReturn(matches);
		this.mockmvc.perform(get("/questions/match").params(requestParams)).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void getMatchTestFailure() throws Exception {
		List<Match> matches = new ArrayList<>();
		Match match = new Match();
		match.setId(1);
		match.setCol_a("column a");
		match.setCol_b("column b");
		match.setId(1);
		match.setMatch_option_id(2);
		match.setQ_id(1);
		matches.add(match);
		int qid = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("qid", "1");
		doThrow(ServiceException.class).when(questionservice).getMatches(qid);
		this.mockmvc.perform(get("/questions/match").params(requestParams)).andExpect(status().isBadRequest());
	}

	@Test
	public void getQuestionTest() throws Exception {
		Optional<Question> questionDetails;
		Question question = QuestionObject();
		questionDetails = Optional.of(question);

		int id = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		when(questionservice.getQuestion(id)).thenReturn(questionDetails);
		this.mockmvc.perform(get("/questions/").params(requestParams)).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void getQuestionTestFailure() throws Exception {
		Optional<Question> questionDetails;
		Question question = QuestionObject();
		questionDetails = Optional.of(question);

		int id = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		doThrow(ServiceException.class).when(questionservice).getQuestion(id);
		this.mockmvc.perform(get("/questions/").params(requestParams)).andExpect(status().isBadRequest());
	}

	@Test
	public void ViewActivatedQuestionTest() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> activatedQuestions = new PageImpl(ques);
		when(questionservice.getactivatedQuestions(0, 10, "id")).thenReturn(activatedQuestions);
		this.mockmvc.perform(get("/questions/activated")).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void ViewActivatedQuestionTestFailure() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> activatedQuestions = new PageImpl(ques);
		doThrow(ServiceException.class).when(questionservice).getactivatedQuestions(0, 10, "id");
		this.mockmvc.perform(get("/questions/activated")).andExpect(status().isBadRequest());
	}

	@Test
	public void ViewDeactivatedQuestionTest() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> deactivatedQuestions = new PageImpl(ques);
		when(questionservice.getDeactivatedQuestions(0, 10, "id")).thenReturn(deactivatedQuestions);
		this.mockmvc.perform(get("/questions/deactivated")).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void ViewDeactivatedQuestionTestFailure() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> deactivatedQuestions = new PageImpl(ques);
		doThrow(ServiceException.class).when(questionservice).getDeactivatedQuestions(0, 10, "id");
		this.mockmvc.perform(get("/questions/deactivated")).andExpect(status().isBadRequest());
	}

	@Test
	public void ViewQuestionBasedonTagsTest() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> questions = new PageImpl(ques);
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("tagName", "test");
		requestParams.add("status", "active");
		when(questionservice.getQuestionBasedOnTags("test", "active", 0, 10, "id")).thenReturn(questions);
		this.mockmvc.perform(get("/questions/basedontags").params(requestParams)).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}

	@Test
	public void ViewQuestionBasedonTagsTestFailure() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> questions = new PageImpl(ques);
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("tagName", "test");
		requestParams.add("status", "active");
		doThrow(ServiceException.class).when(questionservice).getQuestionBasedOnTags("test", "active", 0, 10, "id");
		this.mockmvc.perform(get("/questions/basedontags").params(requestParams)).andExpect(status().isBadRequest());
	}

}
