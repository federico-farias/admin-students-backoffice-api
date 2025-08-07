package com.bintics.adminscholls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

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
        this.students = new ArrayList<>();
    }

    // Utility methods
    public Integer getStudentsCount() {
        return students != null ? students.size() : 0;
    }

    public String getFullName() {
        return academicLevel + " " + grade + " - " + name;
    }

    public boolean hasAvailableSlots() {
        return getStudentsCount() < maxStudents;
    }

    // Método para compatibilidad con código existente
    public boolean hasAvailableSpaces() {
        return hasAvailableSlots();
    }
}
