package com.bintics.adminscholls.domains.student.dto;

import com.bintics.adminscholls.domains.student.model.StudentTutor;
import com.bintics.adminscholls.domains.student.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorDTO {

    private String publicId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String relationship;
    private String occupation;
    private Boolean isActive;

    // Constructor para crear desde entidad
    public TutorDTO(Tutor tutor) {
        this.publicId = tutor.getPublicId();
        this.firstName = tutor.getFirstName();
        this.lastName = tutor.getLastName();
        this.email = tutor.getEmail();
        this.phone = tutor.getPhone();
        this.address = tutor.getAddress();
        this.relationship = tutor.getRelationship();
        this.occupation = tutor.getOccupation();
        this.isActive = tutor.getIsActive();
    }

    // Utility method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
