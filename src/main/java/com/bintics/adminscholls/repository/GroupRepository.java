package com.bintics.adminscholls.repository;

import com.bintics.adminscholls.model.Group;
import com.bintics.adminscholls.model.AcademicLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByIsActiveTrue();

    List<Group> findByAcademicLevel(AcademicLevel academicLevel);

    List<Group> findByGradeAndIsActiveTrue(String grade);

    @Query("SELECT COUNT(g) FROM Group g WHERE g.isActive = true AND SIZE(g.students) >= g.maxStudents")
    Long countFullGroups();

    @Query("SELECT AVG(SIZE(g.students) * 100.0 / g.maxStudents) FROM Group g WHERE g.isActive = true")
    Double getAverageOccupancyPercentage();
}
