package com.harsha.demo.dao;

import com.harsha.demo.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizeDao extends JpaRepository<Quiz,Integer>{
}
