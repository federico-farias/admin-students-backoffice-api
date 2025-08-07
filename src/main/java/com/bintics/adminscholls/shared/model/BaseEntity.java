package com.bintics.adminscholls.shared.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    // Esta clase base puede contener campos comunes a todas las entidades
    // Por ahora está vacía, pero se puede extender según necesidades futuras
}
