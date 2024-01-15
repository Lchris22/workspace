package com.example.demo;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controllers.AdminController;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.service.QuizService;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserControllerTest {

	
	@Mock
	private UserServiceImpl userService;
	
	@Mock
	private QuizService quizService;
	
	@InjectMocks
	private AdminController userController;
	
	private MockMvc mockMvc;
	
	private User admin;
	@BeforeEach
	public void setUp() {
//		MockitoAnnotations.initMocks(this);
		admin = new User(1l,"admin","admin@123",UserRole.ROLE_ADMIN,"Active");
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
	}
	
	@Test
	public void getAllUsers() throws Exception{
		User user = new User(3l,"Lenin","Lenin@12345",UserRole.ROLE_STUDENT,"Active");
		User user1 = new User(3l,"Vaibhav","Vaibhav@12345",UserRole.ROLE_STUDENT,"Active");
		
		List<User> userList = new ArrayList<>();
		userList.add(user);
		userList.add(user1);
		
		when(userService.getAllUsers()).thenReturn(userList);
		
		MvcResult requestResult =  mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllUsers").contentType(MediaType.APPLICATION_JSON).
				content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();;
				
	}
	
	@Test
	public void createUser_testCreateUserAPI() throws Exception{
	
		User user = new User(3l,"Vishwajeet","Jeet@12345",UserRole.ROLE_STUDENT,"Active");
		when(userService.createUser(user)).thenReturn(user);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser").contentType(MediaType.APPLICATION_JSON).
				content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());
	
	}
	@Test
	public void updateStatus_testCaseToInactive() throws Exception{
		List<Questions> question_list = DummyData.generate20Questions();
		
		Quiz quiz = new Quiz(100,"CPP Basics", 10, "CPP", "INACTIVE", 20, 2, question_list, "pratibh");
		
		when(quizService.updateStatusOfQuiz(10, "INACTIVE")).thenReturn(quiz);
		
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateStatusOfQuiz/{id}",10).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString("INACTIVE")))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
	}
	
}
