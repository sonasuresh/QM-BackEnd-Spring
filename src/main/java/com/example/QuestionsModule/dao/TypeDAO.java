package com.example.QuestionsModule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuestionsModule.model.Types;

@Repository
public interface TypeDAO extends JpaRepository<Types, Integer> {

}
