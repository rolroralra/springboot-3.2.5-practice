package com.example.project.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> entityClass) {
        super(entityClass.getSimpleName() + " not found");
    }

    public NotFoundException(Class<?> entityClass, Long id) {
        super(entityClass.getSimpleName() + " not found with id: " + id);
    }
}
