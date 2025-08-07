package com.bintics.adminscholls.domains.student.repository;

import com.bintics.adminscholls.domains.student.model.StudentEmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEmergencyContactRepository extends JpaRepository<StudentEmergencyContact, Long> {

    List<StudentEmergencyContact> findByStudentPublicId(String studentPublicId);

    List<StudentEmergencyContact> findByEmergencyContactPublicId(String emergencyContactPublicId);

    void deleteByStudentPublicId(String studentPublicId);
}
