package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

	UserRepository userRepository;
	UserServiceImpl userService;
	public UserServiceTest() {
		userRepository = mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());
		
	}
	
	/** create user when user not present*/
	@Test
	public void createUser_whenUserNotPresent() throws Exception {
		
		User user = new User(1l,"Vishwajeet","Jeet@12345",UserRole.ROLE_ADMIN,"Active");
		String password = "Jeet@2001";
		when(userRepository.save(user)).thenReturn(user);
		User createdUser = userService.createUser(user);
		
		assertNotNull(createdUser);
		assertEquals(createdUser.getUsername(), user.getUsername());
		assertNotEquals(createdUser.getPassword(), password);
		assertEquals(createdUser.getRole(), user.getRole());
		assertEquals(user.getStatus(), createdUser.getStatus());
	}
	
	/** This test case tries to create User when Already same user exist*/
	@Test
	public void createUser_whenUserExist() throws Exception{
		User user = new User(1l,"Vishwajeet","Jeet@12345",UserRole.ROLE_ADMIN,"Active");
		
		when(userRepository.findByUsername("vishwajeet")).thenReturn(new User());
		when(userRepository.save(user)).thenReturn(null);
		
		User creatingUser = userService.createUser(user);
		assertNull(creatingUser);
		
	}
	
	/** This test case Gets all the users when users are present in the Database*/
	@Test
	public void getAllUsers_whenUsersArePresent() {
		User user = new User(1l,"Vishwajeet","Jeet@12345",UserRole.ROLE_ADMIN,"Active");
		User user2 = new User(2l,"Lenin","Lenin@12345",UserRole.ROLE_TRAINER,"Active");
		List<User> templist = new ArrayList<>();
		templist.add(user);
		templist.add(user2);
		
		when(userRepository.findAll()).thenReturn(templist);
		List<User> userList = userService.getAllUsers();
		assertEquals(2, userList.size());
	}
	


}
