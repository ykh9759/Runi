package com.example.runi.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyCustomValidator.class)
public @interface MyCustomValidation {
    String message() default "Custom validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
