package com.harsha.demo.service;

import com.harsha.demo.dao.QuestionDao;
import com.harsha.demo.dao.QuizeDao;
import com.harsha.demo.model.Question;
import com.harsha.demo.model.QuestionWrapper;
import com.harsha.demo.model.Quiz;
import com.harsha.demo.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizeService {

    @Autowired
    QuizeDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            List<Question> question = questionDao.findRandomQuestionByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(question);
            quizDao.save(quiz);

            return new ResponseEntity<>("Quiz created", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Quiz creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizeQuestion(Integer id) {

        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses) {
            if (response.getResponse().trim().equalsIgnoreCase(questions.get(i).getRightAnswer().trim())) {
                right++;
            }

            right++;

            i++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);

    }
}