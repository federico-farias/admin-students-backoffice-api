package com.bintics.adminscholls.domains.student.dto;

import com.bintics.adminscholls.domains.student.model.Student;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    private String publicId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String lastName;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String phone;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate dateOfBirth;

    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String address;

    // Lista de publicId de contactos de emergencia (para crear/actualizar)
    @NotEmpty(message = "Debe proporcionar al menos un contacto de emergencia")
    private List<String> emergencyContactIds;

    // Lista de objetos completos de contactos de emergencia (solo para lectura)
    private List<EmergencyContactDTO> emergencyContacts;

    // Lista de publicId de tutores
    @NotEmpty(message = "Debe proporcionar al menos un tutor")
    private List<String> tutorIds;

    private Boolean isActive;

    // Constructor para crear desde entidad
    public StudentDTO(Student student, List<EmergencyContactDTO> emergencyContacts, List<String> tutorIds) {
        this.id = student.getId();
        this.publicId = student.getPublicId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.dateOfBirth = student.getDateOfBirth();
        this.address = student.getAddress();
        this.isActive = student.getIsActive();
        this.emergencyContacts = emergencyContacts; // Solo para lectura
        this.tutorIds = tutorIds;
    }

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.publicId = student.getPublicId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.dateOfBirth = student.getDateOfBirth();
        this.address = student.getAddress();
        this.isActive = student.getIsActive();
        // Los contactos de emergencia y tutores se cargarán por separado si es necesario
    }

    // Utility methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
