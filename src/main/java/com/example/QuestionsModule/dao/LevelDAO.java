package com.example.QuestionsModule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuestionsModule.model.Level;

@Repository
public interface LevelDAO extends JpaRepository<Level, Integer> {

}
