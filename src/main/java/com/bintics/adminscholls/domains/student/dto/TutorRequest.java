package com.bintics.adminscholls.domains.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TutorRequest {
    @NotBlank
    private String publicId;
    @NotBlank
    private String relationship;
}
