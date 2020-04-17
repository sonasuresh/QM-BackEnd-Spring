package com.example.demo.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.exception.DBException;
import com.example.demo.model.User_Role;

public interface UserRoleDAO extends JpaRepository<User_Role, Integer> {

	@Transactional
	@Modifying
	@Query(value = "insert into user_role(user_Id,role_Id)values(?,?)", nativeQuery = true)
	void saveInto(int userId, int roleId) throws DBException;

}
