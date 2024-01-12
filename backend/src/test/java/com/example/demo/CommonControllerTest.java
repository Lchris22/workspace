package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controllers.CommonController;
import com.example.demo.entity.QuestionWrapper;
import com.example.demo.entity.Response;
import com.example.demo.entity.Result;
import com.example.demo.service.QuizService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Run(MockitoJUnitRunner.class)

public class CommonControllerTest {

	@Mock
	private QuizService quizService;
	
	@InjectMocks
	private CommonController commonController;
	
	private MockMvc mockMvc;
	 
	@BeforeEach
	public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commonController).build();
    }
	
	@Test
	public void commonController_getLeaderBoard() throws Exception {
		List<Response> listResponse = new ArrayList<>();
		Result result1 =  new Result(1,100,listResponse,"vishwajeet",95.2,85);
		Result result2 =  new Result(2,100,listResponse,"Pratibh",92,80);
		Result result3 =  new Result(3,100,listResponse,"Sahil",90,75);
		LinkedHashMap<String,Object> result_map1 = new LinkedHashMap<>();
		result_map1.put("Vishwajeet", result1);
		LinkedHashMap<String,Object> result_map2 = new LinkedHashMap<>();
		result_map2.put("Pratibh", result2);
		LinkedHashMap<String,Object> result_map3 = new LinkedHashMap<>();
		result_map3.put("Sahil", result3);
		List<LinkedHashMap<String,Object>> listResponse1 = new ArrayList<>(Arrays.asList(result_map1,result_map2,result_map3));
		when(quizService.showLeaderBoard(100)).thenReturn(listResponse1);
		
		 MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/LeaderBoard/{id}", 100)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andReturn();
		 MockHttpServletResponse response = requestResult.getResponse();
		 String responseBody = response.getContentAsString();
		ObjectMapper objmap = new ObjectMapper();
		List<LinkedHashMap<String, Object>> toppers= objmap.readValue(responseBody,new TypeReference<List<LinkedHashMap<String, Object>>>() {});
		assertEquals(3, toppers.size());
		
		
	}
	
}
