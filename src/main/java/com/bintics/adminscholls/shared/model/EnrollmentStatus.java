package com.bintics.adminscholls.shared.model;

public enum EnrollmentStatus {
    PENDIENTE,
    CONFIRMADA,
    COMPLETADA,
    CANCELADA,
    INSCRITO,
    RETIRADO,
    GRADUADO;

    public String getDisplayName() {
        return switch (this) {
            case PENDIENTE -> "Pendiente";
            case CONFIRMADA -> "Confirmada";
            case COMPLETADA -> "Completada";
            case CANCELADA -> "Cancelada";
            case INSCRITO -> "Inscrito";
            case RETIRADO -> "Retirado";
            case GRADUADO -> "Graduado";
        };
    }
}
