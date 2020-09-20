package com.autoexam.apiserver.beans;

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
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(generator = "admin_generator")
    @SequenceGenerator(
            name = "admin_generator",
            sequenceName = "admin_sequence",
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
}
