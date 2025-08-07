package com.bintics.adminscholls.domains.breakfast.src.repository;

import com.bintics.adminscholls.domains.breakfast.src.model.BreakfastPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreakfastPackageRepository extends JpaRepository<BreakfastPackage, Long> {

    List<BreakfastPackage> findByIsActiveTrue();

    List<BreakfastPackage> findByIsActiveTrueOrderByNameAsc();
}
