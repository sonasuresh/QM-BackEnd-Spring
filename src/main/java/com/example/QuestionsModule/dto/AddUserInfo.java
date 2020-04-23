package com.example.QuestionsModule.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

public class AddUserInfo {
	
	//private int id;
	private String email;
	private List<Integer> roleId;
	
	//private String name;
	//private String password;
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public int getRoleId() {
//		return roleId;
//	}
//	public void setRoleId(int roleId) {
//		this.roleId = roleId;
//	}
	public List<Integer> getRoleId() {
		return roleId;
	}
	public void setRoleId(List<Integer> roleId) {
		this.roleId = roleId;
	}
}
