package com.bintics.adminscholls.domains.student.model;

import com.bintics.adminscholls.shared.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "group") // Evitar referencia circular en toString
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private String publicId = UUID.randomUUID().toString();

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 20)
    @Column(name = "phone")
    private String phone;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 50)
    @Column(name = "grade", nullable = false)
    private String grade;

    @NotBlank(message = "La sección es obligatoria")
    @Size(max = 10)
    @Column(name = "section", nullable = false)
    private String section;

    @NotBlank(message = "El nombre del padre/madre es obligatorio")
    @Size(max = 200)
    @Column(name = "parent_name", nullable = false)
    private String parentName;

    @NotBlank(message = "El teléfono del padre/madre es obligatorio")
    @Size(max = 20)
    @Column(name = "parent_phone", nullable = false)
    private String parentPhone;

    @Email(message = "El email del padre/madre debe tener un formato válido")
    @Size(max = 255)
    @Column(name = "parent_email")
    private String parentEmail;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 500)
    @Column(name = "address", nullable = false)
    private String address;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Emergency Contact
    @Size(max = 200)
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Size(max = 20)
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Size(max = 100)
    @Column(name = "emergency_contact_relationship")
    private String emergencyContactRelationship;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor personalizado para casos comunes
    public Student(String firstName, String lastName, LocalDate dateOfBirth,
                   String grade, String section, String parentName, String parentPhone,
                   String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.grade = grade;
        this.section = section;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.address = address;
        this.isActive = true;
    }

    // Utility method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
