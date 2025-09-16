package com.bintics.adminscholls.domains.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorRequest {
    @NotBlank
    private String publicId;
    @NotBlank
    private String relationship;

    private TutorDTO data;
}
