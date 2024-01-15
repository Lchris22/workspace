package com.example.demo.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Response {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int ResponseId;
	
	private Integer questionId;
	private Integer response;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "resultId",name = "result_Id")
	@JsonBackReference
	private Result result;

	public int getResponseId() {
		return ResponseId;
	}

	public void setResponseId(int responseId) {
		ResponseId = responseId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getResponse() {
		return response;
	}

	public void setResponse(Integer response) {
		this.response = response;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Response(int responseId, Integer questionId, Integer response, Result result) {
		super();
		ResponseId = responseId;
		this.questionId = questionId;
		this.response = response;
		this.result = result;
	}

	public Response() {
		super();
	}	
}
