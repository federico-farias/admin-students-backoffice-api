package com.bintics.adminscholls.repository;

import com.bintics.adminscholls.model.Group;
import com.bintics.adminscholls.model.AcademicLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Métodos simples para casos específicos (sin paginación)
    List<Group> findByIsActiveTrue();

    // Método universal que maneja todos los filtros y casos
    @Query("SELECT g FROM Group g WHERE " +
           "(:academicLevel IS NULL OR g.academicLevel = :academicLevel) AND " +
           "(:grade IS NULL OR LOWER(g.grade) LIKE LOWER(CONCAT('%', :grade, '%'))) AND " +
           "(:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:academicYear IS NULL OR g.academicYear = :academicYear) AND " +
           "(:isActive IS NULL OR g.isActive = :isActive) AND " +
           "(:availableOnly = false OR SIZE(g.students) < g.maxStudents) AND " +
           "(:searchText IS NULL OR " +
           "  LOWER(g.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(g.grade) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(g.academicYear) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Group> findGroupsWithAllFilters(@Param("academicLevel") AcademicLevel academicLevel,
                                        @Param("grade") String grade,
                                        @Param("name") String name,
                                        @Param("academicYear") String academicYear,
                                        @Param("isActive") Boolean isActive,
                                        @Param("availableOnly") boolean availableOnly,
                                        @Param("searchText") String searchText,
                                        Pageable pageable);

    @Query("SELECT COUNT(g) FROM Group g WHERE g.isActive = true AND SIZE(g.students) >= g.maxStudents")
    Long countFullGroups();

    @Query("SELECT AVG(SIZE(g.students) * 100.0 / g.maxStudents) FROM Group g WHERE g.isActive = true")
    Double getAverageOccupancyPercentage();
}
