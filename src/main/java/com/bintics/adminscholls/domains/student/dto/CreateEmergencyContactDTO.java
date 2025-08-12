package com.bintics.adminscholls.domains.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateEmergencyContactDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String lastName;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String phone;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;

    @NotBlank(message = "La relación con el estudiante es obligatoria")
    @Size(max = 50, message = "La relación no puede exceder 50 caracteres")
    private String relationship; // relación con el estudiante (padre, madre, tutor, etc.)

    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String address; // dirección del contacto

    // publicId del estudiante al que se asociará el contacto (opcional)
    private String studentPublicId;
}

