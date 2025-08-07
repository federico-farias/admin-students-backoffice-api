package com.bintics.adminscholls.domains.enrollment.src.model;

import com.bintics.adminscholls.shared.model.BaseEntity;
import com.bintics.adminscholls.shared.model.EnrollmentStatus;
import com.bintics.adminscholls.domains.student.src.model.Student;
import com.bintics.adminscholls.domains.group.src.model.Group;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"student", "group"})
public class Enrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private String publicId = UUID.randomUUID().toString();

    @NotNull(message = "El estudiante es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "El grupo es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @NotNull(message = "El año académico es obligatorio")
    @Size(max = 20)
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @NotNull(message = "La cuota de inscripción es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La cuota debe ser mayor a 0")
    @Column(name = "enrollment_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal enrollmentFee;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.PENDIENTE;

    @Size(max = 1000)
    @Column(name = "notes")
    private String notes;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor personalizado
    public Enrollment(Student student, Group group, LocalDate enrollmentDate,
                     String academicYear, BigDecimal enrollmentFee) {
        this.student = student;
        this.group = group;
        this.enrollmentDate = enrollmentDate;
        this.academicYear = academicYear;
        this.enrollmentFee = enrollmentFee;
        this.status = EnrollmentStatus.PENDIENTE;
        this.isActive = true;
    }

    // Utility methods
    public String getStudentFullName() {
        return student != null ? student.getFullName() : "";
    }

    public String getGroupFullName() {
        return group != null ? group.getFullName() : "";
    }
}
