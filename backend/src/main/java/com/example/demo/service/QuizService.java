package com.example.demo.service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import com.example.demo.entity.QuestionWrapper;
import com.example.demo.Exception.QuizAnswered;
import com.example.demo.Exception.QuizNotFoundException;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.Result;

public interface QuizService {
	Quiz getQuizById(int id) throws Exception;
	Quiz addQuiz(Quiz q) throws Exception;
	List<Quiz> getQuizByUsername() throws QuizNotFoundException;
	List<Map<String, Object>> getQuizByquizTopic(String quizTopic) throws QuizNotFoundException;
	ResponseEntity<List<QuestionWrapper>> takeQuiz(Integer id,String username)throws QuizAnswered;
	ResponseEntity<String> calculateResult(Integer id, Result result);
	Set<String> getAllTopics();
	List<LinkedHashMap<String, Object>> getQuizByquizTopics(String quizTopic);
	Quiz updateStatusOfQuiz(int id, String status) throws QuizNotFoundException;
	List<LinkedHashMap<String, Object>> showLeaderBoard(int id);
}
