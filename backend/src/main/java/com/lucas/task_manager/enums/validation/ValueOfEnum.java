package com.lucas.task_manager.enums.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
@Documented
public @interface ValueOfEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "deve ser um dos valores definidos no enum: {enumClass}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}