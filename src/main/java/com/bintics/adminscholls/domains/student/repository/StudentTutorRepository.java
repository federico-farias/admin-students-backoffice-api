package com.bintics.adminscholls.domains.student.repository;

import com.bintics.adminscholls.domains.student.model.StudentTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTutorRepository extends JpaRepository<StudentTutor, Long> {

    List<StudentTutor> findByStudentPublicId(String studentPublicId);

    List<StudentTutor> findByTutorPublicId(String tutorPublicId);

    void deleteByStudentPublicId(String studentPublicId);
}
