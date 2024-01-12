package com.example.demo.Exception;

public class QuestionsLengthException extends Exception{
	
	public QuestionsLengthException(){
		super("Total no of Questions are not Equal to 20");
	}

}
