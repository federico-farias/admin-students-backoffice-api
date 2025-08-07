package com.bintics.adminscholls.shared.model;

public enum AssignmentStatus {
    ACTIVA,
    FINALIZADA,
    CANCELADA,
    TRANSFERIDA;

    public String getDisplayName() {
        return switch (this) {
            case ACTIVA -> "Activa";
            case FINALIZADA -> "Finalizada";
            case CANCELADA -> "Cancelada";
            case TRANSFERIDA -> "Transferida";
        };
    }
}
