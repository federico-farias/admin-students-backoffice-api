package com.bintics.adminscholls.domains.student.repository;

import com.bintics.adminscholls.domains.student.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByPublicId(String publicId);

    List<Tutor> findByIsActiveTrue();

    @Query("SELECT t.publicId FROM Tutor t WHERE t.publicId IN :publicIds AND t.isActive = true")
    List<String> findExistingPublicIds(@Param("publicIds") List<String> publicIds);

    List<Tutor> findByPublicIdIn(List<String> publicIds);

    @Query("SELECT t FROM Tutor t WHERE t.isActive = true AND " +
           "(LOWER(t.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(t.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(t.phone) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Tutor> findBySearchTerm(@Param("search") String search);
}
