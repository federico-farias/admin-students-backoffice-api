package com.bintics.adminscholls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "school_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "students") // Evitar referencia circular en toString
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private String publicId = UUID.randomUUID().toString();

    @Column(name = "group_code", nullable = false, unique = true, updatable = false)
    private String groupCode;

    @NotNull(message = "El nivel académico es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "academic_level", nullable = false)
    private AcademicLevel academicLevel;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 50)
    @Column(name = "grade", nullable = false)
    private String grade;

    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Size(max = 10)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "El ciclo escolar es obligatorio")
    @Size(max = 20, message = "El ciclo escolar no puede exceder 20 caracteres")
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @NotNull(message = "El máximo de estudiantes es obligatorio")
    @Min(value = 1, message = "El máximo de estudiantes debe ser mayor a 0")
    @Max(value = 50, message = "El máximo de estudiantes no puede ser mayor a 50")
    @Column(name = "max_students", nullable = false)
    private Integer maxStudents;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor personalizado para casos comunes
    public Group(AcademicLevel academicLevel, String grade, String name, String academicYear, Integer maxStudents) {
        this.academicLevel = academicLevel;
        this.grade = grade;
        this.name = name;
        this.academicYear = academicYear;
        this.maxStudents = maxStudents;
        this.isActive = true;
    }

    // Utility methods - ahora estos métodos necesitarán ser calculados externamente
    // ya que no tenemos la relación directa con students
    public String getFullName() {
        return academicLevel + " " + grade + " - " + name;
    }

    // Método para generar el código del grupo automáticamente
    public void generateGroupCode() {
        if (this.groupCode == null && this.academicYear != null &&
            this.academicLevel != null && this.grade != null && this.name != null) {

            // Formato: {academicYear}-{academicLevel}-{grade}-{name}
            // Ejemplo: 2024-2025-PRIMARIA-PRIMERO-A
            this.groupCode = String.format("%s-%s-%s-%s",
                this.academicYear,
                this.academicLevel.toString(),
                this.grade.toUpperCase().replace(" ", ""),
                this.name.toUpperCase()
            );
        }
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        generateGroupCode();
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID().toString();
        }
    }
}
