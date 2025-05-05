package com.lucas.task_manager.enums.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private Set<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = new HashSet<>();
        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        for (Enum<?> enumVal : enumClass.getEnumConstants()) {
            acceptedValues.add(enumVal.toString()); // usa o toString() que pode retornar getValue()
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || acceptedValues.contains(value);
    }
}
