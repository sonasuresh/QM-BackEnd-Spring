package com.example.QuestionsModule.dto;

public class NumericalInfo {
	private String answer;
	//private String 
	public String getAnswer() {
		return answer;
	}
	private String correctExplanation;
	private String wrongExplanation;
	public String getCorrectExplanation() {
		return correctExplanation;
	}
	public void setCorrectExplanation(String correctExplanation) {
		this.correctExplanation = correctExplanation;
	}
	public String getWrongExplanation() {
		return wrongExplanation;
	}
	public void setWrongExplanation(String wrongExplanation) {
		this.wrongExplanation = wrongExplanation;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
