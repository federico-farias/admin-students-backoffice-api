package com.bintics.adminscholls.domains.student.model;

import com.bintics.adminscholls.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_tutors")
@Getter
@Setter
@NoArgsConstructor
public class StudentTutor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_public_id", nullable = false)
    private String studentPublicId;

    @Column(name = "tutor_public_id", nullable = false)
    private String tutorPublicId;

    @Column(name = "relationship", nullable = false)
    private String relationship;

    @Column(name = "is_primary_tutor", nullable = false)
    private Boolean isPrimaryTutor = false;

    public StudentTutor(String studentPublicId, String tutorPublicId, String relationship, Boolean isPrimaryTutor) {
        this.studentPublicId = studentPublicId;
        this.tutorPublicId = tutorPublicId;
        this.relationship = relationship;
        this.isPrimaryTutor = isPrimaryTutor != null ? isPrimaryTutor : false;
    }
}
