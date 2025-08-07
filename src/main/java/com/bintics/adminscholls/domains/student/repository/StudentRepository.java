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
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Student> findActiveStudentsBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.isActive = true")
    Long countActiveStudents();

    // Métodos para validar duplicados
    boolean existsByEmailAndIsActiveTrue(String email);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s " +
           "WHERE s.firstName = :firstName AND s.lastName = :lastName AND " +
           "s.dateOfBirth = :dateOfBirth AND s.isActive = true")
    boolean existsByFirstNameAndLastNameAndDateOfBirth(@Param("firstName") String firstName,
                                                      @Param("lastName") String lastName,
                                                      @Param("dateOfBirth") java.time.LocalDate dateOfBirth);
}
