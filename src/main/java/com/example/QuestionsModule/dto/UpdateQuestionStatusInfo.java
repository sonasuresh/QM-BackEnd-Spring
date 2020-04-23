package com.example.QuestionsModule.dto;

import java.util.List;


public class UpdateQuestionStatusInfo {
	private List<Integer> id;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Integer> getId() {
		return id;
	}
	public void setId(List<Integer> id) {
		this.id = id;
	}
	}
