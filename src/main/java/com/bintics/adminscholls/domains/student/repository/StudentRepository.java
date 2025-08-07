package com.bintics.adminscholls.domains.student.repository;

import com.bintics.adminscholls.domains.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByIsActiveTrue();

    Optional<Student> findByPublicId(String publicId);

    @Query("SELECT s FROM Student s WHERE s.isActive = true AND " +
           "(LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Student> findActiveStudentsBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.isActive = true")
    Long countActiveStudents();

}
