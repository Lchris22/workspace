package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.Exception.QuizAnswered;
import com.example.demo.controllers.StudentController;
import com.example.demo.entity.QuestionWrapper;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.Response;
import com.example.demo.entity.Result;
import com.example.demo.service.QuizService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class studentControllerMockmvcTests {
	
	
		@Mock
		private QuizService quizService;
	 
		
	 
		@InjectMocks
		private StudentController studentController;
	 
		private MockMvc mockMvc;
	 
		@BeforeEach
		public void setUp() {
			mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
		}
		
		

		/**  Test to  **/ //method removed
//		@Test
//		public void testToGetQuizzes() throws Exception {
//	 
//			List<Questions> question_list = DummyData.generate20Questions();
//			Quiz quiz2 = DummyData.createQuiz("Java Basics", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "pratibh");
//			Quiz quiz3 = DummyData.createQuiz("Java Advance", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "lenin");
//			List<Quiz> quiz_list = new ArrayList<>();
//			quiz_list.add(quiz2);
//			quiz_list.add(quiz3);
//			
//			List<Map<String, Object>> newQuiz=new ArrayList<>(); 
//			 for(Quiz quiz:quiz_list) {
//				 Map<String, Object> quizInfo=new HashMap<>();
//					
//				 quizInfo.put("quizId", quiz.getQuizId());
//				 quizInfo.put("quizName", quiz.getQuizName());
//				 quizInfo.put("quizTopic", quiz.getQuizTopic());
//				 quizInfo.put("totalNumberOfQuestions", quiz.getTotal_number_of_questions());
//				 quizInfo.put("quizStatus", quiz.getQuiz_status());
//				 quizInfo.put("totalDurationOfQuiz", quiz.getTotal_duaration_of_quiz());
//				 
//				 newQuiz.add(quizInfo);
//				 }
//			
//			
//			
//			
//			when(quizService.getQuizzessForStudent()).thenReturn(newQuiz);
//			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/getAllQuizes").contentType(MediaType.APPLICATION_JSON)
//					.content(new ObjectMapper().writeValueAsString(quiz_list)))
//					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//			
//			MockHttpServletResponse response = requestResult.getResponse();
//			String responseBody = response.getContentAsString();
//			System.out.println(responseBody);
//			ObjectMapper objmap = new ObjectMapper();
//			List<Map<String, Object>> quiz_list2= objmap.readValue(responseBody,new TypeReference<List<Map<String, Object>>>() {});
//			
//			assertEquals(2,quiz_list2.size());
//		}

		
		@Test
	    public void studentController_welcome()throws Exception {
	        String message= "Welocome to Assessment Platform";
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(message)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			MockHttpServletResponse response = requestResult.getResponse();
			
			String responseBody = response.getContentAsString();
			System.out.println(responseBody);
			
	 
	    }
		@Test
	    public void studentController_getAllTopics_ReturnsRespectiveQuizzes()throws Exception {
			
			List<Questions> question_list = DummyData.generate20Questions();
			Quiz quiz2 = DummyData.createQuiz("Java Basics", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "pratibh");
			Quiz quiz3 = DummyData.createQuiz("Java Advance", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "lenin");
			List<Quiz> quiz_list = new ArrayList<>();
			quiz_list.add(quiz2);
			quiz_list.add(quiz3);
			
			
			Set<String> topicSet = new HashSet<>();
	    	topicSet.add("CPP");
	    	topicSet.add("JAVA_CORE");
	    	topicSet.add("PYTHON");
	    	topicSet.add("UI");
	    	when(quizService.getAllTopics()).thenReturn(topicSet);
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/topics").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(
							quiz_list
							)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			MockHttpServletResponse response = requestResult.getResponse();
			String responseBody = response.getContentAsString();
			ObjectMapper objmap = new ObjectMapper();
			Set<String> topic_list= objmap.readValue(responseBody,new TypeReference<Set<String>>() {});

			assert(topic_list.contains("CPP"));
			assert(topic_list.contains("JAVA_CORE"));
			assert(topic_list.contains("PYTHON"));
			assert(topic_list.contains("UI"));
				
	    }
		
		@Test
		public void studentController_takequiz_ReturnsRespectiveWrappedQuiz() throws Exception {
	 
			List<Questions> question_list = DummyData.generate20Questions();
			Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 19, "lenin");
			quiz1.setQuizId(49);
			List<Questions> questionFromDbList=quiz1.getListOfquestions();
			List<QuestionWrapper> questionforStudent=new ArrayList<>();
			for(Questions que:questionFromDbList) {
				QuestionWrapper qWrapper=new QuestionWrapper(que.getQuestionId(),que.getQuestionStatement(), que.getOption1(),que.getOption2(),que.getOption3(), que.getOption4());
				questionforStudent.add(qWrapper);
			}
			when(quizService.takeQuiz(49,"prathm")).thenReturn(new ResponseEntity<>(questionforStudent,HttpStatus.OK));
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/takequiz/49").contentType(MediaType.APPLICATION_JSON)
					.content("prathm"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			
			MockHttpServletResponse response = requestResult.getResponse();
			assertEquals(200, response.getStatus());
			String responseBody = response.getContentAsString();
			System.out.println(responseBody);
			ObjectMapper objmap = new ObjectMapper();
			List<QuestionWrapper> quiz_list2= objmap.readValue(responseBody,new TypeReference<List<QuestionWrapper>>() {});
			assertEquals(20, quiz_list2.size());
		}

		@Test
		public void studentController_takequiz_Returns403WhenAnsweredEarlier() throws Exception {
			when(quizService.takeQuiz(49,"prathm")).thenThrow(new QuizAnswered());
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/takequiz/49").contentType(MediaType.APPLICATION_JSON)
					.content("prathm")).andReturn();
			
			MockHttpServletResponse response = requestResult.getResponse();
			assertEquals(403, response.getStatus());
		}
		@Test
		public void studentController_getQuizByTopic_ReturnsRespectiveQuizzes() throws Exception {
			List<Questions> question_list = DummyData.generate20Questions();
			Quiz quiz2 = DummyData.createQuiz("Java Basics", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "pratibh");
			Quiz quiz3 = DummyData.createQuiz("Java Advance", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "lenin");
			List<Quiz> quiz_list = new ArrayList<>();
			quiz_list.add(quiz2);
			quiz_list.add(quiz3);
			
			List<LinkedHashMap<String, Object>> newQuiz = new ArrayList<>();
			 for(Quiz quiz:quiz_list) {
				 LinkedHashMap<String, Object> simpDataObjects = new LinkedHashMap<>();
				 
				 simpDataObjects.put("quizId", quiz.getQuizId());
				 simpDataObjects.put("quizTopic", quiz.getQuizTopic());
				 simpDataObjects.put("quizName", quiz.getQuizName());
				 simpDataObjects.put("No.Of.Questions", quiz.getTotal_number_of_questions());
				 simpDataObjects.put("Duration", quiz.getTotal_duaration_of_quiz());
				 simpDataObjects.put("Status", quiz.getQuiz_status());
				 
				 newQuiz.add(simpDataObjects);
				 }
			
			when(quizService.getQuizByquizTopics("JAVA_CORE")).thenReturn(newQuiz);
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/getQuizByTopic/JAVA_CORE").contentType(MediaType.APPLICATION_JSON)
					.content("prathm")).andReturn();
			
			MockHttpServletResponse response = requestResult.getResponse();
			assertEquals(200, response.getStatus());
			String responseBody = response.getContentAsString();
			ObjectMapper objmap = new ObjectMapper();
			List<LinkedHashMap<String, Object>> quiz_list2= objmap.readValue(responseBody,new TypeReference<List<LinkedHashMap<String, Object>>>() {});
			System.out.println("checkpoint");
			System.out.println(quiz_list2);
			assertEquals("JAVA_CORE",quiz_list2.get(1).get("quizTopic"));
			assertEquals("JAVA_CORE",quiz_list2.get(0).get("quizTopic"));
			
		}
		
		@Test
		public void studentController_takequizSubmit_SubmitsTheQuiz() throws Exception {
	 
			Result result = new Result();
			when(quizService.calculateResult(49,result )).thenReturn(new ResponseEntity<>("Result Message", HttpStatus.OK));
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/takequiz/49/submit").contentType(MediaType.APPLICATION_JSON)
					.content(
							new ObjectMapper().writeValueAsString(result)
							))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			MockHttpServletResponse response = requestResult.getResponse();
			assertEquals(200, response.getStatus());
			
		}

		@Test
		public void studentController_calculateResult() throws Exception {
			List<Response> listResponse = new ArrayList<Response>();
			Result result1 =  new Result(1,100,listResponse,"vishwajeet",95.2,85);
			
			Map<String,Object> result_map = new LinkedHashMap<>();
			String resultString = "Congratulations! You have passed with 95.2% correct answers.";
			ResponseEntity<String> responseEntity = ResponseEntity.ok("Congratulations! You have passed with 95.2% correct answers.");
			when(quizService.calculateResult(1,result1)).thenReturn(responseEntity);
			
			MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/takequiz/1/submit").contentType(MediaType.APPLICATION_JSON)
					.content(
							new ObjectMapper().writeValueAsString(result1)
							))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			System.out.println(requestResult);
			MockHttpServletResponse response = requestResult.getResponse();
			String responseBody = response.getContentAsString();
			assertEquals(200, response.getStatus());
			
		}
		
}
