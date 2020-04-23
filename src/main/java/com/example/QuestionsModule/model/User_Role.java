package com.example.QuestionsModule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class User_Role {
	@Id
	@Column(name = "`id`")
	private int id;
	@Column(name = "`user_Id`")
	private int user_Id;
	public int getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}
	@Column(name = "`role_Id`")
	private int role_Id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole_Id() {
		return role_Id;
	}
	public void setRole_Id(int roleId) {
		this.role_Id = roleId;
	}
}
