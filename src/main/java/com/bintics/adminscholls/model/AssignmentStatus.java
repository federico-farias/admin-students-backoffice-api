package com.bintics.adminscholls.model;

public enum AssignmentStatus {
    ACTIVA("Activa"),
    FINALIZADA("Finalizada"),
    TRANSFERIDA("Transferida"),
    CANCELADA("Cancelada");

    private final String displayName;

    AssignmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
