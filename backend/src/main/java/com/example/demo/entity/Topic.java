package com.example.demo.entity;

public enum Topic {
	JAVA_CORE,
	CPP,
	UI,
	PYTHON;	
	
	private String topicName;
	
	public String getTopic() {
		return this.topicName;
	}
 
	public static Topic fromString(String topic) {
		try {
			return Topic.valueOf(topic);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("No enum constant for " + topic);
		}
	}
}
