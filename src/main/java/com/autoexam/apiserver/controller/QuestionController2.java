package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.Question2;
import com.autoexam.apiserver.exception.ResourceNotFoundException;
import com.autoexam.apiserver.dao.QuestionDao2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class QuestionController2 {

    @Autowired
    private QuestionDao2 questionDao2;

    @GetMapping("/questions")
    public Page<Question2> getQuestions(Pageable pageable) {
        return questionDao2.findAll(pageable);
    }


    @PostMapping("/questions")
    public Question2 createQuestion(@Valid @RequestBody Question2 question2) {
        return questionDao2.save(question2);
    }

    @PutMapping("/questions/{questionId}")
    public Question2 updateQuestion(@PathVariable Long questionId,
                                    @Valid @RequestBody Question2 question2Request) {
        return questionDao2.findById(questionId)
                .map(question2 -> {
                    question2.setTitle(question2Request.getTitle());
                    question2.setDescription(question2Request.getDescription());
                    return questionDao2.save(question2);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }


    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        return questionDao2.findById(questionId)
                .map(question2 -> {
                    questionDao2.delete(question2);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }
}
