package com.example.QuestionsModule.model;

import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.Join;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name = "questions")
public class Question {
	@Id
	@Column(name = "`id`")
	private int id;
	@Column(name = "`title`")
	private String title;
	@OneToOne
	@JoinColumn(name = "`category_Id`")
	private Category category_Id;
	@Column(name = "`content`")
	private String content;
	@OneToOne
	@JoinColumn(name = "`level_Id`")
	private Level level_Id;
	@Column(name = "`skill_points`")
	private int skill_points;
	@Column(name = "`score`")
	private int score;
	@Column(name="tags")
	private String tags;
	@Column(name = "`duration`")
	private Time duration;
	@OneToOne
	@JoinColumn(name = "`type_Id`")
	private Types type_Id;
	
	@Column(name = "`status`")
	private String status;
	
	@Column(name = "`option`")
	private String option;
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
	public Category getCategory_Id() {
		return category_Id;
	}
	public void setCategory_Id(Category category_Id) {
		this.category_Id = category_Id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Level getLevel_Id() {
		return level_Id;
	}
	public void setLevel_Id(Level level_Id) {
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
	public Types getType_Id() {
		return type_Id;
	}
	public void setType_Id(Types type_Id) {
		this.type_Id = type_Id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	
}
