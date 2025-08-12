package com.bintics.adminscholls.domains.student.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String publicId) {
        super("El estudiante con publicId " + publicId + " no existe");
    }
}

