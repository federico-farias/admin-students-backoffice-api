package com.bintics.adminscholls.domains.student.model;

import com.bintics.adminscholls.shared.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tutors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tutor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", unique = true, nullable = false)
    private String publicId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "La relación con el estudiante es obligatoria")
    @Size(max = 50, message = "La relación no puede exceder 50 caracteres")
    @Column(name = "relationship", nullable = false)
    private String relationship; // Padre, Madre, Tutor Legal, etc.

    @Size(max = 50, message = "La ocupación no puede exceder 50 caracteres")
    @Column(name = "occupation")
    private String occupation;

    @PrePersist
    private void generatePublicId() {
        if (publicId == null || publicId.isEmpty()) {
            publicId = UUID.randomUUID().toString();
        }
    }

    public Tutor(String firstName, String lastName, String email, String phone,
                String address, String relationship, String occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.relationship = relationship;
        this.occupation = occupation;
        generatePublicId();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
