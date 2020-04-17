package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.dto.CreateQuestionInfo;
import com.example.demo.dto.DeleteQuestionInfo;
import com.example.demo.dto.ShortAnswerInfo;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

//@AutoConfigureRestDocs(outputDir="target/generated-snippets")
@AutoConfigureMockMvc
class QuestionControllerTest {
//	@Rule
//	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	private MockMvc mockmvc;

	private ObjectMapper om = new ObjectMapper();
	@InjectMocks
	QuestionController questioncontroller;
	@Mock
	QuestionService questionservice;

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(questioncontroller)
//				.apply(documentationConfiguration(this.restDocumentation).uris().withScheme("http")
//						.withHost("localhost").withPort(9004))
				// .apply(documentationConfiguration(this.restDocumentation))
				.build();
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
	void getLevelsTest() throws Exception {
		List<Level> levels = new ArrayList<Level>();
		Level level = new Level();
		level.setId(1);
		level.setName("level 100");
		levels.add(level);
		when(questionservice.getLevels()).thenReturn(levels);
		this.mockmvc.perform(get("/questions/levels").contentType("application/json"))
				// .andDo(print())
				.andExpect(status().isOk());
		// .andDo(document("getLevels",
		// preprocessRequest(prettyPrint()),
		// preprocessResponse(prettyPrint())
		// )
		// );
	}
	
	@Test
	void getLevelsTestFailure() throws Exception {
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
	void getTypesTest() throws Exception {
		List<Types> types = new ArrayList<>();
		Types type = new Types();
		type.setId(1);
		type.setName("type 5");
		types.add(type);
		when(questionservice.getTypes()).thenReturn(types);
		this.mockmvc.perform(get("/questions/types")).andExpect(status().isOk());
	}

	@Test
	void getTypesTestFailure() throws Exception {
		List<Types> types = new ArrayList<>();
		Types type = new Types();
		type.setId(1);
		type.setName("type 5");
		types.add(type);
		doThrow(ServiceException.class).when(questionservice).getTypes();
		this.mockmvc.perform(get("/questions/types")).andExpect(status().isBadRequest());
	}
	
	@Test
	void getCategoriesTest() throws Exception {
		List<Category> categories = new ArrayList<>();
		Category category = new Category();
		category.setId(1);
		category.setName("category test");
		categories.add(category);
		when(questionservice.getCategories()).thenReturn(categories);
		this.mockmvc.perform(get("/questions/categories").contentType("application/json")).andExpect(status().isOk());
	}
	
	@Test
	void getCategoriesTestFailure() throws Exception {
		List<Category> categories = new ArrayList<>();
		Category category = new Category();
		category.setId(1);
		category.setName("category test");
		categories.add(category);
		doThrow(ServiceException.class).when(questionservice).getCategories();
		this.mockmvc.perform(get("/questions/categories").contentType("application/json")).andExpect(status().isBadRequest());
	}

	@Test
	void addQuestionTest() throws Exception {
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
				.andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	void addQuestionTestFailure() throws Exception {
		CreateQuestionInfo question = new CreateQuestionInfo();
		doNothing().when(questionservice).addQuestion(question);
		this.mockmvc.perform(post("/questions/add").contentType("application/json")).andExpect(status().isBadRequest());
	}

	@Test
	void updateQuestionTest() throws Exception {
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
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	void updateQuestionTestFailure() throws Exception {
		UpdateQuestionInfo question = new UpdateQuestionInfo();
		doThrow(ServiceException.class).when(questionservice).updateQuestion(question);
		this.mockmvc.perform(put("/questions/edit").contentType("application/json")).andExpect(status().isBadRequest());
	}
	
	@Test
	void deleteQuestionsTest() throws Exception {
		DeleteQuestionInfo id = new DeleteQuestionInfo();
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		id.setId(ids);
		doNothing().when(questionservice).deleteQuestion(id);
		String Json = om.writeValueAsString(id);
		this.mockmvc.perform(delete("/questions/delete").contentType("application/json").content(Json))
				.andExpect(status().isOk());
	}
	
	@Test
	void deleteQuestionsTestFailure() throws Exception {
		DeleteQuestionInfo id = new DeleteQuestionInfo();
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		id.setId(ids);
		doThrow(ServiceException.class).when(questionservice).deleteQuestion(id);
		this.mockmvc.perform(delete("/questions/delete").contentType("application/json")).andExpect(status().isBadRequest());
	}

	@Test
	void updateQuestionsTest() throws Exception {
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
				.andExpect(status().isOk());
	}

	@Test
	void updateQuestionsTestFailure() throws Exception {
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
	void getMultipleChoiceTest() throws Exception {
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
		this.mockmvc.perform(get("/questions/multiplechoice").params(requestParams))
				.andExpect(status().isOk());
	}
	
	@Test
	void getMultipleChoiceTestFailure() throws Exception {
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
		this.mockmvc.perform(get("/questions/multiplechoice").params(requestParams))
				.andExpect(status().isBadRequest());
	}

	@Test
	void getBestChoiceTest() throws Exception {
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
		this.mockmvc.perform(get("/questions/bestchoice").params(requestParams))
				.andExpect(status().isOk());
	}
	
	@Test
	void getBestChoiceTestFailure() throws Exception {
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
		this.mockmvc.perform(get("/questions/bestchoice").params(requestParams))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void getMatchTest() throws Exception {
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
		this.mockmvc.perform(get("/questions/match").params(requestParams))
				.andExpect(status().isOk());
	}

	@Test
	void getMatchTestFailure() throws Exception {
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
		this.mockmvc.perform(get("/questions/match").params(requestParams))
				.andExpect(status().isBadRequest());
	}

	
	@Test
	void getQuestionTest() throws Exception {
		Optional<Question> questionDetails;
		Question question = QuestionObject();
		questionDetails = Optional.of(question);

		int id = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		when(questionservice.getQuestion(id)).thenReturn(questionDetails);
		this.mockmvc.perform(get("/questions/").params(requestParams))
				.andExpect(status().isOk());
	}
	
	@Test
	void getQuestionTestFailure() throws Exception {
		Optional<Question> questionDetails;
		Question question = QuestionObject();
		questionDetails = Optional.of(question);

		int id = 1;
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		doThrow(ServiceException.class).when(questionservice).getQuestion(id);
		this.mockmvc.perform(get("/questions/").params(requestParams))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void ViewActivatedQuestionTest() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> activatedQuestions = new PageImpl(ques);
		when(questionservice.getactivatedQuestions(0, 10, "id")).thenReturn(activatedQuestions);
		this.mockmvc.perform(get("/questions/activated")).andExpect(status().isOk());
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
		this.mockmvc.perform(get("/questions/deactivated")).andExpect(status().isOk());
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
		this.mockmvc.perform(get("/questions/basedontags").params(requestParams))
				.andExpect(status().isOk());
	}
	
	@Test
	public void ViewQuestionBasedonTagsTestFailure() throws Exception {
		List<Question> ques = new ArrayList<>();
		Page<Question> questions = new PageImpl(ques);
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("tagName", "test");
		requestParams.add("status", "active");
		doThrow(ServiceException.class).when(questionservice).getQuestionBasedOnTags("test", "active", 0, 10, "id");
		this.mockmvc.perform(get("/questions/basedontags").params(requestParams))
				.andExpect(status().isBadRequest());
	}

}
