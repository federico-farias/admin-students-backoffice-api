package com.bintics.adminscholls.domains.assignment.src.repository;

import com.bintics.adminscholls.domains.assignment.src.model.StudentGroupAssignment;
import com.bintics.adminscholls.shared.model.AssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGroupAssignmentRepository extends JpaRepository<StudentGroupAssignment, Long> {

    Optional<StudentGroupAssignment> findByPublicId(String publicId);

    List<StudentGroupAssignment> findByIsActiveTrue();

    @Query("SELECT sga FROM StudentGroupAssignment sga WHERE sga.student.publicId = :studentPublicId")
    List<StudentGroupAssignment> findByStudentPublicId(@Param("studentPublicId") String studentPublicId);

    @Query("SELECT sga FROM StudentGroupAssignment sga WHERE sga.group.groupCode = :groupCode")
    List<StudentGroupAssignment> findByGroupCode(@Param("groupCode") String groupCode);

    @Query("SELECT sga FROM StudentGroupAssignment sga WHERE " +
           "sga.student.publicId = :studentPublicId AND sga.status = 'ACTIVA' AND sga.isActive = true")
    Optional<StudentGroupAssignment> findActiveAssignmentByStudent(@Param("studentPublicId") String studentPublicId);

    @Query("SELECT sga FROM StudentGroupAssignment sga WHERE " +
           "sga.group.groupCode = :groupCode AND sga.status = 'ACTIVA' AND sga.isActive = true")
    List<StudentGroupAssignment> findActiveAssignmentsByGroup(@Param("groupCode") String groupCode);

    // Método universal con filtros y paginación
    @Query("SELECT sga FROM StudentGroupAssignment sga WHERE " +
           "(:status IS NULL OR sga.status = :status) AND " +
           "(:academicYear IS NULL OR sga.academicYear = :academicYear) AND " +
           "(:groupCode IS NULL OR sga.group.groupCode = :groupCode) AND " +
           "(:isActive IS NULL OR sga.isActive = :isActive) AND " +
           "(:searchText IS NULL OR " +
           "  LOWER(sga.student.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(sga.student.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(sga.group.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(sga.group.grade) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(sga.group.groupCode) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<StudentGroupAssignment> findAssignmentsWithFilters(@Param("status") AssignmentStatus status,
                                                           @Param("academicYear") String academicYear,
                                                           @Param("groupCode") String groupCode,
                                                           @Param("isActive") Boolean isActive,
                                                           @Param("searchText") String searchText,
                                                           Pageable pageable);

    @Query("SELECT COUNT(sga) FROM StudentGroupAssignment sga WHERE " +
           "sga.group.groupCode = :groupCode AND sga.status = 'ACTIVA' AND sga.isActive = true")
    Long countActiveStudentsInGroup(@Param("groupCode") String groupCode);

    @Query("SELECT COUNT(sga) FROM StudentGroupAssignment sga WHERE sga.status = :status AND sga.isActive = true")
    Long countByStatus(@Param("status") AssignmentStatus status);
}
