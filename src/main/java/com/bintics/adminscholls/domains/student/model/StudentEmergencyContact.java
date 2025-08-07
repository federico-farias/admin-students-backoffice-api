package com.bintics.adminscholls.domains.student.model;

import com.bintics.adminscholls.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_emergency_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEmergencyContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_public_id", nullable = false)
    private String studentPublicId;

    @Column(name = "emergency_contact_public_id", nullable = false)
    private String emergencyContactPublicId;

    public StudentEmergencyContact(String studentPublicId, String emergencyContactPublicId) {
        this.studentPublicId = studentPublicId;
        this.emergencyContactPublicId = emergencyContactPublicId;
    }
}
