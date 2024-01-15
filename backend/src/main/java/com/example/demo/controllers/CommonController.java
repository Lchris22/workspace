package com.example.demo.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.QuizService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Quiz LeaderBoard",description  = "Endpoint for accessing quiz leaderboard")
public class CommonController {
	
	@Autowired
	private QuizService quizService;
	
	/**
	 * Gets list of Top 3 Students.
	 *
	 * @param Id of the result.
	 * @return List of top scorers.
	 */
	@ApiOperation(value = "Get leaderboard by quiz ID",notes = "Retrieves the top 3 students on leaderboard for a specific identified quiz by it's id.<br>"
			+ "This endpoint allows access to the top-performing students for a particular quiz,providing insghts into the highest achievers."
	 )
	@GetMapping("/LeaderBoard/{id}")
	public List<LinkedHashMap<String, Object>> getleaderBoard(@PathVariable int id) {
		return quizService.showLeaderBoard(id);
	}
}
