package com.autoexam.apiserver.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(generator = "teacher_generator")
    @SequenceGenerator(
            name = "teacher_generator",
            sequenceName = "teacher_sequence",
            initialValue = 1000
    )
    @Column(name = "id", columnDefinition = "bigserial")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 128, message = "name length should be between 3 and 128")
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Admin admin;
}
