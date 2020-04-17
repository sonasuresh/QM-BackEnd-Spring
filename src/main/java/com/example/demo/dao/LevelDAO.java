package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Level;

@Repository
public interface LevelDAO extends JpaRepository<Level, Integer> {

}
