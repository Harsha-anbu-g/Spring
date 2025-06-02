package com.harsha.demo.service;

import com.harsha.demo.model.Question;
import com.harsha.demo.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        try {
            Optional<Question> existingQuestion = questionDao.findById(id);  // Find the existing question by id
            if (existingQuestion.isPresent()) {
                Question updatedQuestion = existingQuestion.get();
                updatedQuestion.setQuestionTitle(question.getQuestionTitle());  // Update fields
                updatedQuestion.setOption1(question.getOption1());  // Update fields
                updatedQuestion.setOption2(question.getOption2());  // Update fields
                updatedQuestion.setOption3(question.getOption3());  // Update fields
                updatedQuestion.setOption4(question.getOption4());  // Update fields
                updatedQuestion.setRightAnswer(question.getRightAnswer());  // Update fields
                updatedQuestion.setDifficultyLevel(question.getDifficultyLevel());  // Update fields
                updatedQuestion.setCategory(question.getCategory());  // Update fields
                questionDao.save(updatedQuestion);  // Save the updated question
                return new ResponseEntity<>( "success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>( "Question not found", HttpStatus.INTERNAL_SERVER_ERROR);  // Return a message if the question doesn't exist
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            Optional<Question> question = questionDao.findById(id);  // Find the question by id
            if (question.isPresent()) {
                questionDao.delete(question.get());  // Delete the question if found
                return new ResponseEntity<>("success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>( "Question not found", HttpStatus.INTERNAL_SERVER_ERROR);  // Return a message if the question doesn't exist
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




