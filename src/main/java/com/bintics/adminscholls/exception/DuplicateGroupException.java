package com.bintics.adminscholls.exception;

public class DuplicateGroupException extends RuntimeException {

    public DuplicateGroupException(String message) {
        super(message);
    }

    public DuplicateGroupException(String academicLevel, String grade, String name, String academicYear) {
        super(String.format("Ya existe un grupo activo con nivel académico '%s', grado '%s', nombre '%s' y año académico '%s'",
                          academicLevel, grade, name, academicYear));
    }
}
