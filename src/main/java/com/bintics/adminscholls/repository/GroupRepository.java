package com.bintics.adminscholls.repository;

import com.bintics.adminscholls.model.Group;
import com.bintics.adminscholls.model.AcademicLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Métodos simples para casos específicos (sin paginación)
    List<Group> findByIsActiveTrue();

    // Método universal que maneja todos los filtros y casos
    @Query("SELECT DISTINCT g FROM Group g " +
           "LEFT JOIN StudentGroupAssignment sga ON g.id = sga.group.id AND sga.status = 'ACTIVA' AND sga.isActive = true " +
           "WHERE " +
           "(:academicLevel IS NULL OR g.academicLevel = :academicLevel) AND " +
           "(:grade IS NULL OR LOWER(g.grade) LIKE LOWER(CONCAT('%', :grade, '%'))) AND " +
           "(:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:academicYear IS NULL OR g.academicYear = :academicYear) AND " +
           "(:isActive IS NULL OR g.isActive = :isActive) AND " +
           "(:availableOnly = false OR " +
           "  g.maxStudents > (SELECT COUNT(sga2) FROM StudentGroupAssignment sga2 " +
           "                   WHERE sga2.group.id = g.id AND sga2.status = 'ACTIVA' AND sga2.isActive = true)) AND " +
           "(:searchText IS NULL OR " +
           "  LOWER(g.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(g.grade) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(g.academicYear) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "  LOWER(g.groupCode) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Group> findGroupsWithAllFilters(@Param("academicLevel") AcademicLevel academicLevel,
                                        @Param("grade") String grade,
                                        @Param("name") String name,
                                        @Param("academicYear") String academicYear,
                                        @Param("isActive") Boolean isActive,
                                        @Param("availableOnly") boolean availableOnly,
                                        @Param("searchText") String searchText,
                                        Pageable pageable);

    @Query("SELECT COUNT(DISTINCT g.id) FROM Group g " +
           "JOIN StudentGroupAssignment sga ON g.id = sga.group.id " +
           "WHERE g.isActive = true AND sga.status = 'ACTIVA' AND sga.isActive = true " +
           "GROUP BY g.id " +
           "HAVING COUNT(sga) >= g.maxStudents")
    Long countFullGroups();

    @Query("SELECT AVG(CAST(activeStudents.studentCount AS double) * 100.0 / g.maxStudents) " +
           "FROM Group g LEFT JOIN " +
           "(SELECT sga.group.id as groupId, COUNT(sga) as studentCount " +
           " FROM StudentGroupAssignment sga " +
           " WHERE sga.status = 'ACTIVA' AND sga.isActive = true " +
           " GROUP BY sga.group.id) activeStudents " +
           "ON g.id = activeStudents.groupId " +
           "WHERE g.isActive = true")
    Double getAverageOccupancyPercentage();

    // Validaciones para evitar duplicados
    @Query("SELECT COUNT(g) FROM Group g WHERE " +
           "g.academicLevel = :academicLevel AND " +
           "LOWER(g.grade) = LOWER(:grade) AND " +
           "LOWER(g.name) = LOWER(:name) AND " +
           "g.academicYear = :academicYear AND " +
           "g.isActive = true AND " +
           "(:excludeId IS NULL OR g.id != :excludeId)")
    Long countDuplicateGroups(@Param("academicLevel") AcademicLevel academicLevel,
                             @Param("grade") String grade,
                             @Param("name") String name,
                             @Param("academicYear") String academicYear,
                             @Param("excludeId") Long excludeId);

    default boolean existsDuplicateGroup(AcademicLevel academicLevel, String grade,
                                       String name, String academicYear, Long excludeId) {
        return countDuplicateGroups(academicLevel, grade, name, academicYear, excludeId) > 0;
    }

    @Query("SELECT g FROM Group g WHERE " +
           "g.groupCode = :groupCode AND " +
           "LOWER(g.academicLevel) = LOWER(:academicLevel) AND " +
           "LOWER(g.grade) = LOWER(:grade) AND " +
           "LOWER(g.name) = LOWER(:groupName) AND " +
           "g.isActive = true")
    Optional<Group> findByGroupCode(@Param("groupCode") String groupCode, @NotBlank @Size String academicLevel, @NotBlank @Size String grade, @NotBlank @Size String groupName);

    // Nuevo método para buscar grupo por todos los parámetros necesarios para transferencia
    @Query("SELECT g FROM Group g WHERE " +
           "g.academicYear = :academicYear AND " +
           "g.academicLevel = :academicLevel AND " +
           "LOWER(g.grade) = LOWER(:grade) AND " +
           "LOWER(g.name) = LOWER(:groupName) AND " +
           "g.isActive = true")
    Optional<Group> findByAcademicYearAndLevelAndGradeAndName(@Param("academicYear") String academicYear,
                                                             @Param("academicLevel") AcademicLevel academicLevel,
                                                             @Param("grade") String grade,
                                                             @Param("groupName") String groupName);
}
