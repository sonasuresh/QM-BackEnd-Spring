package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.exception.DBException;
import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select users.id,users.email,users.username,users.password,roles.name as role,users.isactive from users  join roles join user_role where user_role.user_Id=users.id and user_role.role_Id=roles.id and users.username=?;", nativeQuery = true)
	Optional<User> findByUserName(String userName);

	@Query(value = "select users.id,users.email,users.username,users.password,roles.name as role,users.isactive from users  join roles join user_role where user_role.user_Id=users.id and user_role.role_Id=roles.id;", nativeQuery = true)
	List<User> findAllUsers() throws DBException;

	@Transactional
	@Modifying
	@Query(value = "update users set username=?1,password=?2 where email=?3", nativeQuery = true)

	void updateDetails(String username, String password, String email) throws DBException;

	@Query(value = "select email from users where email=?;", nativeQuery = true)
	String exists(String email) throws DBException;

	@Query(value = "select password from users where username=?;", nativeQuery = true)
	String getHashedPassword(String name) throws DBException;

}
