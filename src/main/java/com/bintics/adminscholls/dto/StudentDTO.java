package com.bintics.adminscholls.dto;

import com.bintics.adminscholls.model.Student;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String lastName;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String phone;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate dateOfBirth;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 50)
    private String grade;

    @NotBlank(message = "La sección es obligatoria")
    @Size(max = 10)
    private String section;

    @NotBlank(message = "El nombre del padre/madre es obligatorio")
    @Size(max = 200)
    private String parentName;

    @NotBlank(message = "El teléfono del padre/madre es obligatorio")
    @Size(max = 20)
    private String parentPhone;

    @Email(message = "El email del padre/madre debe tener un formato válido")
    @Size(max = 255)
    private String parentEmail;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 500)
    private String address;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate enrollmentDate;

    private Boolean isActive;

    // Emergency Contact
    @Size(max = 200)
    private String emergencyContactName;

    @Size(max = 20)
    private String emergencyContactPhone;

    @Size(max = 100)
    private String emergencyContactRelationship;

    private Long groupId;
    private String groupName;

    // Constructor para convertir desde entidad
    public StudentDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.dateOfBirth = student.getDateOfBirth();
        this.grade = student.getGrade();
        this.section = student.getSection();
        this.parentName = student.getParentName();
        this.parentPhone = student.getParentPhone();
        this.parentEmail = student.getParentEmail();
        this.address = student.getAddress();
        this.enrollmentDate = student.getEnrollmentDate();
        this.isActive = student.getIsActive();
        this.emergencyContactName = student.getEmergencyContactName();
        this.emergencyContactPhone = student.getEmergencyContactPhone();
        this.emergencyContactRelationship = student.getEmergencyContactRelationship();

        if (student.getGroup() != null) {
            this.groupId = student.getGroup().getId();
            this.groupName = student.getGroup().getFullName();
        }
    }

    // Utility methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
