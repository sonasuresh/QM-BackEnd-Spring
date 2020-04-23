package com.example.QuestionsModule.dto;

public class MatchInfo {
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
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getMatch_option_id() {
		return match_option_id;
	}
	public void setMatch_option_id(int match_option_id) {
		this.match_option_id = match_option_id;
	}
	private String col_a;
	private String col_b;
	private int mid;
	private int match_option_id;
}
