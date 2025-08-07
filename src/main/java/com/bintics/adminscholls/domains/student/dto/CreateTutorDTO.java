package com.bintics.adminscholls.domains.student.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTutorDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String lastName;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String phone;

    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String address;

    @NotBlank(message = "La relación con el estudiante es obligatoria")
    @Size(max = 50, message = "La relación no puede exceder 50 caracteres")
    private String relationship; // Padre, Madre, Tutor Legal, etc.

    @Size(max = 50, message = "La ocupación no puede exceder 50 caracteres")
    private String occupation;

    // PublicId del estudiante (opcional)
    private String studentPublicId;
}
