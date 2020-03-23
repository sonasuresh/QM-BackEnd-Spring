package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;
import com.example.demo.model.Types;

@Repository
public interface TypeDAO extends JpaRepository<Types,Integer>{

}
