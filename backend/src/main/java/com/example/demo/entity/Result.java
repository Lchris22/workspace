package com.example.demo.entity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "result")
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int resultId;
	
    private int quizId;
	
	@OneToMany(
			mappedBy = "result",
			cascade = CascadeType.ALL
			)
	@JsonManagedReference
	private List<Response> listOfResponses = new ArrayList<>();
	
	private String username;
	
	private double percentage;
	
	private Integer score;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public List<Response> getListOfResponses() {
		return listOfResponses;
	}

	public void setListOfResponses(List<Response> listOfResponses) {
		this.listOfResponses = listOfResponses;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Result() {
		super();
	}

public Result(int resultId, int quizId, List<Response> listOfResponses, String username, double percentage,
		Integer score) {
	super();
	this.resultId = resultId;
	this.quizId = quizId;
	this.listOfResponses = listOfResponses;
	this.username = username;
	this.percentage = percentage;
	this.score = score;
}	
}
