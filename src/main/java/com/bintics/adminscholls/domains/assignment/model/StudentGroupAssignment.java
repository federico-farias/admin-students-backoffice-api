package com.bintics.adminscholls.domains.assignment.model;

import com.bintics.adminscholls.shared.model.AssignmentStatus;
import com.bintics.adminscholls.domains.student.model.Student;
import com.bintics.adminscholls.domains.group.model.Group;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student_group_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"student", "group"})
public class StudentGroupAssignment {

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

    @NotNull(message = "La fecha de asignación es obligatoria")
    @Column(name = "assignment_date", nullable = false)
    private LocalDate assignmentDate;

    @NotNull(message = "El año académico es obligatorio")
    @Size(max = 20)
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssignmentStatus status = AssignmentStatus.ACTIVA;

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
    public StudentGroupAssignment(Student student, Group group, LocalDate assignmentDate, String academicYear) {
        this.student = student;
        this.group = group;
        this.assignmentDate = assignmentDate;
        this.academicYear = academicYear;
        this.status = AssignmentStatus.ACTIVA;
        this.isActive = true;
    }

    // Utility methods
    public String getStudentFullName() {
        return student != null ? student.getFullName() : "";
    }

    public String getGroupFullName() {
        return group != null ? group.getFullName() : "";
    }

    public String getGroupCode() {
        return group != null ? group.getGroupCode() : "";
    }

    @PrePersist
    public void prePersist() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID().toString();
        }
        if (this.assignmentDate == null) {
            this.assignmentDate = LocalDate.now();
        }
    }
}
