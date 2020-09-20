package com.autoexam.apiserver.beans;

import com.autoexam.apiserver.beans.base.AuditInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question2 extends AuditInfo {
    @Id
//    @GeneratedValue(generator = "question_generator")
//    @SequenceGenerator(
//            name = "question_generator",
//            sequenceName = "question_sequence",
//            initialValue = 1000
//    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Column(columnDefinition = "text")
    private String description;
}
