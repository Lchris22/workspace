package com.example.demo.entity;

public enum QuizStatus {
	ACTIVE,
	INACTIVE,;

	
	private String quizStatus;
	
	public String getTopic() {
		return this.quizStatus;
	}
	public static QuizStatus fromString(String quiz_status) {
			try {
			return QuizStatus.valueOf(quiz_status);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("No enum constant for " + quiz_status);
		}
	}
}
