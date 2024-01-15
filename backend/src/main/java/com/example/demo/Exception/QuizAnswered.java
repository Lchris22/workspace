package com.example.demo.Exception;

public class QuizAnswered extends Exception {
	public QuizAnswered(){
		super("Quiz already taken by user.");
	}
}
