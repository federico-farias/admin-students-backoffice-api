package com.bintics.adminscholls.repository;

import com.bintics.adminscholls.model.BreakfastPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreakfastPackageRepository extends JpaRepository<BreakfastPackage, Long> {

    List<BreakfastPackage> findByIsActiveTrue();

    List<BreakfastPackage> findByIsActiveTrueOrderByNameAsc();
}
