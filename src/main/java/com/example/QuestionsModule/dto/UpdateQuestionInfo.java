package com.example.QuestionsModule.dto;

import java.sql.Time;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

public class UpdateQuestionInfo {
	private int id;
	private int mid;
	private String title;
	
	private int category_Id;
	
	private String content;
	private String tags;
	
	private int level_Id;
	private int skill_points;
	private String status;
	private int score;
	
	private Time duration;
	
	private int type_Id;	
	private True_FalseInfo true_false;
	private ShortAnswerInfo shortAnswer;
	private NumericalInfo numericalValue;
	private List<MatchInfo> match;
	private List<BestChoiceInfo> best_choice;
	private List<MultipleChoiceInfo> multiple_choice;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory_Id() {
		return category_Id;
	}

	public void setCategory_Id(int category_Id) {
		this.category_Id = category_Id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLevel_Id() {
		return level_Id;
	}

	public void setLevel_Id(int level_Id) {
		this.level_Id = level_Id;
	}

	public int getSkill_points() {
		return skill_points;
	}

	public void setSkill_points(int skill_points) {
		this.skill_points = skill_points;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public int getType_Id() {
		return type_Id;
	}

	public void setType_Id(int type_Id) {
		this.type_Id = type_Id;
	}

	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public True_FalseInfo getTrue_false() {
		return true_false;
	}
	public void setTrue_false(True_FalseInfo true_false) {
		this.true_false = true_false;
	}
	public NumericalInfo getNumericalValue() {
		return numericalValue;
	}
	public void setNumericalValue(NumericalInfo numericalValue) {
		this.numericalValue = numericalValue;
	}
	public ShortAnswerInfo getShortAnswer() {
		return shortAnswer;
	}
	public void setShortAnswer(ShortAnswerInfo shortAnswer) {
		this.shortAnswer = shortAnswer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public List<MatchInfo> getMatch() {
		return match;
	}
	public void setMatch(List<MatchInfo> match) {
		this.match = match;
	}
	
	public List<BestChoiceInfo> getBest_choice() {
		return best_choice;
	}
	public void setBest_choice(List<BestChoiceInfo> best_choice) {
		this.best_choice = best_choice;
	}
	public List<MultipleChoiceInfo> getMultiple_choice() {
		return multiple_choice;
	}
	public void setMultiple_choice(List<MultipleChoiceInfo> multiple_choice) {
		this.multiple_choice = multiple_choice;
	}


	
}
