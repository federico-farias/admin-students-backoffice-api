package com.bintics.adminscholls.domains.student.exception;

public class NoTutorsProvidedException extends RuntimeException {
    public NoTutorsProvidedException() {
        super("Es obligatorio proporcionar al menos un tutor para el estudiante");
    }
}
