package com.lucas.task_manager.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.lucas.task_manager.enums.Status;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Stream.of(Status.values())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
