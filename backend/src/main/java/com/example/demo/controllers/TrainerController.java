package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Exception.QuizNotFoundException;
import com.example.demo.entity.Quiz;
import com.example.demo.service.QuizService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * RestController class handling Endpoints related to quizzes and user actions.
 * All Endpoints are under the "/" base path.
 */
@RestController
@RequestMapping("/trainer")
@Api(tags="Trainer Quiz Management",description = "Endpoints for managing trainer's related operations.")
public class TrainerController {

	/**
	 * Service for managing Quiz-related operations.
	 */
	@Autowired
	public QuizService quizService;
	
	/**
	 * Adds a new quiz to the platform.
	 *
	 * @param q1 The quiz object to be added.
	 * @return The added quiz object.
	 */
	@ApiOperation(value = "Trainer can add a new Quiz.",notes = "Enables a trainer to create and publish a new quiz for student access.<br>"+
	 "This endpoint enables trainer to add new quizzes to system.")
	@PostMapping("/addQuiz")
	public Quiz addQuiz(@RequestBody Quiz q1) {
		try {
			return quizService.addQuiz(q1);
		} catch (Exception e) {
			System.out.println(e);
		}
		return new Quiz();
	}

	/**
	 * Retrieves quizzes associated with a specific trainer (user) by username.
	 *
	 * @param username-->The username of the trainer.
	 * @return List of quizzes associated with the trainer.
	 */
	@ApiOperation(value = "Get all quizzes created by particular trainer",
			notes = "Retrieve a list of all quizzes created by a specific trainer identified by the trainer login credentials through which we get username<br>"+
	"<strong>Permissions:</strong> Accessible to trainers.<br>"+
					"<strong>Response:</strong> Returns a list of quizzes created by specified trainer.")
	@GetMapping("/getAllquiz")
	public List<Quiz> getAllQuizzes() {
		try {
			return quizService.getQuizByUsername();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Retrieves a list of quizzes based on the specified topic specifically for
	 * trainers.
	 *
	 * @param quizTopic The topic of the quizzes to retrieve.
	 * @return List of quizzes related to the specified topic for trainers.
	 */
	
	@ApiOperation(value = "Get all quizzes created by particular trainer by topic",
			notes = "Retrieve a list of all quizzes created by a specific trainer identified by the trainer login credentials and by the topic<br>"+
	"<strong>Permissions:</strong> Accessible to trainers.<br>"+
					"<strong>Response:</strong> Returns a list of quizzes created by specified trainer and the topic passed.")
	@GetMapping("/getquizByTopic/{topic}")
	public List<Map<String, Object>> getQuizByTopic(@PathVariable("topic") String quizTopic) {
		try {
			return quizService.getQuizByquizTopic(quizTopic);
		} catch (QuizNotFoundException e) {
			return null;
		}
	}	
}