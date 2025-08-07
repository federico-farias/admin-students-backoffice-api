package com.bintics.adminscholls.domains.student.exception;

import java.util.List;

public class TutorsNotFoundException extends RuntimeException {
    private final List<String> notFoundTutorIds;

    public TutorsNotFoundException(List<String> notFoundTutorIds) {
        super("Los siguientes tutores no existen: " + String.join(", ", notFoundTutorIds));
        this.notFoundTutorIds = notFoundTutorIds;
    }

    public List<String> getNotFoundTutorIds() {
        return notFoundTutorIds;
    }
}

