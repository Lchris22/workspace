package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Exception.QuizNotFoundException;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.User;
import com.example.demo.service.QuizService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* RestController class handling user-related endpoints with admin access.
* All endpoints are under the "/admin" base path.
*/
@RestController
@RequestMapping("/admin")
@Api(tags="Admin Quiz Management",description = "Endpoints for managing admin's related operations.")
public class AdminController {
	
	/**
     * Service for managing user-related operations.
     */
	@Autowired 
	private UserService userService;
	
	@Autowired
	private QuizService quizService;
	
	/**
     * Creates a new user using the provided user object.
     *
     * @param user The User object containing user details.
     * @return The created User object.
     * @throws Exception If an error occurs during user creation.
     */
	@ApiOperation(value="Add a new user as trainer or student",notes = "Allows admin to create a new user account as a student or trainer.<br>"+
     "Use this endpoint to seccurly register a user  with necessary permissions and access levels.<br>"+
     		 "<strong>Permissions:</strong> Only accessible to user with a admin privileges<br>"
     		 		+ "<strong>Request Body:</strong> Provide user details such as name,password,status and role")
	@PostMapping("/addUser")
	public User createUser(@RequestBody User user) throws Exception {
		return userService.createUser(user);
	}
	
	/**
     * Retrieves a list of all users.
     *
     * @return List of User objects representing all users.
     */
	
	@ApiOperation(value = "Get all users present in system",
			notes = "Retrieves a list of all users registered in system. This endpoint provides details of all users,including students,trainers and admin.<br>"
			+"<strong>Permissions:</strong> Only accessible to user with a admin privileges<br>"
					+"<strong>Response:</strong> Return a list of user object <br>")
	@GetMapping("/getAllUsers")
	 public List<User> showUser(){
		
		return userService.getAllUsers();
		
	}
	
	/**
	 * Updates the status of a quiz with the specified ID by the admin.
	 *
	 * @param id     The ID of the quiz to be updated.
	 * @param status The desired status for the quiz ("Active" or "InActive").
	 * @return The updated quiz object with the specified status.
	 */
	
	@ApiOperation(value="Update status of quiz(admin-only)",
			notes="Allows admin to update the status of quiz,such as ACTIVE or INACTIVE.<br>"+
					"<strong>Permissions:</strong> Only accessible to user with a admin privileges<br>"+
					"<strong>Request Body:</strong> Provide quizId and status(ACTIVE OR INACTIVE)")
	@PutMapping("/updateStatusOfQuiz/{id}")
	public Quiz updateStatusOfQuizToInactive(@PathVariable int id, @RequestBody String status) {
		try {
			return quizService.updateStatusOfQuiz(id,status);
		} catch (Exception e) {
			if (e.getClass()==QuizNotFoundException.class) {
				System.out.println("Quiz Not Found Exception");
				return null;
			}
			return null;
		}
		
	}
}
