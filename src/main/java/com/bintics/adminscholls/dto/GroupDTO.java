package com.bintics.adminscholls.dto;

import com.bintics.adminscholls.model.AcademicLevel;
import com.bintics.adminscholls.model.Group;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {

    private Long id;

    @NotNull(message = "El nivel académico es obligatorio")
    private AcademicLevel academicLevel;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 50)
    private String grade;

    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Size(max = 10)
    private String name;

    @NotNull(message = "El máximo de estudiantes es obligatorio")
    @Min(value = 1, message = "El máximo de estudiantes debe ser mayor a 0")
    @Max(value = 50, message = "El máximo de estudiantes no puede ser mayor a 50")
    private Integer maxStudents;

    private Integer studentsCount;
    private Boolean isActive;

    // Constructor para convertir desde entidad
    public GroupDTO(Group group) {
        this.id = group.getId();
        this.academicLevel = group.getAcademicLevel();
        this.grade = group.getGrade();
        this.name = group.getName();
        this.maxStudents = group.getMaxStudents();
        this.studentsCount = group.getStudentsCount();
        this.isActive = group.getIsActive();
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
