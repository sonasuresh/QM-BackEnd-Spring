package com.example.QuestionsModule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "multiple_choice")
public class MultipleChoice {
	@Id
	@Column(name = "`id`")
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
	
	@Column(name = "`value`")
	private String value;
	@Column(name = "`is_yes`")
	private int is_yes;
	public int getIs_yes() {
		return is_yes;
	}
	public void setIs_yes(int is_yes) {
		this.is_yes = is_yes;
	}
	public int getIs_sticky() {
		return is_sticky;
	}
	public void setIs_sticky(int is_sticky) {
		this.is_sticky = is_sticky;
	}

	public int getQ_id() {
		return q_id;
	}
	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}

	@Column(name = "`is_sticky`")
	
	private int is_sticky;
	@Column(name = "`q_id`")
	private int q_id;


}
