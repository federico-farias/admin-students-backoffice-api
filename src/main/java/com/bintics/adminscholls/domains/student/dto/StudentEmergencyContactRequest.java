package com.bintics.adminscholls.domains.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ...existing code...
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEmergencyContactRequest {
    @NotBlank
    private String publicId;
    @NotBlank
    private String relationship;

    private EmergencyContactDTO data;
    // getters y setters
}
// ...existing code...

