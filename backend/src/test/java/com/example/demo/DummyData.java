package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Questions;
import com.example.demo.entity.Quiz;

public class DummyData {


    public static Questions createQuestion(String questionStatement, String option1, String option2, String option3, String option4, int correctOption) {

	    Questions question = new Questions();
	    question.setQuestionStatement(questionStatement);
	    question.setOption1(option1);
	    question.setOption2(option2);
	    question.setOption3(option3);
	    question.setOption4(option4);
	    question.setCorrectOption(correctOption);
	    return question;
	}

	public static Quiz createQuiz(String quizName, int durationPerQuestion, String quizStatus, String quizTopic, List<Questions> questions, int totalNumberOfQuestions, String username) {
	    Quiz quiz = new Quiz();
	    quiz.setQuizName(quizName);
	    quiz.setDuration_per_question(durationPerQuestion);
	    quiz.setQuiz_status(quizStatus);
	    quiz.setQuizTopic(quizTopic);
	    quiz.setListOfquestions(questions);
	    quiz.setTotal_number_of_questions(totalNumberOfQuestions);
	    quiz.setUsername(username);
	    return quiz;
	}
	public static List<Questions> generate20Questions() {
	    List<Questions> questionsList = new ArrayList<>();
	    for (int i = 1; i <= 20; i++) {
	        questionsList.add(createQuestion(
	            "Question " + i,
	            "Option A " + i,
	            "Option B " + i,
	            "Option C " + i,
	            "Option D " + i,

	             i

	        ));
	    }
	    return questionsList;
	}
}
