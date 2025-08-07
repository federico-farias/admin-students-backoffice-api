package com.bintics.adminscholls.domains.student.exception;

import java.util.List;

public class EmergencyContactsNotFoundException extends RuntimeException {
    private final List<String> notFoundContactIds;

    public EmergencyContactsNotFoundException(List<String> notFoundContactIds) {
        super("Los siguientes contactos de emergencia no existen: " + String.join(", ", notFoundContactIds));
        this.notFoundContactIds = notFoundContactIds;
    }

    public List<String> getNotFoundContactIds() {
        return notFoundContactIds;
    }
}
