package com.example.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.exception.DBException;
import com.example.demo.model.Match;

@Repository
public interface MatchDAO extends JpaRepository<Match, Integer> {
	@Transactional
	@Modifying
	@Query(value = "insert into question_db.match (`id`, `q_id`,`col_a`, `col_b`,`match_option_id`) values (0,?,?,?,?);", nativeQuery = true)
	void insert(int q_id, String cola, String colb, int match_option_id) throws DBException;

	@Transactional
	@Modifying
	@Query(value = "update question_db.match set col_a=?,col_b=?,match_option_id=? where q_id=? AND id=?;", nativeQuery = true)
	void update(String cola, String colb, int option, int q_id, int m_id) throws DBException;

	@Query(value = "SELECT * FROM question_db.match where q_id=?", nativeQuery = true)
	List<Match> match(int qid) throws DBException;

	@Transactional
	@Modifying
	@Query(value = "delete from question_db.match where id =?", nativeQuery = true)
	void delete(int id) throws DBException;
}
