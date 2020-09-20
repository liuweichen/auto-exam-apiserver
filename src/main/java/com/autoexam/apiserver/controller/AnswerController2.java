package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.Answer2;
import com.autoexam.apiserver.exception.ResourceNotFoundException;
import com.autoexam.apiserver.dao.AnswerDao2;
import com.autoexam.apiserver.dao.QuestionDao2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController2 {

    @Autowired
    private AnswerDao2 answerDao2;

    @Autowired
    private QuestionDao2 questionDao2;

    @GetMapping("/questions/{questionId}/answers")
    public List<Answer2> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerDao2.findByQuestionId(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    public Answer2 addAnswer(@PathVariable Long questionId,
                             @Valid @RequestBody Answer2 answer2) {
        return questionDao2.findById(questionId)
                .map(question -> {
                    answer2.setQuestion(question);
                    return answerDao2.save(answer2);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public Answer2 updateAnswer(@PathVariable Long questionId,
                                @PathVariable Long answerId,
                                @Valid @RequestBody Answer2 answer2Request) {
        if(!questionDao2.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }

        return answerDao2.findById(answerId)
                .map(answer2 -> {
                    answer2.setText(answer2Request.getText());
                    return answerDao2.save(answer2);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId,
                                          @PathVariable Long answerId) {
        if(!questionDao2.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }

        return answerDao2.findById(answerId)
                .map(answer2 -> {
                    answerDao2.delete(answer2);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));

    }
}
