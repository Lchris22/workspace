package com.example.demo.entity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "quiz")
public class Quiz {
   
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int quizId;
	private String quizName;
	private int total_number_of_questions;
    @Enumerated(EnumType.STRING)
	private Topic quizTopic;
    @Enumerated(EnumType.STRING)
	private QuizStatus quiz_status;
	private int total_duaration_of_quiz;
	private int duration_per_question;
	
	

	public String getQuizTopic() {
		return quizTopic.toString();
	}



	public void setQuizTopic(String quizTopic) {
		this.quizTopic = Topic.fromString(quizTopic);
	}



	@OneToMany(
			mappedBy = "quiz",
			cascade = CascadeType.ALL
			)
	@JsonManagedReference
	private List<Questions> listOfquestions = new ArrayList<>();

	private String username;


	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public int getQuizId() {
		return quizId;
	}



	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}


	public String getQuizName() {
		return quizName;
	}



	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}



	public int getTotal_number_of_questions() {
		return total_number_of_questions;
	}



	public void setTotal_number_of_questions(int total_number_of_questions) {
		this.total_number_of_questions = total_number_of_questions;
	}



	public String getQuiz_status() {
		return quiz_status.toString();
	}



	public void setQuiz_status(String quiz_status) {
		this.quiz_status = QuizStatus.fromString(quiz_status);
	}



	public int getTotal_duaration_of_quiz() {
		return total_duaration_of_quiz;
	}



	public void setTotal_duaration_of_quiz(int total_duaration_of_quiz) {
		this.total_duaration_of_quiz = total_duaration_of_quiz;
	}



	public int getDuration_per_question() {
		return duration_per_question;
	}



	public void setDuration_per_question(int duration_per_question) {
		this.duration_per_question = duration_per_question;
	}



	public List<Questions> getListOfquestions() {
		return listOfquestions;
	}



	public void setListOfquestions(List<Questions> listOfquestions) {
		this.listOfquestions = listOfquestions;
	}



	public Quiz() {
		super();
	}



	public Quiz(int quizId, String quizName, int total_number_of_questions, String quizTopic, String quiz_status,
			int total_duaration_of_quiz, int duration_per_question, List<Questions> listOfquestions, String username) {
		super();
		this.quizId = quizId;
		this.quizName = quizName;
		this.total_number_of_questions = total_number_of_questions;
		this.quizTopic = Topic.fromString(quizTopic);
		this.quiz_status = QuizStatus.fromString(quiz_status);
		this.total_duaration_of_quiz = total_duaration_of_quiz;
		this.duration_per_question = duration_per_question;
		this.listOfquestions = listOfquestions;
		this.username = username;
	}






	





	
	

}
