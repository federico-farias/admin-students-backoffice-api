package com.bintics.adminscholls.domains.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// ...existing code...
@Data
public class StudentEmergencyContactRequest {
    @NotBlank
    private String emergencyContactPublicId;
    @NotBlank
    private String relationship;
    // getters y setters
}
// ...existing code...

