package com.bintics.adminscholls.domains.enrollment.repository;

import com.bintics.adminscholls.domains.enrollment.model.Enrollment;
import com.bintics.adminscholls.shared.model.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByPublicId(String publicId);

    List<Enrollment> findByIsActiveTrue();

    List<Enrollment> findByStatus(EnrollmentStatus status);

    List<Enrollment> findByAcademicYear(String academicYear);

    @Query("SELECT e FROM Enrollment e WHERE e.student.publicId = :studentPublicId")
    List<Enrollment> findByStudentPublicId(@Param("studentPublicId") String studentPublicId);

    @Query("SELECT e FROM Enrollment e WHERE e.group.id = :groupId")
    List<Enrollment> findByGroupId(@Param("groupId") Long groupId);

    // Método universal con filtros y paginación
    @Query("SELECT e FROM Enrollment e WHERE " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:academicYear IS NULL OR e.academicYear = :academicYear) AND " +
           "(:groupId IS NULL OR e.group.id = :groupId) AND " +
           "(:isActive IS NULL OR e.isActive = :isActive) AND " +
           "(:searchText IS NULL OR " +
           "  LOWER(e.student.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(e.student.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(e.group.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(e.group.grade) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Enrollment> findEnrollmentsWithFilters(@Param("status") EnrollmentStatus status,
                                               @Param("academicYear") String academicYear,
                                               @Param("groupId") Long groupId,
                                               @Param("isActive") Boolean isActive,
                                               @Param("searchText") String searchText,
                                               Pageable pageable);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.status = :status AND e.isActive = true")
    Long countByStatus(@Param("status") EnrollmentStatus status);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.academicYear = :academicYear AND e.isActive = true")
    Long countByAcademicYear(@Param("academicYear") String academicYear);
}
