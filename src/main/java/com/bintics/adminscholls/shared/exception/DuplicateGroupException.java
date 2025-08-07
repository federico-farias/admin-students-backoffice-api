package com.bintics.adminscholls.shared.exception;

public class DuplicateGroupException extends RuntimeException {

    public DuplicateGroupException(String academicLevel, String grade, String name, String academicYear) {
        super(String.format("Ya existe un grupo con nivel académico '%s', grado '%s', nombre '%s' para el año académico '%s'",
              academicLevel, grade, name, academicYear));
    }

    public DuplicateGroupException(String message) {
        super(message);
    }
}
