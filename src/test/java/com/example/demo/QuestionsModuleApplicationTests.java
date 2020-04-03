package com.example.demo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.xml.ws.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuestionsModuleApplicationTests {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	ObjectMapper om=new ObjectMapper();
	@Before
	public void setup() {
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	//@Test
//	public void getActivatedTest() throws Exception{
//		MvcResult result=mockMvc
//						.perform(get("/questions/activated").content(MediaType.APPLICATION_JSON_VALUE))
//						.andExpect(status().isOk()).andReturn();
//		String resultContent=result.getResponse().getContentAsString();
//		Response response=om.readValue(resultContent,Response.class);
//		Assert.assertTrue(response.HttpStatus==Boolean.TRUE);
//				
//	}

}
