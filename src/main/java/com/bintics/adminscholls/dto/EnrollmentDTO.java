package com.bintics.adminscholls.dto;

import com.bintics.adminscholls.model.Enrollment;
import com.bintics.adminscholls.model.EnrollmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDTO {

    private Long id;
    private String publicId;

    @NotBlank(message = "El ID público del estudiante es obligatorio")
    private String studentPublicId;

    private String studentFullName;

    @NotNull(message = "El ID del grupo es obligatorio")
    private Long groupId;

    private String groupFullName;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate enrollmentDate;

    @NotBlank(message = "El año académico es obligatorio")
    @Size(max = 20)
    private String academicYear;

    @NotNull(message = "La cuota de inscripción es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La cuota debe ser mayor a 0")
    private BigDecimal enrollmentFee;

    private EnrollmentStatus status;

    @Size(max = 1000)
    private String notes;

    private Boolean isActive;

    // Constructor para convertir desde entidad
    public EnrollmentDTO(Enrollment enrollment) {
        this.id = enrollment.getId();
        this.publicId = enrollment.getPublicId();
        this.studentPublicId = enrollment.getStudent() != null ? enrollment.getStudent().getPublicId() : null;
        this.studentFullName = enrollment.getStudentFullName();
        this.groupId = enrollment.getGroup() != null ? enrollment.getGroup().getId() : null;
        this.groupFullName = enrollment.getGroupFullName();
        this.enrollmentDate = enrollment.getEnrollmentDate();
        this.academicYear = enrollment.getAcademicYear();
        this.enrollmentFee = enrollment.getEnrollmentFee();
        this.status = enrollment.getStatus();
        this.notes = enrollment.getNotes();
        this.isActive = enrollment.getIsActive();
    }

    // Utility methods
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "";
    }

    public boolean isPending() {
        return EnrollmentStatus.PENDIENTE.equals(status);
    }

    public boolean isConfirmed() {
        return EnrollmentStatus.CONFIRMADA.equals(status);
    }

    public boolean isCompleted() {
        return EnrollmentStatus.COMPLETADA.equals(status);
    }
}
