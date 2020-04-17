package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MultipleChoice;
import com.example.demo.exception.DBException;
import com.example.demo.model.Match;

@Repository
public interface MultipleChoiceDAO extends JpaRepository<MultipleChoice, Integer> {
	@Transactional
	@Modifying
	@Query(value = "insert into question_db.multiple_choice (`id`, `value`,`is_sticky`, `is_yes`,`q_id`) values (0,?,?,?,?);", nativeQuery = true)
	void insert(String value, int sticky, int yes, int id) throws DBException;

	@Transactional
	@Modifying
	@Query(value = "update question_db.multiple_choice set value=?,is_sticky=?,is_yes=? where q_id=? AND id=?;", nativeQuery = true)
	void update(String value, int isSticky, int isYes, int id, int id2) throws DBException;

	@Query(value = "SELECT * FROM question_db.multiple_choice where q_id=?", nativeQuery = true)
	List<MultipleChoice> getMultipleChoice(int qid) throws DBException;

	@Query(value = "DELETE FROM question_db.multiple_choice WHERE id IN(?);", nativeQuery = true)
	void delete(List<Integer> id) throws DBException;

}
