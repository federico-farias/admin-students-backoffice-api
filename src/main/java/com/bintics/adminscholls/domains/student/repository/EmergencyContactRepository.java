package com.bintics.adminscholls.domains.student.repository;

import com.bintics.adminscholls.domains.student.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    Optional<EmergencyContact> findByPublicId(String publicId);

    List<EmergencyContact> findByIsActiveTrue();

    @Query("SELECT ec.publicId FROM EmergencyContact ec WHERE ec.publicId IN :publicIds AND ec.isActive = true")
    List<String> findExistingPublicIds(@Param("publicIds") List<String> publicIds);

    List<EmergencyContact> findByPublicIdIn(List<String> publicIds);

    @Query("SELECT ec FROM EmergencyContact ec WHERE ec.isActive = true AND " +
           "(LOWER(ec.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(ec.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(ec.phone) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<EmergencyContact> findBySearchTerm(@Param("search") String search);
}
