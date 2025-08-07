package com.bintics.adminscholls.domains.student.dto;

import com.bintics.adminscholls.domains.student.model.EmergencyContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContactDTO {

    private String publicId;
    private String firstName;
    private String lastName;
    private String relationship;
    private String phone;
    private String email;
    private String address;
    private Boolean isActive;

    // Constructor para crear desde entidad
    public EmergencyContactDTO(EmergencyContact contact) {
        this.publicId = contact.getPublicId();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.relationship = contact.getRelationship();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
        this.address = contact.getAddress();
        this.isActive = contact.getIsActive();
    }

    // Utility method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
