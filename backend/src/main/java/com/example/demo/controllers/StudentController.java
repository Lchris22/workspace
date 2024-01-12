package com.example.demo.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Exception.QuizAnswered;
import com.example.demo.entity.QuestionWrapper;
import com.example.demo.entity.Result;
import com.example.demo.service.QuizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * RestController class handling Endpoints related to quizzes and user actions.
 * All Endpoints are under the "/" base path.
 */
@RestController
@RequestMapping("/student")
@Api(tags="Student Quiz Management",description = "Endpoints for managing student's related operations.")
public class StudentController {

	/**
	 * Service for managing Quiz-related operations.
	 */
	@Autowired
	public QuizService quizService;

	/**
	 * Welcome message for the Assessment Platform.
	 *
	 * @return Welcome message for the platform.
	 */

	@GetMapping(path = "/")
	public String welcome() {
		return "Welocome to Assessment Platform";
	}

	/**
	 * Retrieves a All the topics.
	 *
	 * @return The entire HTTP response in ResponseEntity.
	 */
	@ApiOperation(value = "Get all topics", notes = "Retrieves all available topics stored in the database.<br>"
			+ "This endpoint is used to fetch a comprehensive list of topics.<br>"
			+ " It Enables student to access and display all existing topics in system.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrieved the Topics"),
			                @ApiResponse(code = 404, message = "Not Found"), })
	@GetMapping("/topics")
	public ResponseEntity<Set<String>> getAllTopics() {
		Set<String> topics = quizService.getAllTopics();
		return new ResponseEntity<>(topics, HttpStatus.OK);
	}

	/**
	 * Retrieves Questions of the quiz to be answered.
	 *
	 * @param id The ID of the quiz to attempt.
	 * @return The Questions of the quiz to be answered.
	 */
	
	@ApiOperation(value = "Student can attempt quiz by ID", notes = "Allows a student to attempt a particular quiz identified by it's ID.<br>"
			+ "This endpoint enables student to start and complete a specific quiz by it's ID.")

	@PostMapping("/takequiz/{id}")
	public ResponseEntity<List<QuestionWrapper>> takeQuiz(@PathVariable Integer id, @RequestBody String username) {
		try {
			return quizService.takeQuiz(id, username);
		} catch (QuizAnswered e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Retrieves a quiz topic name.
	 *
	 * @param Name of the topic from which quiz is required.
	 * @return List of the quiz.
	 */
	@ApiOperation(value = "Get quizzes by topic name for student",notes = "Retrieves quizzes based on the specified topic name to be attempted by a student.<br> "+""
			+ "This endpoint enables students to fetch quizzes associated with a particular topic name")
	@GetMapping("/getQuizByTopic/{topic}")
	public List<LinkedHashMap<String, Object>> getQuizByTopic(@PathVariable("topic") String quizTopic) {
		return quizService.getQuizByquizTopics(quizTopic);
	}

	/**
	 * Submits the quiz.
	 *
	 * @param Id of the quiz and result requestBody.
	 * @return String stating whether the individual has cleared the quiz.
	 */
	@ApiOperation(value="Submit completed/attempted quiz",notes = "Allows a student to submit a completed quiz for assessment and scoring.<br>"
			+ "This Endpoint finalize the student's attempt for the specified quiz")
	@PostMapping("/takequiz/{id}/submit")
	public ResponseEntity<String> submitQuiz(@PathVariable Integer id, @RequestBody Result result) {
		return quizService.calculateResult(id, result);
	}	
}