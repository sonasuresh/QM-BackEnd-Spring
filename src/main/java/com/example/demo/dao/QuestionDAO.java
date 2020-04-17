package com.example.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.exception.DBException;
import com.example.demo.model.Question;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {
	@Query(value = "select * from questions where status = ?1", nativeQuery = true)
	Page<Question> getActivatedQuestions(String status, Pageable paging) throws DBException;

	@Query(value = "select * from questions where status = ?1", nativeQuery = true)
	Page<Question> getDeactivatedQuestions(String status, Pageable paging) throws DBException;
	
	@Transactional
	@Modifying
	@Query(value = "update questions set status=?1 where id in(?2)", nativeQuery = true)
	void updateStatus(String status, List<Integer> id) throws DBException;

	@Query(value = "SELECT * FROM questions WHERE FIND_IN_SET(?1, tags) and status=?2", nativeQuery = true)
	Page<Question> getQuestionBasedOnTags(String tagName, String status, Pageable paging) throws DBException;

	@Query(value = "SELECT MAX(ID) AS LastID FROM questions;", nativeQuery = true)
	int getId() throws DBException;

}
