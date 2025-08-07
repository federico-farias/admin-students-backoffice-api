package com.bintics.adminscholls.domains.group.dto;

import com.bintics.adminscholls.domains.group.model.Group;
import com.bintics.adminscholls.shared.model.AcademicLevel;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {

    private Long id;

    private String groupCode;

    @NotNull(message = "El nivel académico es obligatorio")
    private AcademicLevel academicLevel;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 50)
    private String grade;

    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Size(max = 10)
    private String name;

    @NotBlank(message = "El ciclo escolar es obligatorio")
    @Size(max = 20, message = "El ciclo escolar no puede exceder 20 caracteres")
    private String academicYear;

    @NotNull(message = "El máximo de estudiantes es obligatorio")
    @Min(value = 1, message = "El máximo de estudiantes debe ser mayor a 0")
    @Max(value = 50, message = "El máximo de estudiantes no puede ser mayor a 50")
    private Integer maxStudents;

    private Integer studentsCount;
    private Boolean isActive;

    // Constructor para convertir desde entidad
    public GroupDTO(Group group) {
        this.id = group.getId();
        this.groupCode = group.getGroupCode();
        this.academicLevel = group.getAcademicLevel();
        this.grade = group.getGrade();
        this.name = group.getName();
        this.academicYear = group.getAcademicYear();
        this.maxStudents = group.getMaxStudents();
        this.studentsCount = 0; // Se calculará externamente usando StudentGroupAssignmentService
        this.isActive = group.getIsActive();
    }

    // Constructor con studentsCount calculado externamente
    public GroupDTO(Group group, Integer studentsCount) {
        this(group);
        this.studentsCount = studentsCount;
    }

    // Utility methods
    public String getFullName() {
        return academicLevel + " " + grade + " - " + name;
    }

    public boolean hasAvailableSpaces() {
        return studentsCount != null && maxStudents != null && studentsCount < maxStudents;
    }

    public Integer getAvailableSpaces() {
        if (studentsCount != null && maxStudents != null) {
            return maxStudents - studentsCount;
        }
        return null;
    }

    public boolean isFull() {
        return studentsCount != null && maxStudents != null &&
               studentsCount.equals(maxStudents);
    }

    public double getOccupancyPercentage() {
        if (studentsCount != null && maxStudents != null && maxStudents > 0) {
            return (studentsCount.doubleValue() / maxStudents.doubleValue()) * 100;
        }
        return 0.0;
    }
}
