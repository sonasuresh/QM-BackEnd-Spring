package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;
import com.example.demo.model.Users;

@Repository
public interface UsersDAO extends JpaRepository<Users,Integer>{
	
	  @Query(value = "select password from users where name = ?1",nativeQuery = true)
   	  String getHashedPassword(String name);
}
