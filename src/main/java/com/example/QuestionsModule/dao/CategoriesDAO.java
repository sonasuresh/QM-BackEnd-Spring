package com.example.QuestionsModule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuestionsModule.model.Category;

@Repository
public interface CategoriesDAO extends JpaRepository<Category, Integer> {

}
