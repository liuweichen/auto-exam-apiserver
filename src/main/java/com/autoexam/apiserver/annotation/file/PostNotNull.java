package com.autoexam.apiserver.annotation.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostNotNullConstraint.class)
public @interface PostNotNull {
  String message() default "Null parameter for Post method";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
