package com.lucas.task_manager.enums;

public enum Status {
    PENDING("Pendente"),
    IN_PROGRESS("Em andamento"),
    COMPLETED("Concluída"),
    DELETED("Removida");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

