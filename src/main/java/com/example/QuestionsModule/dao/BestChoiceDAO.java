package com.example.QuestionsModule.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.QuestionsModule.exception.DBException;
import com.example.QuestionsModule.model.BestChoice;
import com.example.QuestionsModule.model.Match;

@Repository
public interface BestChoiceDAO extends JpaRepository<BestChoice, Integer> {
	@Transactional
	@Modifying
	@Query(value = "insert into question_db.best_choice (`id`, `value`,`is_sticky`, `is_yes`,`q_id`) values (0,?,?,?,?);", nativeQuery = true)
	void insert(String value, int sticky, int yes, int id) throws DBException;

	@Transactional
	@Modifying
	@Query(value = "update question_db.best_choice set value=?,is_sticky=?,is_yes=? where q_id=? AND id=?;", nativeQuery = true)
	void update(String value, int isSticky, int isYes, int id, int id2) throws DBException;

	@Query(value = "SELECT * FROM question_db.best_choice where q_id=?", nativeQuery = true)
	List<BestChoice> getBestChoice(int qid) throws DBException;

	@Query(value = "DELETE FROM question_db.best_choice WHERE id IN(?);", nativeQuery = true)
	void delete(List<Integer> id) throws DBException;

}
