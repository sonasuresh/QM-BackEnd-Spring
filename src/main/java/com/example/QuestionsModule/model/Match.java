package com.example.QuestionsModule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "match")
public class Match {
	@Id
	@Column(name = "`id`")
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCol_a() {
		return col_a;
	}
	public void setCol_a(String col_a) {
		this.col_a = col_a;
	}
	public String getCol_b() {
		return col_b;
	}
	public void setCol_b(String col_b) {
		this.col_b = col_b;
	}
	
	

	public int getQ_id() {
		return q_id;
	}
	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}


	public int getMatch_option_id() {
		return match_option_id;
	}
	public void setMatch_option_id(int match_option_id) {
		this.match_option_id = match_option_id;
	}


	@Column(name = "`q_id`")
	private int q_id;
	@Column(name = "`col_a`")
	private String col_a;
	@Column(name = "`col_b`")
	private String col_b;
	@Column(name="`match_option_id`")
	private int match_option_id;
	

}
