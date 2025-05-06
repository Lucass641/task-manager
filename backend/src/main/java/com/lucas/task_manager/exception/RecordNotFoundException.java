package com.lucas.task_manager.exception;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(Long id) {
        super("Registro não encontrado com o id: " + id);
    }
}
