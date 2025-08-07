package com.bintics.adminscholls.domains.assignment.dto;

import com.bintics.adminscholls.domains.assignment.model.StudentGroupAssignment;
import com.bintics.adminscholls.shared.model.AssignmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGroupAssignmentDTO {

    private Long id;
    private String publicId;

    @NotBlank(message = "El ID público del estudiante es obligatorio")
    private String studentPublicId;

    private String studentFullName;

    private String groupFullName;

    @NotNull(message = "La fecha de asignación es obligatoria")
    private LocalDate assignmentDate;

    @NotBlank(message = "El año académico es obligatorio")
    @Size(max = 20)
    private String academicYear;

    @NotBlank(message = "El nivel académico es obligatorio")
    @Size(max = 50)
    private String academicLevel;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 50)
    private String grade;

    @NotBlank(message = "El grupo es obligatorio")
    @Size(max = 100)
    private String groupName;

    private AssignmentStatus status;

    @Size(max = 1000)
    private String notes;

    private Boolean isActive;

    // Constructor para convertir desde entidad
    public StudentGroupAssignmentDTO(StudentGroupAssignment assignment) {
        this.id = assignment.getId();
        this.publicId = assignment.getPublicId();
        this.studentPublicId = assignment.getStudent() != null ? assignment.getStudent().getPublicId() : null;
        this.studentFullName = assignment.getStudentFullName();
        this.groupFullName = assignment.getGroupFullName();
        this.assignmentDate = assignment.getAssignmentDate();
        this.academicYear = assignment.getAcademicYear();
        this.status = assignment.getStatus();
        this.notes = assignment.getNotes();
        this.isActive = assignment.getIsActive();
    }

    // Utility methods
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "";
    }

    public boolean isActive() {
        return AssignmentStatus.ACTIVA.equals(status);
    }

    public boolean isFinished() {
        return AssignmentStatus.FINALIZADA.equals(status);
    }

}
