package com.example.QuestionsModule.dto;

public class MultipleChoiceInfo {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String value;
	private int isYes;
	public int getIsYes() {
		return isYes;
	}
	public void setIsYes(int isYes) {
		this.isYes = isYes;
	}
	public int getIsSticky() {
		return isSticky;
	}
	public void setIsSticky(int isSticky) {
		this.isSticky = isSticky;
	}
	private int isSticky;


}
